package arbitrage.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arbitrage.dao.ArbitrageTransactionDao;
import arbitrage.dao.PriceHistoryDao;
import arbitrage.dao.ProfitsDao;
import arbitrage.dto.ArbitrageTransactionDto;
import arbitrage.dto.TradeHistoryDto;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.util.BitCoinUtil;
import arbitrage.util.StatusEnum;
import arbitrage.vo.ArbitrageTargetVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.ProfitVo;

public class ArbitrageExecutorService {

	private static Logger log = LoggerFactory.getLogger(ArbitrageExecutorService.class);

	private final ArbitrageTransactionDao dao;

	public ArbitrageExecutorService() throws Exception {
		dao = new ArbitrageTransactionDao();
	}

	public void execute(ArbitrageTargetVo arbitrageTargetVo, boolean canExecute) throws Exception {

		
		if(!canExecute){
			return;
		}
		
		log.info("start execute");
		if (hasFailedOrder()) {
			return;
		}

		double amount = ArbitragePropertiesService.getInstance().getDealAmount();
		// 最初に取得データを保存する
		long newTransactionId = insertArbitrageData(arbitrageTargetVo, amount);
		printDealInfomationLog(arbitrageTargetVo, newTransactionId);

		// アービトラージ可能かどうかの金額をチェック
		if (!arbitrageTargetVo.isCanExecute()) {
			log.info("end execute for not deal");
			savePriceData(arbitrageTargetVo,newTransactionId);
			return;
		}

		// bitFlyer,zaifはサービス側の原因でapiが落ちることがあるので、先に行う
		if (isContainZaifService(arbitrageTargetVo.getAskTarget(), arbitrageTargetVo.getBidTarget())) {
			dealForNotStableService(BitCoinServiceEnum.Zaif,arbitrageTargetVo, amount, newTransactionId);
		}else if (isContainBitFlyerService(arbitrageTargetVo.getAskTarget(), arbitrageTargetVo.getBidTarget())) {
			dealForNotStableService(BitCoinServiceEnum.BIT_FLYER,arbitrageTargetVo, amount, newTransactionId);
		} else {
			log.info("start bid proc");
			boolean isSuccess = executeBid(arbitrageTargetVo, newTransactionId, amount);
			log.info("end bid proc");
			if (isSuccess) {
				log.info("start ask proc");
				executeAsk(arbitrageTargetVo, newTransactionId, amount);
				log.info("end ask proc");
			}

		}
		savePriceData(arbitrageTargetVo,newTransactionId);
	}

	private void savePriceData(ArbitrageTargetVo arbitrageTargetDto,long newTransactionId) {
		PriceHistoryDao priceHistoryDao = new PriceHistoryDao();
		insertProfits(arbitrageTargetDto.getProfitsList(), newTransactionId);
		arbitrageTargetDto.getOrderBookList().stream().forEach(p -> priceHistoryDao.insert(newTransactionId, p));
	}

	private void dealForNotStableService(BitCoinServiceEnum service,ArbitrageTargetVo arbitrageTargetDto, double amount, long newTransactionId)
			throws Exception {
		if (service.getServiceId() == arbitrageTargetDto.getAskTarget().getServiceId()) {
			log.info("First proc is "+service.getServiceCaption()+" ask.");
			boolean isSuccess = executeAsk(arbitrageTargetDto, newTransactionId, amount);
			if (isSuccess) {
				executeBid(arbitrageTargetDto, newTransactionId, amount);
			}
		} else if (service.getServiceId() == arbitrageTargetDto.getBidTarget().getServiceId()) {
			log.info("First proc is "+service.getServiceCaption() +" bid.");

			boolean isSuccess = executeBid(arbitrageTargetDto, newTransactionId, amount);
			if (isSuccess) {
				executeAsk(arbitrageTargetDto, newTransactionId, amount);
			}
		}
	}

	private boolean isContainBitFlyerService(OrderBookByServiceVo ask, OrderBookByServiceVo bid) {
		return BitCoinServiceEnum.BIT_FLYER.getServiceId() == ask.getServiceId()
				|| BitCoinServiceEnum.BIT_FLYER.getServiceId() == bid.getServiceId();
	}

	private boolean isContainZaifService(OrderBookByServiceVo ask, OrderBookByServiceVo bid) {
		return BitCoinServiceEnum.Zaif.getServiceId() == ask.getServiceId()
				|| BitCoinServiceEnum.Zaif.getServiceId() == bid.getServiceId();
	}
	
	private void printDealInfomationLog(ArbitrageTargetVo arbitrageTargetDto, long newTransactionId) {
		log.info("transaction id is " + newTransactionId);
		log.info("[ask service book] price=" + arbitrageTargetDto.getAskTarget().getBuyPrice());
		arbitrageTargetDto.getAskTarget().getBuyOrderBooks().stream().limit(10)
				.forEach(o -> log.info("price:" + o.getPrice() + " amount:" + o.getAmount()));
		log.info("[bid service book] price=" + arbitrageTargetDto.getBidTarget().getSellPrice());
		arbitrageTargetDto.getBidTarget().getSellOrderBooks().stream().limit(10)
				.forEach(o -> log.info("price:" + o.getPrice() + " amount:" + o.getAmount()));
	}

	private void insertProfits(List<ProfitVo> profitsList, long newTransactionId) {
		ProfitsDao profitsDao = new ProfitsDao();
		profitsList.stream().forEach(p -> profitsDao.insert(p, newTransactionId));

	}

	private boolean executeBid(ArbitrageTargetVo arbitrageTargetDto, long newTransactionId, double amount)
			throws Exception {
		log.info(LocalDateTime.now() + " bid start");
		BitCoinServiceHandler bitCoinServiceHandler = new BitCoinServiceHandler(
				arbitrageTargetDto.getBidTarget().getServiceId());
		double sellPrice = arbitrageTargetDto.getBidTarget().getSellPrice();
		TradeHistoryDto dto = bitCoinServiceHandler.sell(newTransactionId, sellPrice, amount);
		log.info("bid  " + "service:" + BitCoinUtil.getServiceCaption(arbitrageTargetDto.getBidTarget().getServiceId())
				+ "  " + dto.isSuccess());
		if (dto.isSuccess()) {
			// DBのstatusをupdate(売却注文済みに)
			updateArbitrageData(newTransactionId, StatusEnum.BIDED.getStatus());
		}
		log.info("bid end");
		return dto.isSuccess();
	}

	private boolean hasFailedOrder() {
		ArbitrageTransactionDao dao = new ArbitrageTransactionDao();
		List<ArbitrageTransactionDto> list = dao.getFailedOrder();
		return !list.isEmpty();
	}

	private boolean executeAsk(ArbitrageTargetVo arbitrageTargetDto, long arbitrageTransactionId, double amount)
			throws Exception {

		BitCoinServiceHandler bitCoinServiceHandler = new BitCoinServiceHandler(
				arbitrageTargetDto.getAskTarget().getServiceId());

		TradeHistoryDto tradeHistoryDto = bitCoinServiceHandler.buy(arbitrageTransactionId,
				arbitrageTargetDto.getAskTarget().getBuyPrice(), amount);

		log.info("ask  " + "service:" + BitCoinUtil.getServiceCaption(arbitrageTargetDto.getAskTarget().getServiceId())
				+ "  " + tradeHistoryDto.isSuccess());
		if (tradeHistoryDto.isSuccess()) {
			// DBのstatusをupdate(購入済みに)
			updateArbitrageData(arbitrageTransactionId, StatusEnum.ASKED.getStatus());
		}
		return tradeHistoryDto.isSuccess();

	}

	private void updateArbitrageData(long transactionId, int status) {
		dao.update(transactionId, status);
	}

	private long insertArbitrageData(ArbitrageTargetVo arbitrageTargetDto, double amount) {
		return dao
				.insert(ArbitrageTransactionDto.builder().askServiceId(arbitrageTargetDto.getAskTarget().getServiceId())
						.bidServiceId(arbitrageTargetDto.getBidTarget().getServiceId()).currency("bitCoin")
						.amount(amount).askAmount(arbitrageTargetDto.getAskTarget().getBuyPrice())
						.bidAmount(arbitrageTargetDto.getBidTarget().getSellPrice())
						.difference(arbitrageTargetDto.getDifference()).build());
	}

}

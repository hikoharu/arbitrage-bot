package arbitrage.service.external;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arbitrage.dao.SequenceDao;
import arbitrage.dto.TradeHistoryDto;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.util.CurrencyEnum;
import arbitrage.util.PairEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.OrderVo;
import jp.nyatla.jzaif.api.ApiKey;
import jp.nyatla.jzaif.api.ExchangeApi;
import jp.nyatla.jzaif.api.PublicApi;
import jp.nyatla.jzaif.api.result.DepthResult;
import jp.nyatla.jzaif.api.result.GetInfoResult;
import jp.nyatla.jzaif.api.result.TradeResult;
import jp.nyatla.jzaif.types.CurrencyPair;
import jp.nyatla.jzaif.types.TradeType;

public class ZaifService implements BitCoinService {

	private static ZaifService instance;

	private PublicApi publicApi;
	private ApiKey key;
	private ExchangeApi exchangeApi;
	private SequenceDao sequenceDao;

	private static Logger log = LoggerFactory.getLogger(ZaifService.class);

	private CurrencyPair pair() {
		if (PairEnum.BTC_JPY.equals(getPair())) {
			return CurrencyPair.BTCJPY;
		} else if (PairEnum.ETH_BTC.equals(getPair())) {
			return CurrencyPair.ETHBTC;
		}
		return null;
	}

	private double getTargetFund(GetInfoResult result) {
		return getFund(result, getTargetCurrency());
	}

	private double getBaseFund(GetInfoResult result) {
		return getFund(result, getBaseCurrency());
	}

	private double getFund(GetInfoResult result, CurrencyEnum currency) {
		if (CurrencyEnum.BTC.equals(currency)) {
			return result.funds.btc;
		} else if (CurrencyEnum.ETH.equals(currency)) {
			return result.funds.eth;
		} else if (CurrencyEnum.JPY.equals(currency)) {
			return result.funds.jpy;
		}
		return 0;
	}

	private double getTargetDeposit(GetInfoResult result) {
		return getDeposit(result, getTargetCurrency());
	}

	private double getBaseDeposit(GetInfoResult result) {
		return getDeposit(result, getBaseCurrency());
	}

	private double getDeposit(GetInfoResult result, CurrencyEnum currency) {
		if (CurrencyEnum.BTC.equals(currency)) {
			return result.deposit.btc;
		} else if (CurrencyEnum.ETH.equals(currency)) {
			return result.deposit.eth;
		} else if (CurrencyEnum.JPY.equals(currency)) {
			return result.deposit.jpy;
		}
		return 0;
	}

	public static ZaifService getInstance() {
		if (instance == null) {
			instance = new ZaifService();
		}
		return instance;
	}

	private ZaifService() {
		sequenceDao = new SequenceDao();
		publicApi = new PublicApi(pair());
		key = new ApiKey(BitCoinServiceEnum.Zaif.getAccessKey(), BitCoinServiceEnum.Zaif.getSecretAccessKey());
		exchangeApi = new ExchangeApi(key);

	}

	@Override
	public TradeHistoryDto buy(double buyPrice, double amount) throws Exception {

		SequenceDao dao = new SequenceDao();

		TradeResult tradeResult = exchangeApi.trade(pair(), TradeType.BID, buyPrice, amount, dao.get());

		return TradeHistoryDto.builder().orderId(String.valueOf(tradeResult.order_id)).isSuccess(tradeResult.success)
				.errorContent(tradeResult.error_text).build();

	}

	@Override
	public TradeHistoryDto sell(double sellPrice, double amount) throws Exception {
		TradeResult tradeResult = exchangeApi.trade(pair(), TradeType.ASK, sellPrice, amount, sequenceDao.get());
		return TradeHistoryDto.builder().orderId(String.valueOf(tradeResult.order_id)).isSuccess(tradeResult.success)
				.errorContent(tradeResult.error_text).build();

	}

	@Override
	public BalanceVo getBalance() throws Exception {
		GetInfoResult result = exchangeApi.getInfo(sequenceDao.get());

		double baseDeposit = getBaseDeposit(result);
		double baseFund = getBaseFund(result);
		double targetDeposit = getTargetDeposit(result);
		double targetFund = getTargetFund(result);

		return BalanceVo.builder().balanceJpy(baseFund).balanceBtc(targetFund)
				.reserveJpy(baseDeposit - baseFund).reserveBtc(targetDeposit - targetFund)
				.build();
	}

	@Override
	public OrderBookByServiceVo getOrderBook() throws Exception {

		DepthResult depthResult = publicApi.depth();
		List<OrderVo> askOrders = new ArrayList<>();
		List<OrderVo> bidOrders = new ArrayList<>();
		for (jp.nyatla.jzaif.api.result.DepthResult.Item i : depthResult.asks) {
			askOrders.add(OrderVo.builder().price(i.price).amount(i.volume).build());
		}
		for (jp.nyatla.jzaif.api.result.DepthResult.Item i : depthResult.bids) {
			bidOrders.add(OrderVo.builder().price(i.price).amount(i.volume).build());
		}
		return OrderBookByServiceVo.builder().buyOrderBooks(askOrders).sellOrderBooks(bidOrders).build();
	}
}

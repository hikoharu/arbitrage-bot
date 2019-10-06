package arbitrage.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import arbitrage.dao.TradeHistoryDao;
import arbitrage.dto.TradeHistoryDto;
import arbitrage.service.external.BitBankService;
import arbitrage.service.external.BitCoinService;
import arbitrage.service.external.BitFlyerService;
import arbitrage.service.external.CoinCheckService;
import arbitrage.service.external.QuoineService;
import arbitrage.service.external.ZaifService;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.util.StatusEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.OrderVo;

public class BitCoinServiceHandler {

	private final int serviceId;

	public BitCoinServiceHandler(int serviceId) {
		this.serviceId = serviceId;
	}

	public OrderBookByServiceVo getOrderBook() throws Exception {
		OrderBookByServiceVo ob = getInstance().getOrderBook();
		Collections.sort(ob.getSellOrderBooks(), new sellPriceComparator());
		Collections.sort(ob.getBuyOrderBooks(), new buyPriceComparator());
		double amount = ArbitragePropertiesService.getInstance().getDealAmount();
		ob.setBuyPrice(calculatePrice(ob.getBuyOrderBooks(), amount));
		ob.setSellPrice(calculatePrice(ob.getSellOrderBooks(), amount));
		ob.setServiceId(serviceId);
		return ob;
	}

	public class buyPriceComparator implements Comparator<OrderVo> {

		public int compare(OrderVo a, OrderVo b) {
			double p1 = a.getPrice();
			double p2 = b.getPrice();

			if (p1 > p2) {
				return 1;
			} else if (p1 == p2) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public class sellPriceComparator implements Comparator<OrderVo> {

		public int compare(OrderVo a, OrderVo b) {
			double p1 = a.getPrice();
			double p2 = b.getPrice();

			if (p1 > p2) {
				return -1;
			} else if (p1 == p2) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	public BalanceVo getBalance() throws Exception {
		BalanceVo balanceDto = getInstance().getBalance();
		return balanceDto;
	};

	public TradeHistoryDto buy(long transactionId, double buyPrice, double amount) throws Exception {

		TradeHistoryDto dto = getInstance().buy(buyPrice, amount);
		dto.setTransactionId(transactionId);
		saveTradeHistory(dto);

		return dto;
	};

	public TradeHistoryDto sell(long transactionId, double sellPrice, double amount) throws Exception {
		TradeHistoryDto dto = getInstance().sell(sellPrice, amount);
		dto.setTransactionId(transactionId);
		saveTradeHistory(dto);

		return dto;

	};

	private void saveTradeHistory(TradeHistoryDto dto) {
		TradeHistoryDao dao = new TradeHistoryDao();
		dao.insert(dto);
	};

	private BitCoinService getInstance() throws Exception {

		if (serviceId == BitCoinServiceEnum.COIN_CHECK.getServiceId()) {
			return CoinCheckService.getInstance();
		} else if (serviceId == BitCoinServiceEnum.Zaif.getServiceId()) {
			return ZaifService.getInstance();
		} else if (serviceId == BitCoinServiceEnum.BIT_BANK.getServiceId()) {
			return BitBankService.getInstance();
		} else if (serviceId == BitCoinServiceEnum.QUOINE.getServiceId()) {
			return QuoineService.getInstance();
		} else if (serviceId == BitCoinServiceEnum.BIT_FLYER.getServiceId()) {
			return BitFlyerService.getInstance();
		}

		return null;

	};

	private double calculatePrice(List<OrderVo> buyOrderBooks, double orderAmount) {

		double borderAmount = orderAmount * ArbitragePropertiesService.getInstance().getDealOrderMargin();
		double canOrderAmount = 0;
		double price = 0;

		for (OrderVo order : buyOrderBooks) {
			canOrderAmount = canOrderAmount + order.getAmount();
			if (canOrderAmount >= borderAmount) {
				price = order.getPrice();
				break;
			}

		}
		return price;
	}

}

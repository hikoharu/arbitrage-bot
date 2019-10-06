package arbitrage.service.external;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.Asserts;

import com.fckey.bitcoin.bitflyer.common.Currency;

import arbitrage.dto.TradeHistoryDto;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.util.CurrencyEnum;
import arbitrage.util.PairEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.OrderVo;
import cc.bitbank.Bitbankcc;
import cc.bitbank.entity.Assets;
import cc.bitbank.entity.Depth;
import cc.bitbank.entity.Order;
import cc.bitbank.entity.enums.CurrencyPair;
import cc.bitbank.entity.enums.OrderSide;
import cc.bitbank.entity.enums.OrderType;

public class BitBankService implements BitCoinService {

	Bitbankcc bb = new Bitbankcc();

	private static BitBankService instance;

	private CurrencyPair pair() {
		if (PairEnum.BTC_JPY.equals(getPair())) {
			return CurrencyPair.BTC_JPY;
		} else if (PairEnum.ETH_BTC.equals(getPair())) {
			return CurrencyPair.ETH_BTC;
		}
		return null;
	}
	
	private String baseCurrency() {
		if (CurrencyEnum.BTC.equals(getBaseCurrency())) {
			return "btc";
		} else if (CurrencyEnum.ETH.equals(getBaseCurrency())) {
			return "eth";
		} else if (CurrencyEnum.JPY.equals(getBaseCurrency())) {
			return "jpy";
		}
		return null;
	}

	private String targetCurrency() {
		if (CurrencyEnum.BTC.equals(getTargetCurrency())) {
			return "btc";
		} else if (CurrencyEnum.ETH.equals(getTargetCurrency())) {
			return "eth";
		} else if (CurrencyEnum.JPY.equals(getTargetCurrency())) {
			return "jpy";
		}
		return null;
	}

	
	public static BitBankService getInstance() throws Exception {
		if (instance == null) {
			instance = new BitBankService();
		}
		return instance;
	}

	private BitBankService() {
		bb.setKey(BitCoinServiceEnum.BIT_BANK.getAccessKey(), BitCoinServiceEnum.BIT_BANK.getSecretAccessKey());
	}

	@Override
	public TradeHistoryDto buy(double buyPrice, double amount) throws Exception {

		Order order = bb.sendOrder(pair(), buyPrice, BigDecimal.valueOf(amount), OrderSide.BUY,
				OrderType.LIMIT);
		return makeTradeHistory(order);
	}

	private TradeHistoryDto makeTradeHistory(Order order) {
		boolean isSuccess = "UNFILLED".equals(order.status) || "PARTIALLY_FILLED".equals(order.status)
				|| "FULLY_FILLED".equals(order.status) || "CANCELED_UNFILED".equals(order.status)
				|| "CANCELED_PARTIALLY_FILLED".equals(order.status);
		return TradeHistoryDto.builder().orderId(String.valueOf(order.orderId)).isSuccess(isSuccess).build();
	}

	@Override
	public TradeHistoryDto sell(double sellPrice, double amount) throws Exception {
		Order order = bb.sendOrder(pair(), sellPrice, BigDecimal.valueOf(amount), OrderSide.SELL,
				OrderType.LIMIT);
		return makeTradeHistory(order);
	}

	@Override
	public BalanceVo getBalance() throws Exception {
		Assets assets = bb.getAsset();

		double balanceJpy = 0;
		double balanceBtc = 0;
		double reserveBalanceJpy = 0;
		double reserveBalanceBtc = 0;

		for (int i = 0; i < assets.assets.length; i++) {
			if (baseCurrency().equals(assets.assets[i].asset)) {
				balanceJpy = assets.assets[i].freeAmount.doubleValue();
				reserveBalanceJpy = assets.assets[i].lockedAmount.doubleValue();
			} else if (targetCurrency().equals(assets.assets[i].asset)) {
				balanceBtc = assets.assets[i].freeAmount.doubleValue();
				reserveBalanceBtc = assets.assets[i].lockedAmount.doubleValue();
			}
		}
		return BalanceVo.builder().balanceJpy(balanceJpy).balanceBtc(balanceBtc).reserveJpy(reserveBalanceJpy)
				.reserveBtc(reserveBalanceBtc).build();
	}

	@Override
	public OrderBookByServiceVo getOrderBook() throws Exception {

		Depth depth = bb.getDepth(pair());
		List<OrderVo> askOrders = new ArrayList<>();
		List<OrderVo> bidOrders = new ArrayList<>();
		for (int i = 0; i < depth.asks.length; i++) {
			askOrders.add(OrderVo.builder().price(depth.asks[i][0].doubleValue()).amount(depth.asks[i][1].doubleValue())
					.build());
		}
		for (int i = 0; i < depth.bids.length; i++) {
			bidOrders.add(OrderVo.builder().price(depth.bids[i][0].doubleValue()).amount(depth.bids[i][1].doubleValue())
					.build());
		}

		return OrderBookByServiceVo.builder().buyOrderBooks(askOrders).sellOrderBooks(bidOrders).build();
	}

}

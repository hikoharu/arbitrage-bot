package arbitrage.service.external;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.quoine.QuoineExchange;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.service.QuoineAccountService;
import org.knowm.xchange.quoine.service.QuoineMarketDataService;
import org.knowm.xchange.quoine.service.QuoineTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arbitrage.dto.TradeHistoryDto;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.util.CurrencyEnum;
import arbitrage.util.PairEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.OrderVo;

public class QuoineService implements BitCoinService {

	private static QuoineService instance;
	private Exchange exchangeClient;
	private static Logger log = LoggerFactory.getLogger(QuoineService.class);

	private CurrencyPair pair() {
		if (PairEnum.BTC_JPY.equals(getPair())) {
			return CurrencyPair.BTC_JPY;
		} else if (PairEnum.ETH_BTC.equals(getPair())) {
			return CurrencyPair.ETH_BTC;
		}
		return null;
	}

	private Currency baseCurrency() {
		if (CurrencyEnum.BTC.equals(getBaseCurrency())) {
			return Currency.BTC;
		} else if (CurrencyEnum.ETH.equals(getBaseCurrency())) {
			return Currency.ETH;
		} else if (CurrencyEnum.JPY.equals(getBaseCurrency())) {
			return Currency.JPY;
		}
		return null;
	}

	private Currency targetCurrency() {
		if (CurrencyEnum.BTC.equals(getTargetCurrency())) {
			return Currency.BTC;
		} else if (CurrencyEnum.ETH.equals(getTargetCurrency())) {
			return Currency.ETH;
		} else if (CurrencyEnum.JPY.equals(getTargetCurrency())) {
			return Currency.JPY;
		}
		return null;
	}
	
	public static QuoineService getInstance() throws Exception {
		if (instance == null) {
			instance = new QuoineService();
		}
		return instance;
	}

	private QuoineService() throws Exception {
		exchangeClient = ExchangeFactory.INSTANCE.createExchangeWithApiKeys("org.knowm.xchange.quoine.QuoineExchange",
				BitCoinServiceEnum.QUOINE.getAccessKey(), BitCoinServiceEnum.QUOINE.getSecretAccessKey());
		exchangeClient.getExchangeSpecification().setExchangeSpecificParametersItem(QuoineExchange.KEY_TOKEN_ID,
				exchangeClient.getExchangeSpecification().getApiKey());
		exchangeClient.getExchangeSpecification().setExchangeSpecificParametersItem(QuoineExchange.KEY_USER_SECRET,
				exchangeClient.getExchangeSpecification().getSecretKey());
	}

	@Override
	public TradeHistoryDto buy(double buyPrice, double amount) throws Exception {
		QuoineTradeService trade = new QuoineTradeService(exchangeClient, false);
		QuoineOrderResponse o = trade.placeLimitOrder(pair(), "buy", BigDecimal.valueOf(amount),
				BigDecimal.valueOf(buyPrice));
		// statusが正しく取引されてもtrueにならないのでログを出す
		log.info(o.toString());
		return TradeHistoryDto.builder().orderId(o.getId()).isSuccess(true).build();
	}

	@Override
	public TradeHistoryDto sell(double sellPrice, double amount) throws Exception {
		QuoineTradeService trade = new QuoineTradeService(exchangeClient, false);
		QuoineOrderResponse o = trade.placeLimitOrder(pair(), "sell", BigDecimal.valueOf(amount),
				BigDecimal.valueOf(sellPrice));
		// statusが正しく取引されてもtrueにならないのでログを出す
		log.info(o.toString());
		return TradeHistoryDto.builder().orderId(o.getId()).isSuccess(true).build();
	}

	@Override
	public BalanceVo getBalance() throws Exception {

		QuoineAccountService q = new QuoineAccountService((QuoineExchange) exchangeClient, false);
		AccountInfo a = q.getAccountInfo();
		QuoineTradeService trade = new QuoineTradeService(exchangeClient, false);
		OpenOrders orders = trade.getOpenOrders();

		double balanceJpy = a.getWallet().getBalance(baseCurrency()).getAvailable().doubleValue();
		double balanceBtc = a.getWallet().getBalance(targetCurrency()).getAvailable().doubleValue();

		double frozenJpy = orders.getOpenOrders().stream()
				.filter(o -> o.getCurrencyPair().equals(pair()) && o.getType().equals(OrderType.BID))
				.mapToDouble(o -> o.getLimitPrice().multiply(o.getTradableAmount()).doubleValue()).sum();

		double frozenBtc = orders.getOpenOrders().stream()
				.filter(o -> o.getCurrencyPair().equals(pair()) && o.getType().equals(OrderType.ASK))
				.mapToDouble(o -> o.getTradableAmount().doubleValue()).sum();

		return BalanceVo.builder().balanceJpy(balanceJpy - frozenJpy).balanceBtc(balanceBtc - frozenBtc)
				.reserveJpy(frozenJpy).reserveBtc(frozenBtc).build();
	}

	@Override
	public OrderBookByServiceVo getOrderBook() throws Exception {

		QuoineMarketDataService q = new QuoineMarketDataService(exchangeClient);
		OrderBook s = q.getOrderBook(pair());

		List<LimitOrder> asks = s.getAsks();
		List<LimitOrder> bids = s.getBids();
		List<OrderVo> askOrders = new ArrayList<>();
		List<OrderVo> bidOrders = new ArrayList<>();
		for (LimitOrder a : asks) {
			askOrders.add(OrderVo.builder().price(a.getLimitPrice().doubleValue())
					.amount(a.getTradableAmount().doubleValue()).build());
		}
		for (LimitOrder b : bids) {
			bidOrders.add(OrderVo.builder().price(b.getLimitPrice().doubleValue())
					.amount(b.getTradableAmount().doubleValue()).build());
		}

		return OrderBookByServiceVo.builder().buyOrderBooks(askOrders).sellOrderBooks(bidOrders).build();
	}

}

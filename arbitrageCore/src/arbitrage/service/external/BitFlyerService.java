package arbitrage.service.external;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fckey.bitcoin.bitflyer.api.BitflyerRequestHeaderFactory;
import com.fckey.bitcoin.bitflyer.api.PrivateApiClient;
import com.fckey.bitcoin.bitflyer.api.PublicApiClient;
import com.fckey.bitcoin.bitflyer.common.Currency;
import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.TimeInForce;
import com.fckey.bitcoin.bitflyer.model.Board;
import com.fckey.bitcoin.bitflyer.model.Board.Order;
import com.fckey.bitcoin.bitflyer.model.order.ChildOrder;
import com.fckey.bitcoin.bitflyer.model.order.response.OrderResponse;
import com.fckey.bitcoin.bitflyer.model.order.state.ChildOrderState;
import com.fckey.bitcoin.core.api.AccessParams;
import com.fckey.bitcoin.core.common.BuySell;

import arbitrage.dto.TradeHistoryDto;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.util.CurrencyEnum;
import arbitrage.util.PairEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.OrderVo;

public class BitFlyerService implements BitCoinService {

	private static BitFlyerService instance;
	private PrivateApiClient privateClient;
	private PublicApiClient publicClient;
	private static Logger log = LoggerFactory.getLogger(BitFlyerService.class);

	private ProductCode pair() {
		if (PairEnum.BTC_JPY.equals(getPair())) {
			return ProductCode.BTC_JPY;
		} else if (PairEnum.ETH_BTC.equals(getPair())) {
			return ProductCode.ETH_BTC;
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

	public static BitFlyerService getInstance() throws Exception {
		if (instance == null) {
			instance = new BitFlyerService();
		}
		return instance;
	}

	private BitFlyerService() throws Exception {
		BitflyerRequestHeaderFactory.init(new AccessParams(BitCoinServiceEnum.BIT_FLYER.getAccessKey(),
				BitCoinServiceEnum.BIT_FLYER.getSecretAccessKey()));
		privateClient = PrivateApiClient.getInstance();
		publicClient = PublicApiClient.getInstance();
	}

	@Override
	public TradeHistoryDto buy(double buyPrice, double amount) throws Exception {
		ChildOrder childOrder = new ChildOrder(pair(), OrderType.LIMIT, BuySell.BUY, buyPrice, amount, 43200,
				TimeInForce.GTC);
		OrderResponse order = privateClient.sendChildOrder(childOrder);
		// statusが正しく取引されてもtrueにならないのでログを出す
		log.info(order.toString());
		// こちらにrequestが返ってきてから非同期処理で注文を執行しているようなので、一旦待つ
		TimeUnit.SECONDS.sleep(3);
		// なぜか200で返ってきても注文されていない時があるので、orederをチェック
		boolean isSuccess = checkOrderState(pair(), order.getResponseId());
		return TradeHistoryDto.builder().isSuccess(isSuccess).orderId(order.getResponseId()).isSuccess(true).build();
	}

	@Override
	public TradeHistoryDto sell(double sellPrice, double amount) throws Exception {
		ChildOrder childOrder = new ChildOrder(pair(), OrderType.LIMIT, BuySell.SELL, sellPrice, amount, 43200,
				TimeInForce.GTC);
		OrderResponse order = privateClient.sendChildOrder(childOrder);
		// statusが正しく取引されてもtrueにならないのでログを出す
		log.info(order.toString());
		TimeUnit.SECONDS.sleep(3);
		boolean isSuccess = checkOrderState(pair(), order.getResponseId());
		return TradeHistoryDto.builder().isSuccess(isSuccess).orderId(order.getResponseId()).isSuccess(true).build();
	}

	@Override
	public BalanceVo getBalance() throws Exception {
		List<com.fckey.bitcoin.bitflyer.model.Balance> balance = privateClient.getBalance();

		com.fckey.bitcoin.bitflyer.model.Balance balanceJpy = balance.stream()
				.filter(s -> baseCurrency().toString().equals(s.getCurrency())).findFirst().get();

		com.fckey.bitcoin.bitflyer.model.Balance balanceBtc = balance.stream()
				.filter(s -> targetCurrency().toString().equals(s.getCurrency())).findFirst().get();

		return BalanceVo.builder().balanceJpy(balanceJpy.getAvailable()).balanceBtc(balanceBtc.getAvailable())
				.reserveJpy(balanceJpy.getAmount() - balanceJpy.getAvailable())
				.reserveBtc(balanceBtc.getAmount() - balanceBtc.getAvailable()).build();

	}

	@Override
	public OrderBookByServiceVo getOrderBook() throws Exception {
		Board board = publicClient.getBoard(pair());
		List<Order> asks = board.getAsks();
		List<Order> bids = board.getBids();
		List<OrderVo> askOrders = new ArrayList<>();
		List<OrderVo> bidOrders = new ArrayList<>();
		for (Order a : asks) {
			askOrders.add(OrderVo.builder().price(a.getPrice()).amount(a.getSize()).build());
		}
		for (Order b : bids) {
			bidOrders.add(OrderVo.builder().price(b.getPrice()).amount(b.getSize()).build());
		}

		return OrderBookByServiceVo.builder().buyOrderBooks(askOrders).sellOrderBooks(bidOrders).build();

	}

	public boolean checkOrderState(ProductCode productCode, String childOrderAcceptanceId) throws IOException {

		List<ChildOrderState> list = privateClient.getChildOrderStates(productCode, childOrderAcceptanceId);
		return list != null && !list.isEmpty();
	}

}

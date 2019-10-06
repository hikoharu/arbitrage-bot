package arbitrage.service.external;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arbitrage.dto.TradeHistoryDto;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.OrderVo;
import coincheck.CoinCheck;

public class CoinCheckService implements BitCoinService {

	private CoinCheck client;
	private static CoinCheckService instance;

	public static CoinCheckService getInstance() throws Exception {
		if (instance == null) {
			instance = new CoinCheckService();
		}
		return instance;
	}

	private CoinCheckService() throws Exception {
		client = new CoinCheck(BitCoinServiceEnum.COIN_CHECK.getAccessKey(),
				BitCoinServiceEnum.COIN_CHECK.getSecretAccessKey());
	}

	@Override
	public TradeHistoryDto buy(double buyPrice, double amount) throws Exception {

		JSONObject result = null;

		JSONObject orderObj = new JSONObject();
		orderObj.put("rate", buyPrice);
		orderObj.put("amount", amount);
		orderObj.put("order_type", "buy");
		orderObj.put("pair", "btc_jpy");
		result = client.order().create(orderObj);
		int id = 0;
		try {
			id = result.getInt("id");
		} catch (JSONException e) {
		}

		return TradeHistoryDto.builder().orderId(String.valueOf(id)).isSuccess(result.getBoolean("success")).build();

	}

	@Override
	public TradeHistoryDto sell(double sellPrice, double amount) throws Exception {

		JSONObject result = null;

		JSONObject sell = new JSONObject();
		sell.put("rate", sellPrice);
		sell.put("amount", amount);
		sell.put("order_type", "sell");
		sell.put("pair", "btc_jpy");
		result = client.order().create(sell);

		return makeTradeHistoryDto(result);

	}

	private TradeHistoryDto makeTradeHistoryDto(JSONObject result) {
		int id = 0;
		try {
			id = result.getInt("id");
		} catch (JSONException e) {
		}
		return TradeHistoryDto.builder().orderId(String.valueOf(id)).isSuccess(result.getBoolean("success")).build();

	}

	@Override
	public BalanceVo getBalance() throws Exception {

		double balanceJpy = 0;
		double balanceBtc = 0;
		double reserveBalanceJpy = 0;
		double reserveBalanceBtc = 0;
		JSONObject result = client.account().balance();
		try {
			balanceJpy = result.getDouble("jpy");
			balanceBtc = result.getDouble("btc");
			reserveBalanceJpy = result.getDouble("jpy_reserved");
			reserveBalanceBtc = result.getDouble("btc_reserved");
		} catch (JSONException e) {
		}
		return BalanceVo.builder().balanceJpy(balanceJpy).balanceBtc(balanceBtc).reserveJpy(reserveBalanceJpy)
				.reserveBtc(reserveBalanceBtc).build();
	}

	@Override
	public OrderBookByServiceVo getOrderBook() throws Exception {

		JSONObject ob = client.orderBook().all();
		JSONArray asks = ob.getJSONArray("asks");
		JSONArray bids = ob.getJSONArray("bids");
		List<OrderVo> askOrders = new ArrayList<>();
		List<OrderVo> bidOrders = new ArrayList<>();
		for (int i = 0; i < asks.length(); i++) {
			JSONArray ask = asks.getJSONArray(i);
			askOrders.add(OrderVo.builder().price(ask.getDouble(0)).amount(ask.getDouble(1)).build());
		}
		for (int i = 0; i < bids.length(); i++) {
			JSONArray bid = bids.getJSONArray(i);
			bidOrders.add(OrderVo.builder().price(bid.getDouble(0)).amount(bid.getDouble(1)).build());
		}

		return OrderBookByServiceVo.builder().buyOrderBooks(askOrders).sellOrderBooks(bidOrders).build();
	}

}

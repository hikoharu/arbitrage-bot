package test;

import org.junit.Assert;
import org.junit.Test;

import arbitrage.dao.PriceHistoryDao;
import arbitrage.dto.TradeHistoryDto;
import arbitrage.service.BitCoinServiceHandler;
import arbitrage.util.BitCoinServiceEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;
import coincheck.OrderBook;

public class ApiTest {

	@Test
	public void sqliteTest() throws Exception {
		// PriceHistoryDao dao = new PriceHistoryDao();
		// dao.insert(1, OrderBookByServiceVo.builder().build());
	}

	@Test
	public void bitFlyerTest() throws Exception {

		// BitCoinServiceHandler bitCoinService = new
		// BitCoinServiceHandler(BitCoinServiceEnum.Zaif.getServiceId());
		// bitCoinService.buy(0, 400000, 0.1);
		// TradeHistoryDto buyResult = bitCoinService.buy(1, 322640, 0.1);
		// TradeHistoryDto sellResult = bitCoinService.sell(1, 452640, 0.1);
		// Assert.assertTrue(buyResult.isSuccess());
		// Assert.assertTrue(sellResult.isSuccess());

	}

	@Test
	public void balanceTest() throws Exception {
		getBalance(BitCoinServiceEnum.Zaif);
		getOrder(BitCoinServiceEnum.Zaif);
		getBalance(BitCoinServiceEnum.BIT_BANK);
		getOrder(BitCoinServiceEnum.BIT_BANK);
		getBalance(BitCoinServiceEnum.QUOINE);
		getOrder(BitCoinServiceEnum.QUOINE);
		getBalance(BitCoinServiceEnum.BIT_FLYER);
		getOrder(BitCoinServiceEnum.BIT_FLYER);
	}

	@Test
	public void deailFeeTest() {

		for (BitCoinServiceEnum e : BitCoinServiceEnum.values()) {
			System.out.println(e.getDealFee());
		}
	}

	@Test
	public void buyTest() throws Exception {
		// buy(BitCoinServiceEnum.BIT_FLYER);
		// sell(BitCoinServiceEnum.BIT_FLYER);
	}

	private void getBalance(BitCoinServiceEnum bs) throws Exception {
		BitCoinServiceHandler bitCoinServiceHandler = new BitCoinServiceHandler(bs.getServiceId());
		BalanceVo b = bitCoinServiceHandler.getBalance();
		System.out.println(b);
	}

	private void getOrder(BitCoinServiceEnum bs) throws Exception {
		BitCoinServiceHandler bitCoinServiceHandler = new BitCoinServiceHandler(bs.getServiceId());
		OrderBookByServiceVo b = bitCoinServiceHandler.getOrderBook();
		System.out.println(b);
	}

	private void buy(BitCoinServiceEnum bs) throws Exception {
		BitCoinServiceHandler bitCoinServiceHandler = new BitCoinServiceHandler(bs.getServiceId());
		TradeHistoryDto dto = bitCoinServiceHandler.buy(0, 0.09, 0.01);
		System.out.println(dto);
	}

	private void sell(BitCoinServiceEnum bs) throws Exception {
		BitCoinServiceHandler bitCoinServiceHandler = new BitCoinServiceHandler(bs.getServiceId());
		TradeHistoryDto dto = bitCoinServiceHandler.sell(0, 0.11, 0.01);
		System.out.println(dto);
	}

}

package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import arbitrage.service.ArbitrageCollectorService;
import arbitrage.vo.ArbitrageTargetVo;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;

public class dealArbitrageTest {

	@Test
	public void test() throws Exception {

		List<OrderBookByServiceVo> list = new ArrayList<>();

		BalanceVo normalBalance = BalanceVo.builder().balanceJpy(300000).balanceBtc(0.1).build();
		BalanceVo shortageJpyBalance = BalanceVo.builder().balanceJpy(0).balanceBtc(0.1).build();
		BalanceVo shortageBtcBalance = BalanceVo.builder().balanceJpy(300000).balanceBtc(0).build();
		list.add(OrderBookByServiceVo.builder().serviceId(1).buyPrice(310000).sellPrice(300000).balance(shortageBtcBalance)
				.build());
		list.add(OrderBookByServiceVo.builder().serviceId(2).buyPrice(309900).sellPrice(300000).balance(shortageJpyBalance)
				.build());
		list.add(OrderBookByServiceVo.builder().serviceId(3).buyPrice(309850).sellPrice(300000).balance(shortageJpyBalance)
				.build());
		list.add(OrderBookByServiceVo.builder().serviceId(4).buyPrice(310000).sellPrice(310001).balance(normalBalance)
				.build());
		ArbitrageCollectorService dealService = new ArbitrageCollectorService();
		ArbitrageTargetVo result = dealService.createArbitrageData(list);
		System.out.println("isCanExecute:" + result.isCanExecute());
		System.out.println("askTarget:" + result.getAskTarget() + " " + "bidTarget" + result.getBidTarget());
	}
}

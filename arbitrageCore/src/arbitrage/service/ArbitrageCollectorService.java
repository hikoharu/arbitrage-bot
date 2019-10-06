package arbitrage.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arbitrage.util.BitCoinServiceEnum;
import arbitrage.util.BitCoinUtil;
import arbitrage.vo.ArbitrageTargetVo;
import arbitrage.vo.OrderBookByServiceVo;
import arbitrage.vo.ProfitVo;

public class ArbitrageCollectorService {

	private static Logger log = LoggerFactory.getLogger(ArbitrageCollectorService.class);

	public ArbitrageTargetVo getTarget() throws Exception {

		List<OrderBookByServiceVo> orderBookList = new ArrayList<>();
		for (BitCoinServiceEnum b : BitCoinServiceEnum.values()) {

			if(b.getAccessKey().isEmpty()){
				continue;
			}
			try {
				log.info(LocalDateTime.now() + "Service:" + BitCoinUtil.getServiceCaption(b.getServiceId())
						+ "  start checkPrice");
				BitCoinServiceHandler bitCoinServiceHandler = new BitCoinServiceHandler(b.getServiceId());
				OrderBookByServiceVo orderVo = bitCoinServiceHandler.getOrderBook();
				orderVo.setBalance(bitCoinServiceHandler.getBalance());
				orderBookList.add(orderVo);
				log.info(LocalDateTime.now() + "Service:" + BitCoinUtil.getServiceCaption(b.getServiceId())
						+ "  end checkPrice");
			} catch (Exception e) {
				e.printStackTrace();
				// サイトが高負荷など、特定のサービスで疎通ができないときがあるので、その時はそれを無視して処理続行
			}
		}
		return createArbitrageData(orderBookList);

	}

	public ArbitrageTargetVo createArbitrageData(List<OrderBookByServiceVo> orderBookList) {

		List<ProfitVo> profitsList = calcProfits(orderBookList);
		ProfitVo target = getDealTarget(profitsList, ArbitragePropertiesService.getInstance().getDealDifference(),
				false);
		// 基本のロジック、決めているサヤ以上ならここを通る
		if (target != null) {
			ArbitrageTargetVo result = createArbitrageTarget(orderBookList, target);
			result.setProfitsList(profitsList.stream().filter(p -> p.getProfit() > 0).collect(Collectors.toList()));
			log.info("[normal mode]difference:" + result.getDifference() + "  " + "askService:"
					+ BitCoinUtil.getServiceCaption(result.getAskTarget().getServiceId()) + "  " + "bidService:"
					+ BitCoinUtil.getServiceCaption(result.getBidTarget().getServiceId()));
			result.setCanExecute(true);
			return result;
		}
		// 予備ロジック、サヤが０円以上かつ、バランスを崩さない場合は取引する。
		target = getDealTarget(profitsList, ArbitragePropertiesService.getInstance().getKeepBalanceDifference(), true);
		if (target != null) {
			ArbitrageTargetVo result = createArbitrageTarget(orderBookList, target);
			result.setProfitsList(profitsList.stream().filter(p -> p.getProfit() > 0).collect(Collectors.toList()));
			log.info("[compromise mode]difference:" + result.getDifference() + "  " + "askService:"
					+ BitCoinUtil.getServiceCaption(result.getAskTarget().getServiceId()) + "  " + "bidService:"
					+ BitCoinUtil.getServiceCaption(result.getBidTarget().getServiceId()));
			result.setCanExecute(true);
			return result;
		}

		// 取引できない場合は最も差分のあるものをDBに入れる
		ArbitrageTargetVo result = ArbitrageTargetVo.builder().askTarget(profitsList.get(0).getAskOrderBook())
				.bidTarget(profitsList.get(0).getBidOrderBook()).orderBookList(orderBookList)
				.difference(profitsList.get(0).getProfit()).canExecute(false).build();
		result.setProfitsList(profitsList.stream().filter(p -> p.getProfit() > 0).collect(Collectors.toList()));
		log.info("[not deal]difference:" + result.getDifference() + "  " + "askService:"
				+ BitCoinUtil.getServiceCaption(result.getAskTarget().getServiceId()) + "  " + "bidService:"
				+ BitCoinUtil.getServiceCaption(result.getBidTarget().getServiceId()));
		result.setCanExecute(false);
		return result;

	}

	private List<ProfitVo> calcProfits(List<OrderBookByServiceVo> orderBookList) {
		List<ProfitVo> profitsList = new ArrayList<ProfitVo>();
		for (OrderBookByServiceVo askOrderBook : orderBookList) {
			for (OrderBookByServiceVo bidOrderBook : orderBookList) {
				double dealFee = getDealFee(askOrderBook, bidOrderBook);
				double difference = getDifference(askOrderBook, bidOrderBook);
				if (difference != 0) {
					profitsList.add(
							ProfitVo.builder().askOrderBook(askOrderBook).bidOrderBook(bidOrderBook).dealFee(dealFee)
									.difference(difference).askJpyAmount(askOrderBook.getBalance().getBalanceJpy())
									.askBtcAmount(askOrderBook.getBalance().getBalanceBtc())
									.bidJpyAmount(bidOrderBook.getBalance().getBalanceJpy())
									.bidBtcAmount(bidOrderBook.getBalance().getBalanceBtc())
									.profit(difference - dealFee).build());

				}
			}
		}
		Collections.sort(profitsList, new profitComparator());
		profitsList.stream()
				.forEach(p -> log.info("askService:" + BitCoinUtil.getServiceCaption(p.getAskOrderBook().getServiceId())
						+ "  " + "bidService:" + BitCoinUtil.getServiceCaption(p.getBidOrderBook().getServiceId())
						+ "  " + "profit:" + p.getProfit()));
		return profitsList;
	}

	public class profitComparator implements Comparator<ProfitVo> {

		public int compare(ProfitVo a, ProfitVo b) {
			double p1 = a.getProfit();
			double p2 = b.getProfit();

			if (p1 < p2) {
				return 1;
			} else if (p1 == p2) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	private ArbitrageTargetVo createArbitrageTarget(List<OrderBookByServiceVo> orderBookList, ProfitVo target) {
		OrderBookByServiceVo buyTarget = target.getAskOrderBook();
		OrderBookByServiceVo sellTarget = target.getBidOrderBook();
		double profit = target.getProfit();
		ArbitrageTargetVo result = ArbitrageTargetVo.builder().askTarget(buyTarget).bidTarget(sellTarget)
				.orderBookList(orderBookList).difference(profit).canExecute(false).build();
		return result;
	}

	private ProfitVo getDealTarget(List<ProfitVo> profitsList, double difference, boolean isCharge) {
		ProfitVo target = null;
		double amount = ArbitragePropertiesService.getInstance().getDealAmount();
		for (ProfitVo profit : profitsList) {
			if (profit.getProfit() >= difference) {
				if (canBuy(profit.getAskOrderBook(), amount) && canSell(profit.getBidOrderBook(), amount)) {
					if (isCharge) {

						BigDecimal askAmount = new BigDecimal(profit.getAskBtcAmount());
						askAmount = askAmount.setScale(1, BigDecimal.ROUND_DOWN);
						BigDecimal bidAmount = new BigDecimal(profit.getBidBtcAmount());
						bidAmount = bidAmount.setScale(1, BigDecimal.ROUND_DOWN);

						if (bidAmount.compareTo(askAmount) > 0
								&& !(profit.getAskOrderBook().getBalance().getBalanceBtc() >= amount
										* ArbitragePropertiesService.getInstance().getDealStopperAmount())) {
							target = profit;
							return target;
						}
					} else {
						target = profit;
						return target;
					}
				}
			}
		}
		return null;
	}

	private double getDifference(OrderBookByServiceVo buyTarget, OrderBookByServiceVo sellTarget) {
		if (buyTarget.getBuyPrice() != 0 && sellTarget.getSellPrice() != 0) {
			return sellTarget.getSellPrice() - buyTarget.getBuyPrice();
		}
		return 0;
	}

	private double getDealFee(OrderBookByServiceVo buyTarget, OrderBookByServiceVo sellTarget) {
		double buyCost = buyTarget.getBuyPrice() * getDealFee(buyTarget.getServiceId());
		double sellCost = sellTarget.getSellPrice() * getDealFee(sellTarget.getServiceId());
		return buyCost + sellCost;
	}

	private double getDealFee(int serviceId) {

		for (BitCoinServiceEnum e : BitCoinServiceEnum.values()) {
			if (serviceId == e.getServiceId()) {
				return e.getDealFee();
			}
		}

		return 0;
	}

	private boolean canSell(OrderBookByServiceVo price, double amount) {

		return price.getBalance().getBalanceBtc() >= amount;
	}

	private boolean canBuy(OrderBookByServiceVo price, double amount) {

		double buyPrice = price.getBuyPrice();
		double needJpy = buyPrice * amount;

		return price.getBalance().getBalanceJpy() >= needJpy;

	}

}

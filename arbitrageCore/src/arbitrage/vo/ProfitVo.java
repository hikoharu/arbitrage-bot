package arbitrage.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfitVo {
	private OrderBookByServiceVo askOrderBook;
	private OrderBookByServiceVo bidOrderBook;
	private double dealFee;
	private double difference;
	private double askBtcAmount;
	private double askJpyAmount;
	private double bidBtcAmount;
	private double bidJpyAmount;
	private double profit;

}

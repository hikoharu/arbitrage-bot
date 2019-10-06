package arbitrage.vo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderBookByServiceVo {

	private int serviceId;
	private List<OrderVo> buyOrderBooks;
	private List<OrderVo> sellOrderBooks;
	private double buyPrice;
	private double sellPrice;
	private BalanceVo balance;
	
}

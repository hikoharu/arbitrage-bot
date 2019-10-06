package arbitrage.vo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArbitrageTargetVo {

	OrderBookByServiceVo askTarget;
	OrderBookByServiceVo bidTarget;
	List<OrderBookByServiceVo> orderBookList;
	List<ProfitVo> profitsList;
	double difference;
	boolean canExecute;
}

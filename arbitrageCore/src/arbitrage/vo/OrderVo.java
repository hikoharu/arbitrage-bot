package arbitrage.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderVo {

	private double price;
	private double amount;
}

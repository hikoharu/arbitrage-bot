package arbitrage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArbitrageTransactionDto {

	private long id;
	private int askServiceId;
	private int bidServiceId;
	private String currency;
	private double amount;
	private double askAmount;
	private double bidAmount;
	private double difference;

}

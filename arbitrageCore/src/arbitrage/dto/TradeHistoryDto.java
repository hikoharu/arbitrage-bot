package arbitrage.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TradeHistoryDto {

	long transactionId;
	String orderId;
	boolean isSuccess;
	String errorContent;

}

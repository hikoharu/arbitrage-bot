package arbitrage.dto;


import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BalanceDto {

	long id;
	int serviceId;
	Date date;
	int hour;
	double balanceJPY;
	double balanceBTC;
	double reserveBalanceJPY;
	double reserveBalanceBTC;
	double rate;
	double total;
	
}

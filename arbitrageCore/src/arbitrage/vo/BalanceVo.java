package arbitrage.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BalanceVo {

	private double balanceJpy;
	private double balanceBtc;
	private double reserveJpy;
	private double reserveBtc;
}

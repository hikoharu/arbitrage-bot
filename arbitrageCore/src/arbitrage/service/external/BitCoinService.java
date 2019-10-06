package arbitrage.service.external;

import arbitrage.dto.TradeHistoryDto;
import arbitrage.service.ArbitragePropertiesService;
import arbitrage.util.CurrencyEnum;
import arbitrage.util.PairEnum;
import arbitrage.vo.BalanceVo;
import arbitrage.vo.OrderBookByServiceVo;

public interface BitCoinService {

	TradeHistoryDto buy(double buyPrice, double amount) throws Exception;

	TradeHistoryDto sell(double sellPrice, double amount) throws Exception;

	BalanceVo getBalance() throws Exception;

	OrderBookByServiceVo getOrderBook() throws Exception;
	
	default PairEnum getPair(){
		return ArbitragePropertiesService.getInstance().getPair();
	}
	
	default CurrencyEnum getBaseCurrency(){
		return ArbitragePropertiesService.getInstance().getPair().getBase();
	}
	
	default CurrencyEnum getTargetCurrency(){
		return ArbitragePropertiesService.getInstance().getPair().getTarget();
	}
}

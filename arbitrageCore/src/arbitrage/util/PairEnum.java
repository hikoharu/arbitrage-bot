package arbitrage.util;

public enum PairEnum {
	BTC_JPY(CurrencyEnum.BTC, CurrencyEnum.JPY), ETH_BTC(CurrencyEnum.ETH, CurrencyEnum.BTC);

	CurrencyEnum targetCurrency;
	CurrencyEnum baseCurrency;
	
	private PairEnum(CurrencyEnum target, CurrencyEnum base) {
		this.targetCurrency = target;
		this.baseCurrency = base;
	}

	public CurrencyEnum getTarget() {
		return targetCurrency;
	}

	public CurrencyEnum getBase() {
		return baseCurrency;
	}
}
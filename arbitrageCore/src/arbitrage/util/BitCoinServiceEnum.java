package arbitrage.util;

import arbitrage.service.ArbitragePropertiesService;
import arbitrage.vo.ArbitrageTargetVo;

public enum BitCoinServiceEnum {

	// TODO propertiesにサービスを使うかどうかのフラグ持たせる
	COIN_CHECK(1, "CoinCheck", ArbitragePropertiesService.getInstance().getCoinCheckAccessKey(),
			ArbitragePropertiesService.getInstance().getCoinCheckSecretAccessKey(),
			ArbitragePropertiesService.getInstance().getCoinCheckDealFee()), Zaif(2, "Zaif",
					ArbitragePropertiesService.getInstance().getZaifAccessKey(),
					ArbitragePropertiesService.getInstance().getZaifSecretAccessKey(),
					ArbitragePropertiesService.getInstance().getZaifDealFee()), BIT_BANK(3, "BitBank",
							ArbitragePropertiesService.getInstance().getBitBankAccessKey(),
							ArbitragePropertiesService.getInstance().getBitBankSecretAccessKey(),
							ArbitragePropertiesService.getInstance().getBitBankDealFee()), QUOINE(4, "Quoine",
									ArbitragePropertiesService.getInstance().getQuoineAccessKey(),
									ArbitragePropertiesService.getInstance().getQuoineSecretAccessKey(),
									ArbitragePropertiesService.getInstance().getQuoineDealFee()), BIT_FLYER(5,
											"BitFlyer", ArbitragePropertiesService.getInstance().getBitFlyerAccessKey(),
											ArbitragePropertiesService.getInstance().getBitFlyerSecretAccessKey(),
											ArbitragePropertiesService.getInstance().getBitFlyerDealFee());// ,BTC_BOX(3,"BtcBox","","","",0.001);

	private int serviceId;
	private String serviceCaption;
	private String accessKey;
	private String secretAccessKey;
	private double dealFee;

	private BitCoinServiceEnum(int serviceId, String serviceCaption, String accessKey, String secretAccessKey,
			double dealFee) {
		this.serviceId = serviceId;
		this.serviceCaption = serviceCaption;
		this.accessKey = accessKey;
		this.secretAccessKey = secretAccessKey;
		this.dealFee = dealFee;
	}

	public int getServiceId() {
		return serviceId;
	}

	public String getServiceCaption() {
		return serviceCaption;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public double getDealFee() {
		return dealFee;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

}

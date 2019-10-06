package arbitrage.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import arbitrage.util.CurrencyEnum;
import arbitrage.util.PairEnum;

public class ArbitragePropertiesService {

	private Properties properties = new Properties();
	private static ArbitragePropertiesService instance;

	public static ArbitragePropertiesService getInstance() {
		if (instance == null) {
			instance = new ArbitragePropertiesService();
		}
		return instance;
	}

	public ArbitragePropertiesService() {

		String file = "arbitrage.properties";
		try {
			InputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);
			inputStream.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public String getSQLiteFilePath() {
		return properties.getProperty("sqLiteFilePath");
	}

	public String getDbHost() {
		return properties.getProperty("dbHost");
	}

	public String getDbUser() {
		return properties.getProperty("dbUser");
	}

	public String getDbPass() {
		return properties.getProperty("dbPass");
	}

	public double getDealAmount() {
		return Double.valueOf(properties.getProperty("dealAmount"));
	}

	public double getDealDifference() {
		return Double.valueOf(properties.getProperty("dealDifference"));
	}

	public double getKeepBalanceDifference() {
		return Double.valueOf(properties.getProperty("keepBalanceDifference"));
	}

	public int getDealOrderMargin() {
		return Integer.valueOf(properties.getProperty("dealOrderMargin"));
	}

	public int getDealStopperAmount() {
		return Integer.valueOf(properties.getProperty("dealStopperAmount"));
	}

	public String getCoinCheckAccessKey() {
		return properties.getProperty("coinCheckAccessKey");
	}

	public String getCoinCheckSecretAccessKey() {
		return properties.getProperty("coinCheckSecretAccessKey");
	}

	public String getZaifAccessKey() {
		return properties.getProperty("zaifAccessKey");
	}

	public String getZaifSecretAccessKey() {
		return properties.getProperty("zaifSecretAccessKey");
	}

	public String getBitBankAccessKey() {
		return properties.getProperty("bitBankAccessKey");
	}

	public String getBitBankSecretAccessKey() {
		return properties.getProperty("bitBankSecretAccessKey");
	}

	public String getQuoineAccessKey() {
		return properties.getProperty("quoineAccessKey");
	}

	public String getBitFlyerSecretAccessKey() {
		return properties.getProperty("bitFlyerSecretAccessKey");
	}

	public String getBitFlyerAccessKey() {
		return properties.getProperty("bitFlyerAccessKey");
	}

	public String getQuoineSecretAccessKey() {
		return properties.getProperty("quoineSecretAccessKey");
	}

	public boolean canExecute() {
		return Boolean.valueOf(properties.getProperty("canExecute"));
	}

	public PairEnum getPair() {
		return PairEnum.valueOf(properties.getProperty("pair"));
	}
	
	public double getCoinCheckDealFee() {
		return Double.valueOf(properties.getProperty("coinCheckDealFee"));
	}
	public double getZaifDealFee() {
		return Double.valueOf(properties.getProperty("zaifDealFee"));
	}
	public double getBitBankDealFee() {
		return Double.valueOf(properties.getProperty("bitBankDealFee"));
	}
	public double getQuoineDealFee() {
		return Double.valueOf(properties.getProperty("quoineDealFee"));
	}
	public double getBitFlyerDealFee() {
		return Double.valueOf(properties.getProperty("bitflyerDealFee"));
	}
}

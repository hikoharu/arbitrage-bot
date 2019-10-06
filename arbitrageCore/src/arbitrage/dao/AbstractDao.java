package arbitrage.dao;

import arbitrage.service.ArbitragePropertiesService;

public class AbstractDao {

	protected final String host;
	protected final String user;
	protected final String pass;
	protected final String sqLitePath;

	public AbstractDao() {
		ArbitragePropertiesService a = ArbitragePropertiesService.getInstance();
		host = a.getDbHost();
		user = a.getDbUser();
		pass = a.getDbPass();
		sqLitePath = "jdbc:sqlite:"+a.getSQLiteFilePath();
	}

}

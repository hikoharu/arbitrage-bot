package arbitrage.main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arbitrage.dao.PriceHistoryDao;
import arbitrage.service.ArbitrageCollectorService;
import arbitrage.service.ArbitragePropertiesService;
import arbitrage.vo.ArbitrageTargetVo;

public class TmplArbitrageExecutorSQLite {
	private static Logger log = LoggerFactory.getLogger(ArbitrageExecutor.class);

	public static void main(String[] args) throws Exception {

		final Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					ArbitrageCollectorService dealService = new ArbitrageCollectorService();
					ArbitrageTargetVo arbitrageTargetDto = dealService.getTarget();
					ArbitragePropertiesService a = ArbitragePropertiesService.getInstance();
					if (arbitrageTargetDto != null) {						
						PriceHistoryDao priceHistoryDao4SQLite = new PriceHistoryDao();
						arbitrageTargetDto.getOrderBookList().stream().forEach(p -> priceHistoryDao4SQLite.insert(0, p));		
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					pw.flush();
					log.info(sw.toString());
					e.printStackTrace();
				}

			}

		}, 0, 36000);

	}

}

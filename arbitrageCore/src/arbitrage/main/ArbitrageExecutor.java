package arbitrage.main;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arbitrage.dao.PriceHistoryDao4MySql;
import arbitrage.service.ArbitrageCollectorService;
import arbitrage.service.ArbitrageExecutorService;
import arbitrage.service.ArbitragePropertiesService;
import arbitrage.service.BalanceService;
import arbitrage.vo.ArbitrageTargetVo;

public class ArbitrageExecutor {

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
						ArbitrageExecutorService arbitrageService = new ArbitrageExecutorService();
						arbitrageService.execute(arbitrageTargetDto,a.canExecute());
						BalanceService bs = new BalanceService();
						bs.saveBalanceByHours(arbitrageTargetDto.getOrderBookList());
	
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

package arbitrage.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import arbitrage.dao.BalanceDao;
import arbitrage.dto.BalanceDto;
import arbitrage.vo.OrderBookByServiceVo;

public class BalanceService {

	public void saveBalanceByHours(List<OrderBookByServiceVo> orderBookList) {

		BalanceDao dao = new BalanceDao();
		List<BalanceDto> list = dao.get(Date.valueOf(LocalDate.now()), LocalDateTime.now().getHour());

		if (list.isEmpty()) {

			for (OrderBookByServiceVo orderBook : orderBookList) {

				double jpy = orderBook.getBalance().getBalanceJpy();
				double btc = orderBook.getBalance().getBalanceBtc();
				double reserveJpy = orderBook.getBalance().getReserveJpy();
				double reserveBtc = orderBook.getBalance().getReserveBtc();
				double rate = orderBook.getSellPrice();
				double total = jpy + btc * rate + reserveJpy + reserveBtc * rate;
				dao.insert(BalanceDto.builder().serviceId(orderBook.getServiceId()).date(Date.valueOf(LocalDate.now()))
						.hour(LocalDateTime.now().getHour()).balanceJPY(jpy).balanceBTC(btc)
						.reserveBalanceJPY(reserveJpy).reserveBalanceBTC(reserveBtc).rate(rate).total(total).build());

			}

		}

	}

}

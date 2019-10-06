package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import arbitrage.vo.OrderBookByServiceVo;

public class PriceHistoryDao extends AbstractDao{

	public void insert(long transactionId,OrderBookByServiceVo priceDto) {

		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(sqLitePath);
			String sql = "INSERT INTO price_histories (transaction_id,service_id,buy_price,sell_price,balance_jpy,balance_btc,reserve_balance_jpy,reserve_balance_btc,created_at,updated_at) values (?,?,?,?,?,?,?,?,datetime(CURRENT_TIMESTAMP,'localtime'),datetime(CURRENT_TIMESTAMP,'localtime'))";
			ps = con.prepareStatement(sql);
			ps.setLong(1, transactionId);
			ps.setInt(2, priceDto.getServiceId());
			ps.setDouble(3, priceDto.getBuyPrice());
			ps.setDouble(4, priceDto.getSellPrice());
			ps.setDouble(5, priceDto.getBalance().getBalanceJpy());
			ps.setDouble(6, priceDto.getBalance().getBalanceBtc());
			ps.setDouble(7, priceDto.getBalance().getReserveJpy());
			ps.setDouble(8, priceDto.getBalance().getReserveBtc());

			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}

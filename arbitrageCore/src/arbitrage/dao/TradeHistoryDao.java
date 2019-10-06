package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import arbitrage.dto.TradeHistoryDto;

public class TradeHistoryDao extends AbstractDao {

	public void insert(TradeHistoryDto tradeHistoryDto) {

		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(sqLitePath);
			String sql = "INSERT INTO trade_histories (transaction_id,order_id,is_success,error_content,created_at,updated_at) values (?,?,?,?,datetime(CURRENT_TIMESTAMP,'localtime'),datetime(CURRENT_TIMESTAMP,'localtime'))";
			ps = con.prepareStatement(sql);
			ps.setLong(1, tradeHistoryDto.getTransactionId());
			ps.setString(2, tradeHistoryDto.getOrderId());
			ps.setString(3, String.valueOf(tradeHistoryDto.isSuccess()));
			ps.setString(4, tradeHistoryDto.getErrorContent());
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

package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import arbitrage.dto.TradeHistoryDto;

public class TradeHistoryDao4MySql extends AbstractDao {

	public void insert(TradeHistoryDto tradeHistoryDto) {

		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "INSERT INTO tradeHistory (transactionId,orderId,isSuccess,errorContent,prcDate) values (?,?,?,?,CURRENT_TIMESTAMP)";
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

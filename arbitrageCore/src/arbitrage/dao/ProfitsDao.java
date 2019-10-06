package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import arbitrage.dto.BalanceDto;
import arbitrage.vo.ProfitVo;

public class ProfitsDao extends AbstractDao {

	public void insert(ProfitVo profits, long transactionId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(sqLitePath);
			String sql = "INSERT INTO profits (transaction_id,ask_service_id,bid_service_id,profit,created_at,updated_at) values (?,?,?,?,datetime(CURRENT_TIMESTAMP,'localtime'),datetime(CURRENT_TIMESTAMP,'localtime'))";
			ps = con.prepareStatement(sql);
			ps.setLong(1, transactionId);
			ps.setInt(2, profits.getAskOrderBook().getServiceId());
			ps.setInt(3, profits.getBidOrderBook().getServiceId());
			ps.setDouble(4, profits.getProfit());
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

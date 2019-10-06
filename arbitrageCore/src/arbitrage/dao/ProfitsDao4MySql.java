package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import arbitrage.dto.BalanceDto;
import arbitrage.vo.ProfitVo;

public class ProfitsDao4MySql extends AbstractDao {

	public void insert(ProfitVo profits, long transactionId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "INSERT INTO profit (id,askServiceId,bidServiceId,profit,prcDate) values (?,?,?,?,CURRENT_TIMESTAMP)";
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

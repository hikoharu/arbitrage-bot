package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import arbitrage.vo.OrderBookByServiceVo;

public class PriceHistoryDao4MySql extends AbstractDao{

	public void insert(long transactionId,OrderBookByServiceVo priceDto) {

		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "INSERT INTO priceHistory (transactionId,serviceId,buyPrice,sellPrice,balanceJpy,balanceBtc,reserveBalanceJpy,reserveBalanceBtc,prcDate) values (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
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

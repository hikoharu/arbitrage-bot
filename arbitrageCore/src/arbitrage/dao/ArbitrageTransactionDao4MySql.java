package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import arbitrage.dto.ArbitrageTransactionDto;
import arbitrage.util.StatusEnum;

public class ArbitrageTransactionDao4MySql extends AbstractDao {

	public void update(long transactionId, int status) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			String updateColumn = "";

			if (status == StatusEnum.ASKED.getStatus()) {
				updateColumn = "buyDate";
			} else {
				updateColumn = "sellDate";
			}

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "UPDATE arbitrageTransaction SET " + updateColumn
					+ "=CURRENT_TIMESTAMP,prcDate=CURRENT_TIMESTAMP WHERE id=?";
			ps = con.prepareStatement(sql);
			ps.setLong(1, transactionId);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	public long insert(ArbitrageTransactionDto dto) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "insert into arbitrageTransaction (askServiceId,bidServiceId,currency,amount,askAmount,bidAmount,difference,createdDate,prcDate) values (?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getAskServiceId());
			ps.setInt(2, dto.getBidServiceId());
			ps.setString(3, dto.getCurrency());
			ps.setDouble(4, dto.getAmount());
			ps.setDouble(5, dto.getAskAmount());
			ps.setDouble(6, dto.getBidAmount());
			ps.setDouble(7, dto.getDifference());

			ps.executeUpdate();
			rs = ps.executeQuery("select LAST_INSERT_ID() AS LAST");
			long newTransactionId = 0;
			if (rs != null && rs.next()) {
				newTransactionId = rs.getLong("LAST");
			}
			return newTransactionId;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 0;
	}


	public List<ArbitrageTransactionDto> getFailedOrder() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ArbitrageTransactionDto> list = new ArrayList<ArbitrageTransactionDto>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "SELECT * from arbitrageTransaction where not ((buyDate is null and sellDate is null) or (buyDate is not null and sellDate is not null))";
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery(sql);
			while (rs != null && rs.next()) {

				list.add(ArbitrageTransactionDto.builder().id(rs.getInt("id")).askServiceId(rs.getInt("askServiceId"))
						.bidServiceId(rs.getInt("bidServiceId")).currency(rs.getString("currency"))
						.amount(rs.getDouble("amount")).askAmount(rs.getDouble("askAmount"))
						.bidAmount(rs.getDouble("bidAmount")).difference(rs.getDouble("difference")).build());

			}
			return list;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;

	}

}

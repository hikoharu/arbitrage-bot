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

public class ArbitrageTransactionDao extends AbstractDao {

	public void update(long transactionId, int status) {		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			String updateColumn = "";

			if (status == StatusEnum.ASKED.getStatus()) {
				updateColumn = "buy_date";
			} else {
				updateColumn = "sell_date";
			}

			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(sqLitePath);
			String sql = "UPDATE arbitrage_transactions SET " + updateColumn
					+ "=datetime(CURRENT_TIMESTAMP,'localtime'),updated_at=datetime(CURRENT_TIMESTAMP,'localtime') WHERE id=?";
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
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(sqLitePath);
			String sql = "insert into arbitrage_transactions (ask_service_id,bid_service_id,currency,amount,ask_amount,bid_amount,difference,created_at,updated_at) values (?,?,?,?,?,?,?,datetime(CURRENT_TIMESTAMP,'localtime'),datetime(CURRENT_TIMESTAMP,'localtime'))";
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getAskServiceId());
			ps.setInt(2, dto.getBidServiceId());
			ps.setString(3, dto.getCurrency());
			ps.setDouble(4, dto.getAmount());
			ps.setDouble(5, dto.getAskAmount());
			ps.setDouble(6, dto.getBidAmount());
			ps.setDouble(7, dto.getDifference());

			ps.executeUpdate();
			ps.close();
			ps = con.prepareStatement("select LAST_INSERT_ROWID()");
			rs = ps.executeQuery();
			long newTransactionId = 0;
			if (rs != null && rs.next()) {
				newTransactionId = rs.getLong("LAST_INSERT_ROWID()");
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
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(sqLitePath);
			String sql = "SELECT * from arbitrage_transactions where updated_at> datetime('now','localtime','-6 hours') and not ((buy_date is null and sell_date is null) or (buy_date is not null and sell_date is not null))";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {

				list.add(ArbitrageTransactionDto.builder().id(rs.getInt("id")).askServiceId(rs.getInt("ask_service_id"))
						.bidServiceId(rs.getInt("bid_service_id")).currency(rs.getString("currency"))
						.amount(rs.getDouble("amount")).askAmount(rs.getDouble("ask_amount"))
						.bidAmount(rs.getDouble("bid_amount")).difference(rs.getDouble("difference")).build());

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

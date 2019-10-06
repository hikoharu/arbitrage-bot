package arbitrage.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import arbitrage.dto.BalanceDto;

public class BalanceDao4MySql extends AbstractDao {

	public List<BalanceDto> get(Date date,int hour) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<BalanceDto> list = new ArrayList<BalanceDto>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "SELECT * FROM balance WHERE date=? and hour=?";
			ps = con.prepareStatement(sql);
			ps.setDate(1, date);
			ps.setInt(2, hour);
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {

				list.add(BalanceDto.builder().id(rs.getLong("id")).serviceId(rs.getInt("serviceId"))
						.date(rs.getDate("date")).balanceJPY(rs.getDouble("balanceJPY"))
						.balanceBTC(rs.getDouble("balanceBTC")).rate(rs.getDouble("rate")).total(rs.getDouble("total"))
						.build());

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

	public void insert(BalanceDto balance) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "INSERT INTO balance (serviceId,date,hour,balanceJPY,balanceBTC,reserveBalanceJpy,reserveBalanceBtc,rate,total,prcDate) values (?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, balance.getServiceId());
			ps.setDate(2, balance.getDate());
			ps.setInt(3, balance.getHour());
			ps.setDouble(4, balance.getBalanceJPY());
			ps.setDouble(5, balance.getBalanceBTC());
			ps.setDouble(6, balance.getReserveBalanceJPY());
			ps.setDouble(7, balance.getReserveBalanceBTC());
			ps.setDouble(8, balance.getRate());
			ps.setDouble(9, balance.getTotal());
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

package arbitrage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceDao4MySql extends AbstractDao {

	public long get() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, user, pass);
			String sql = "update sequence set id = LAST_INSERT_ID(id + 1)";
			ps = con.prepareStatement(sql);
			ps.executeUpdate(sql);
			ps.close();
			String getSql = "select LAST_INSERT_ID()";
			ps = con.prepareStatement(getSql);
			rs = ps.executeQuery(getSql);
			while (rs != null && rs.next()) {
				return rs.getLong(1);
			}
			throw new Exception();
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
		throw new Exception();
	}

}

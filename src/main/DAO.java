package main;

import java.sql.*;

public class DAO {
	final private static String driverName = "net.ucanaccess.jdbc.UcanaccessDriver";
	final private static String pathDB = "//d:/Delo/Eclipse/Project/Data.mdb";
	final private static String url = "jdbc:ucanaccess:" + pathDB;
	
	private Connection conn;
	
	public DAO() {
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet exec(String sql, String execType) {
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			
			switch (execType) {
			case "SELECT":
				rs = st.executeQuery(sql);
				break;
			case "INSERT":
				st.execute(sql);
				break;
			case "UPDATE":
				st.executeUpdate(sql);
				break;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return rs;
	}
}
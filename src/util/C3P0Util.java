package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Util {
	// 使用xml配置文件
	private static DataSource cpds = new ComboPooledDataSource();
	
	public static DataSource getCpds() {
		return cpds;
	}
	
	public static Connection getConnection(){
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("服务器忙...");
		}
	}
	public static void release(Connection conn, Statement stmt, ResultSet set){
		if(set != null)
			try {
				set.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException();
			}
		
		if(stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
	}
	
}

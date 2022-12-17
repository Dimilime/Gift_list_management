package be.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;

public class DatabaseConnection {
	public Connection connection;

	public DatabaseConnection() {
		
	}

	public static Connection getConnection(){
		Connection conn=null;
		try{ 
			Context ctx = new InitialContext();
		    Context env = (Context) ctx.lookup("java:comp/env");
		    final String connectionString = (String) env.lookup("connectionString");
		    final String username = (String) env.lookup("dbUser");
		    final String password = (String) env.lookup("dbUserPassword");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(
					connectionString,
					username,
					password);
		}
		catch (Exception ex) {
			
		}
		return conn;
	}

}

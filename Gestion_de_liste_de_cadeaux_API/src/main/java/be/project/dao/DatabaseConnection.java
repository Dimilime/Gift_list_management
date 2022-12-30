package be.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DatabaseConnection {
	private static Connection instance = null;

	private DatabaseConnection() {
		Context ctx = null;
		Context env = null;
		String connectionString = null;
		String username = null;
		String password = null;
		try {
			ctx = new InitialContext();
			env = (Context) ctx.lookup("java:comp/env");
			connectionString = (String) env.lookup("connectionString");
			username = (String) env.lookup("dbUser");
			password = (String) env.lookup("dbUserPassword");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			instance=DriverManager.getConnection(connectionString,username,password);
			if(instance == null || instance.isClosed())
				System.out.println("La base de donnée est inaccessible");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	    catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getInstance(){
		try {
			if(instance == null || instance.isClosed())
				new DatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return instance;
	}

}

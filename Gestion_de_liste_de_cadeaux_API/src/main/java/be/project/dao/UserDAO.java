package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

import be.project.models.User;
import oracle.jdbc.OracleTypes;

public class UserDAO implements DAO<User>{

	@Override
	public int insert(User obj) {
		Connection conn=DatabaseConnection.getConnection();
		int id=0;
		try {
			CallableStatement sql = conn.prepareCall("{call insert_user(?,?,?,?,?)}");
			sql.setString(1, obj.getEmail());
			sql.setString(2, obj.getFirstname());
			sql.setString(3, obj.getLastname());
			sql.setString(4, obj.getPassword());
			sql.registerOutParameter(5, java.sql.Types.INTEGER);
			sql.executeUpdate();
			id = sql.getInt(5) ;
			sql.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return id;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(User obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User find(String email) {
		Connection conn=DatabaseConnection.getConnection();
		CallableStatement callableStatement = null;
		Struct struc=null;
		User user=null;
		try {
			String sql="{call getUserByEmail(?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setString(1, email);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "USER_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
			Object[] objects = struc.getAttributes();
			if(objects != null) {
				int userId= Integer.valueOf(objects[0].toString());
				String firstname=(String)objects[1];
				String lastname=(String)objects[2];
				user = new User(userId, firstname, lastname, email, null);
			}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return user;
	}

	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean login(String email, String pwd) {
		Connection conn=DatabaseConnection.getConnection();
		String hash_password_from_db = null; 
		String hash_password="";
		try {
		      CallableStatement callStmt = conn.prepareCall("{call select_user_password(?,?)}");
		      callStmt.setString(1, email);
		      callStmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		      callStmt.execute();
		      hash_password_from_db = callStmt.getString(2);
		      if(hash_password_from_db != null) {
		    	  CallableStatement sql = conn.prepareCall("{call hash_password(?,?)}");
		    	  sql.setString(1, pwd);
		    	  sql.registerOutParameter(2, java.sql.Types.VARCHAR);
		    	  sql.execute();
		    	  hash_password = sql.getString(2);
		    	  sql.close();
		      }
			if(hash_password.equals(hash_password_from_db)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Erreur dans UserDAO de l'api -> Login " + e.getMessage());
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {
				System.out.println("Erreur dans UserDAO de l'api -> Login " + e.getMessage());
			}
		}
		return false;
	}

}

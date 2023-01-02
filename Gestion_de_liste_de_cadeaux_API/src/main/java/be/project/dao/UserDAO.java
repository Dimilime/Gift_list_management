package be.project.dao;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

import be.project.models.Gift;
import be.project.models.GiftList;
import be.project.models.User;
import oracle.jdbc.OracleTypes;

public class UserDAO extends DAO<User>{
	
	public UserDAO(Connection conn) {
		super(conn);
	}
	@Override
	public int insert(User obj) {
		
		int id=0;
		String sql="{call insert_user(?,?,?,?,?)}";
		try(CallableStatement callableStatement = conn.prepareCall(sql)) {
			
			callableStatement.setString(1, obj.getEmail());
			callableStatement.setString(2, obj.getFirstname());
			callableStatement.setString(3, obj.getLastname());
			callableStatement.setString(4, obj.getPassword());
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			id = callableStatement.getInt(5);
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return id;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public int update(User obj) {
		return 0;
	}
	
	@Override
	public User find(int id) {
		Struct struc=null;
		User user=null;
		String sql="{call getUser(?,?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "USER_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
			Object[] objects = struc.getAttributes();
			if(objects != null) {
				int userId= Integer.valueOf(objects[0].toString());
				String firstname=(String)objects[1];
				String lastname=(String)objects[2];
				String email=(String)objects[3];
				user = new User(userId, firstname, lastname, email, null);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	public User findByEmail(String email) {
		Struct struc=null;
		User user=null;
		GiftListDAO giftListDAO = new GiftListDAO(conn);
		String sql="{call getUserByEmail(?,?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.setString(1, email);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "USER_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
			Object[] objects = struc.getAttributes();
			if(objects != null) {
				int userId= Integer.valueOf(objects[0].toString());
				String firstname=(String)objects[1];
				String lastname=(String)objects[2];
				Array array = (Array) objects[5];
				Object[] invitations = (Object[]) array.getArray();
				ArrayList<GiftList> userInvitations = new ArrayList<>();
				if(invitations != null) {
					for (int i = 0; i < invitations.length; i++) {
						String  invitationId = invitations[i].toString();
						GiftList giftList = giftListDAO.find(Integer.valueOf(invitationId));
						userInvitations.add(giftList);
					}
				}
				user = new User(userId, firstname, lastname, email, null);
				user.setInvitations(userInvitations);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public ArrayList<User> findAll() {
		Array array=null;
		ArrayList<User> users= new ArrayList<>();
		String sql="{call getAllUsers(?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.registerOutParameter(1, OracleTypes.ARRAY, "TABLE_USERS");
			callableStatement.execute();
			
			array = callableStatement.getArray(1);
			Object [] objects = (Object[]) array.getArray();
			if(objects != null) {
				for (int i = 0; i < objects.length; i++) {
					Object [] os = ((Struct)objects[i]).getAttributes();
					if(os != null) {
						
						int userId= Integer.valueOf(os[0].toString());
						String firstname=(String)os[1];
						String lastname=(String)os[2];
						String email=(String)os[3];
						User user = new User(userId, firstname, lastname, email, null);
						users.add(user);		
					}
					
				}
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	
	public boolean login(String email, String pwd) {
		String hash_password_from_db = null; 
		String hash_password="";
		try(CallableStatement callStmt = conn.prepareCall("{call select_user_password(?,?)}")) {
		      
		      callStmt.setString(1, email);
		      callStmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		      callStmt.execute();
		      hash_password_from_db = callStmt.getString(2);
		      if(hash_password_from_db != null) {
		    	  try(CallableStatement sql = conn.prepareCall("{call hash_password(?,?)}")){
						sql.setString(1, pwd);
						sql.registerOutParameter(2, java.sql.Types.VARCHAR);
						sql.execute();
						hash_password = sql.getString(2);
		    	  }
		    	  
		      }
			if(hash_password.equals(hash_password_from_db)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Erreur dans UserDAO de l'api -> Login " + e.getMessage());
		}
		return false;
	}
	

}

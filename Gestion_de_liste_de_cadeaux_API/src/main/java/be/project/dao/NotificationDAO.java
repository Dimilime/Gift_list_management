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
import be.project.models.Notification;
import be.project.models.User;
import oracle.jdbc.OracleTypes;

public class NotificationDAO extends DAO<Notification> {

	public NotificationDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Notification obj) {
		int id=0;
		String sql="{call INSERT_NOTIFICATION(?,?,?)}";
		try(CallableStatement callableStatement = conn.prepareCall(sql)) {
			callableStatement.setString(1, obj.getTitle());
			callableStatement.setString(2, obj.getMessage());
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			id = callableStatement.getInt(3);
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
			
		return id;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public int update(Notification obj) {
		return 0;
	}

	@Override
	public Notification find(int id) {
		Struct struc=null;
		Notification notification=null;
		String sql="{call GETNOTIFICATION(?,?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "NOTIFICATION_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
			Object[] objects = struc.getAttributes();
			if(objects != null) {
				int notificationId= Integer.valueOf(objects[0].toString());
				String title=(String)objects[1];
				String message=(String)objects[2];
				notification = new Notification(notificationId, title, message);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notification;
	}

	@Override
	public ArrayList<Notification> findAll() {
		UserDAO userDAO = new UserDAO(conn);
		Array array=null;
		ArrayList<Notification> notifications= new ArrayList<>();
		String sql="{call getAllNotification(?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.registerOutParameter(1, OracleTypes.ARRAY, "TABLE_NOTIFICATION");
			callableStatement.execute();
			
			array = callableStatement.getArray(1);
			Object [] objects = (Object[]) array.getArray();
			if(objects != null) {
				for (int i = 0; i < objects.length; i++) {
					Object [] os = ((Struct)objects[i]).getAttributes();
					if(os != null) {
						
						int notificationId= Integer.valueOf(os[0].toString());
						String title=os[1].toString();
						String message= os[2].toString();
						Array arrayUser = (Array) os[3];
						Object[] usersObject = (Object[]) arrayUser.getArray();
						ArrayList<User> users = new ArrayList<>();
						if(usersObject != null) {
							for (int j = 0; j < usersObject.length; j++) {
								String  userId = usersObject[j].toString();
								User user = userDAO.find(Integer.valueOf(userId));
								users.add(user);
							}
						}
						Notification notification = new Notification(notificationId, title, message);
						notification.setUsers(users);
						notifications.add(notification);		
					}	
				}
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notifications;
	}
	
	public int insertUserNotification(int notifId, int userId) {
		int idU=0;
		String sql2="{call INSERT_USER_NOTIFICATION(?,?,?)}";
		try(CallableStatement callstmt = conn.prepareCall(sql2)){
				callstmt.setInt(1, userId);
				callstmt.setInt(2, notifId);
				callstmt.registerOutParameter(3, java.sql.Types.INTEGER);
				callstmt.executeUpdate();
				idU = callstmt.getInt(3);
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idU;
	}
	
	public boolean deleteUserNotification(Notification obj, int userId) {
		int id=0;
		String sql ="{call DELETE_USER_Notification(?,?,?)}";
		try (CallableStatement callstmt =conn.prepareCall(sql);)
		{
			callstmt.setInt(1, userId);
			callstmt.setInt(2, obj.getNotificationId());
			callstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			callstmt.executeUpdate();
			
			id =callstmt.getInt(3);

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id != 0 ;

	}

}

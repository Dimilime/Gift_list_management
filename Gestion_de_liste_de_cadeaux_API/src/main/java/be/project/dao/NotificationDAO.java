package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

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
		return null;
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

}

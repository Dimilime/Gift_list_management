package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;
import be.project.dao.NotificationDAO;
public class Notification implements Serializable{

	private static final long serialVersionUID = -6017335916474263287L;
	
	private static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static DAO<Notification> notificationDAO = adf.getNotificationDAO();
	
	private int notificationId;
	private String title;
	private String message;
	private ArrayList<User> users;
	
	public Notification() {
		
	}

	public Notification(int notificationId, String title, String message) {
		this.notificationId = notificationId;
		this.title = title;
		this.message = message;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public ArrayList<User> getUsers() {
		return users;
	}


	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	public int create() {
		
		int idCreated=notificationDAO.insert(this);
		
		if(idCreated != 0) {
			for (User user : users) {
				int idU=((NotificationDAO)notificationDAO).insertUserNotification(idCreated, user.getUserId());
				
				if(idU == 0)
					return idU;
			}
		}
		return idCreated;
	}
	
	public static Notification get(int id) {
		return notificationDAO.find(id);
	}
	
	public static ArrayList<Notification> getAll(){
		return notificationDAO.findAll();
	}
	
	public boolean delete(int userId) {
		return ((NotificationDAO)notificationDAO).deleteUserNotification(this, userId);
	}

	
	
	

}

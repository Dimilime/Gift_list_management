package be.project.javabeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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

	public Notification(int notificationId, String title, String message, ArrayList<User> users) {
		this.notificationId = notificationId;
		this.title = title;
		this.message = message;
		this.users = users;
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
	
	public boolean create() {
		return notificationDAO.insert(this) != 0 ? true : false;
	}
	
	public boolean delete(User u) {
		return ((NotificationDAO)notificationDAO).deleteUserNotification(u, this);
	}
	
	public static Notification mapNotificationFromJson(JSONObject jsonObject) throws JsonParseException, JsonMappingException, JSONException, IOException, ExpirationDateException
	{
		Notification notification = new Notification();
		notification.setNotificationId(jsonObject.getInt("notificationId"));
		notification.setTitle(jsonObject.getString("title"));
		notification.setMessage(jsonObject.getString("message"));
		
		JSONArray usersObject = jsonObject.isNull("users") ? null :jsonObject.getJSONArray("users");
		ArrayList<User> users = new ArrayList<>();
		if(usersObject != null)
			for (int i = 0; i < usersObject.length(); i++) {
				JSONObject userObject = usersObject.getJSONObject(i);
				User user = User.getUserByJSONObject(userObject);
				users.add(user);
			}
		notification.setUsers(users);
		return notification;
	}
	
	public static ArrayList<Notification> getAll(){
		return notificationDAO.findAll();
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", title=" + title + ", message=" + message
				+ ", users=" + users + "]";
	}
	
	
	
	
	

}

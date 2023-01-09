package be.project.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

import be.project.javabeans.Notification;
import be.project.javabeans.User;

public class NotificationDAO extends DAO<Notification>{
	
	public NotificationDAO() {
		
	}

	@Override
	public int insert(Notification obj) {
		parameters.clear();
		parameters.add("title", obj.getTitle());
		parameters.add("message", obj.getMessage());
		StringBuilder users = new StringBuilder();
		for (User u : obj.getUsers()) {
			users.append(u.getEmail()+",");
		}
		parameters.add("users", users.toString());
		
		clientResponse=resource
				.path("notification")
				.header("key",apiKey)
				.post(ClientResponse.class,parameters);
		
		return clientResponse.getStatus() == 201 ? //if client response equals 201 return the id created or 0
				Integer.valueOf(clientResponse.getHeaders().getFirst("idCreated")) : 0;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public boolean update(Notification obj) {
		return false;
	}

	@Override
	public Notification find(int id) {
		return null;
	}

	@Override
	public ArrayList<Notification> findAll() {
		clientResponse= resource.path("notification")
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		
		String responseJSON=clientResponse.getEntity(String.class);
		int status=clientResponse.getStatus();

		if(status == 404) 
			return null;
		ArrayList<Notification> notifications = new ArrayList<>();
		try {
			JSONArray arrayResponseJSON = new JSONArray(responseJSON);
			for(int i = 0; i < arrayResponseJSON.length(); i++) {
				Notification notification = Notification.mapNotificationFromJson((JSONObject) arrayResponseJSON.get(i));
				notifications.add(notification);
			}
			return notifications;
		} catch (Exception e) {
			System.out.println("error findAll de NotificationDAO client = "+e.getMessage());
			return null;
		}
	}
	
	public boolean deleteUserNotification(User user, Notification obj) {
		
		parameters.clear();
		parameters.add("userId", String.valueOf(user.getUserId()));
		clientResponse = resource.path("notification").path(String.valueOf(obj.getNotificationId()))
				.header("key",apiKey)
				.delete(ClientResponse.class, parameters);
		return clientResponse.getStatus() == 204;
	}
	

}

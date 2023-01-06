package be.project.dao;

import java.util.ArrayList;
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
		return null;
	}
	

}

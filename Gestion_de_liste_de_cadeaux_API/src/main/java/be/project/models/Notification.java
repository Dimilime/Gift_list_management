package be.project.models;

import java.io.Serializable;

public class Notification implements Serializable{

	private static final long serialVersionUID = -6017335916474263287L;
	
	private int notificationId;
	private String title;
	private String message;
	private User user;
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	

}

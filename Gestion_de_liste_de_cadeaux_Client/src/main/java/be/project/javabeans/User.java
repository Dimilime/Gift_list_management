package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

	private static final long serialVersionUID = -5899409470895546883L;
	
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<GiftList> giftList;
	private ArrayList<Notification> notifications;
	
	public User() {
		
	}

	public User(int userId, String firstName, String lastName, String email, String password) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<GiftList> getGiftList() {
		return giftList;
	}

	public void setGiftList(ArrayList<GiftList> giftList) {
		this.giftList = giftList;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}
	
	
	
	
	

}

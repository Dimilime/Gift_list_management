package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.UserDAO;

public class User implements Serializable{
	private static final long serialVersionUID = -5899409470895546883L;
	
	private int userId;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private ArrayList<GiftList> giftList;
	private ArrayList<Notification> notifications;
	private ArrayList<Participation> participations;
	
	public User() {
		
	}
	public User(String firstname, String lastname, String email, String password) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.email= email;
		this.password=password;
	}

	public User(int userId, String firstname, String lastname, String email, String password) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}
	
	public User(int userId, String firstname, String lastname, String email, String password,ArrayList<GiftList> giftList ,ArrayList<Notification> notifications, ArrayList<Participation> participations ) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.giftList= giftList;
		this.notifications=notifications;
		this.setParticipations(participations);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	
	public ArrayList<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(ArrayList<Participation> participations) {
		this.participations = participations;
	}
	
	public static boolean login(String email, String password) {
		UserDAO userDAO = new UserDAO();
		return userDAO.login(email, password);
	}

	public static User getUser(String email) {
		UserDAO userDAO = new UserDAO();
		return userDAO.find(email);
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", giftList=" + giftList + ", notifications=" + notifications
				+ ", participations=" + participations + "]";
	}
	public int insertUser() {
		UserDAO userDAO = new UserDAO();
		return userDAO.insert(this);
	}
	
	

}

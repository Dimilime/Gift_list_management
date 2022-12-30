package be.project.javabeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;
import be.project.dao.UserDAO;

public class User implements Serializable{

	private static final long serialVersionUID = -5899409470895546883L;
	
	private int userId;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private ArrayList<GiftList> giftLists;
	private ArrayList<GiftList> invitations;
	private ArrayList<Notification> notifications;
	private ArrayList<Participation> participations;
	
	private static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static DAO<User> userDAO = adf.getUserDAO();
	
	public User() {
		
	}
	public User(String firstname, String lastname, String email, String password) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.email= email;
		this.password=password;
		this.giftLists = new ArrayList<>();
		this.invitations = new ArrayList<>();
		this.notifications = new ArrayList<>();
		this.participations = new ArrayList<>();
	}
	public User(int userId, String firstname, String lastname, String email, String password,ArrayList<GiftList> giftLists ,ArrayList<Notification> notifications, ArrayList<Participation> participations ) {
		this(firstname,lastname,email,password);
		this.userId = userId;
		this.giftLists= giftLists;
		this.notifications=notifications;
		this.participations= participations;
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

	public void setlastname(String lastname) {
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
		return giftLists = GiftList.getAll().stream()
				.filter( list -> list.getGiftListUser().getUserId() == this.userId)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public void setGiftList(ArrayList<GiftList> giftLists) {
		this.giftLists = giftLists;
	}

	public ArrayList<GiftList> getInvitations() {
		return invitations;
	}

	public void setInvitations(ArrayList<GiftList> invitations) {
		this.invitations = invitations;
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
		return ((UserDAO)userDAO).login(email, password);
	}

	public static User getUserByEmail(String email) {
		return ((UserDAO)userDAO).findByEmail(email);
	}
	public static User getUser(int id) {
		return userDAO.find(id);
	}

	public static User getUserByJSONObject(JSONObject json)throws JsonParseException, JsonMappingException, JSONException, IOException {
		User user=new User();
		user.setUserId(json.getInt("userId"));
		user.setFirstname(json.getString("firstname"));
		user.setlastname(json.getString("lastname"));
		user.setEmail(json.getString("email"));
		return user;
	}
	
	public static ArrayList<User> getAll(){
		return userDAO.findAll();
	}
	public boolean createUser() {
		return userDAO.insert(this) == 1;
	}
	public boolean createUser(boolean withSharedList) {
		return false;
	}
	
	public int addGiftList(GiftList gl) {
		giftLists.add(gl);
		return gl.create();
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", giftLists=" + giftLists + ", invitations=" + invitations
				+ ", notifications=" + notifications + ", participations=" + participations + "]";
	}
	
	
	
	
	
	

}

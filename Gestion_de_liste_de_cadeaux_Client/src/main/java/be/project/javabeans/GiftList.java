package be.project.javabeans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;
import be.project.dao.GiftListDAO;

public class GiftList implements Serializable{

	private static final long serialVersionUID = 2490091305208028913L;
	
	private static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static DAO<GiftList> giftListDAO = adf.getGiftListDAO();
	
	private int listId;
	private String occasion;
	private LocalDate expirationDate;
	private boolean enabled;
	private ArrayList<Gift> gifts;
	private ArrayList<User> sharedUsers;
	private User giftListUser;
	private String key;
	
	public GiftList() {
	}

	public GiftList(int listId, String occasion , User giftListUser,  String expirationDate, String key, String enabled) {
		this.setListId(listId);
		this.occasion = occasion;
		this.setEnabled(enabled);
		this.setKey(key);
		this.setExpirationDate(expirationDate);
		this.giftListUser = giftListUser;
		this.gifts = new ArrayList<>();
		this.sharedUsers = new ArrayList<>();
	}
	
	
	public GiftList(int listId, String occasion, String expirationDate, ArrayList<Gift> gifts, ArrayList<User> sharedUsers,
			User giftListUser, String key, String enabled) {
		this(listId,occasion,giftListUser, expirationDate, key,enabled);
		this.gifts = gifts;
		this.sharedUsers = sharedUsers;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}


	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate parsedExpirationDate = null;		
		
		try 
		{
			if(expirationDate != null) {
				expirationDate = expirationDate.contains("-")? expirationDate.replace("-", "/"): expirationDate;
				parsedExpirationDate = LocalDate.parse(expirationDate, formatter);
			}
		
		}catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		this.expirationDate = parsedExpirationDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled.equals("Y") ? true : false;
	}

	public User getGiftListUser() {
		return giftListUser;
	}

	public void setGiftListUser(User giftListUser) {
		this.giftListUser = giftListUser;
	}
	
	public ArrayList<Gift> getGifts() {
		return gifts = Gift.getAll().stream()
				.filter( gift -> gift.getGiftList().listId == this.listId)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public void setGifts(ArrayList<Gift> gifts) {
		this.gifts = gifts;
	}

	public ArrayList<User> getSharedUsers() {
		return sharedUsers ;
	}

	public void setSharedUsers(ArrayList<User> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}
	
	public boolean addGift(Gift g) {
		if(g != null) {
			gifts.add(g);
			return g.create() != 0;
		}
		return false;	
	}
	
	public void removeGift(Gift g) {
		gifts.remove(g);
	}
	
	public boolean addUserToSharedList(User u) {
		if(u != null) {
			sharedUsers.add(u);
			return true;
		}
		return false;
	}
	public boolean share() {
		if(sharedUsers != null) {
			for(User sharedUser : sharedUsers) {
				if(!((GiftListDAO)giftListDAO).addUserTosharedList(this, sharedUser)) {//if sharedList was not created return false
					return false;
				}
			}
			ArrayList<User> usersUnotified = sharedUsers.stream().filter( u -> u.getNotifications() != null)
					.collect(Collectors.toCollection(ArrayList::new));
			// si je fais ça, ça va pas trop aller parce qu'il peuvent déjà avoir des notifications si je recupère les users avec leur notifications directement
			// avec le getUserByEmail, je serais obligé de faire une fonction en sgbd quui retourne toutes les notifs de l'user directement 
			// et si je fais ça les users que j'ajoute dans la liste de shredUsers ont déjà une notification, donc ça sera pas vide
			// les user que je recupère avec la liste n'ont d'office pas de notification, donc plutot que de recup ceux qui ont une liste de notif vide
			//ce serait mieux de recup ceux qui n'ont pas de liste de notif vide car ils correspondent à ceux que je viens d'inserer 
			//-- quand je rentre je crée la fonction qui récup toute les notifs de l'user directement => order by id desc l'id est dans l'ordre de reception donc pas besoin de date
			System.out.println("usersUnotified: " + usersUnotified);
			Notification notification = new Notification(0, "Invitation",
					"Tu es invité à participé à la liste de cadeau de "+giftListUser.getLastname()+" "+giftListUser.getFirstname(),usersUnotified);
			return notification.create();
		}
		return false;
	}

	public static GiftList get(int id) {
		return giftListDAO.find(id);
	}
	public int create() {
		return giftListDAO.insert(this);
	}
	
	public static ArrayList<GiftList> getAll(){
		return giftListDAO.findAll();
	}
	
	public static GiftList mapListFromJson(JSONObject jsonObject) throws JsonParseException, JsonMappingException, JSONException, IOException  {
		GiftList giftList = null;
		User user = null;
		String expirationDate = null;
		int listId = jsonObject.getInt("listId");
		String occasion = jsonObject.getString("occasion");
		JSONObject dateObject = jsonObject.isNull("expirationDate") ? null : jsonObject.getJSONObject("expirationDate");
		if(dateObject != null) {
			expirationDate = String.format("%04d/%02d/%02d", 
					dateObject.getInt("year"),dateObject.getInt("monthValue"), dateObject.getInt("dayOfMonth"));
		}
		String enabled = jsonObject.getBoolean("enabled") ? "Y" : "N" ;
		JSONObject userObject = jsonObject.getJSONObject("giftListUser");
		user = User.getUserByJSONObject(userObject);
		JSONArray sharedUsersObject = jsonObject.isNull("sharedUsers") ? null :jsonObject.getJSONArray("sharedUsers");
		ArrayList<User> sharedUsers = new ArrayList<>();
		if(sharedUsersObject != null)
			for (int i = 0; i < sharedUsersObject.length(); i++) {
				JSONObject sharedUserObject = sharedUsersObject.getJSONObject(i);
				User sharedUser = User.getUserByJSONObject(sharedUserObject);
				sharedUsers.add(sharedUser);
			}
		giftList = new GiftList(listId, occasion, user, expirationDate, null, enabled);
		giftList.setSharedUsers(sharedUsers);
		return giftList;
		
	}

	@Override
	public String toString() {
		return "GiftList [listId=" + listId + ", occasion=" + occasion + ", expirationDate=" + expirationDate
				+ ", enabled=" + enabled + ", gifts=" + gifts + ", sharedUsers=" + sharedUsers + ", giftListUser="
				+ giftListUser + ", key=" + key + "]";
	}
	
	
	
	
	
	

}

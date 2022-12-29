package be.project.javabeans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;

public class GiftList implements Serializable{

	private static final long serialVersionUID = 2490091305208028913L;
	
	private static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static DAO<GiftList> giftListDAO = adf.getGiftListDAO();
	
	private int listId;
	private String occasion;
	@JsonProperty("expirationDate")
	private LocalDate expirationDate;
	private boolean enabled;
	private ArrayList<Gift> gifts;
	private ArrayList<User> sharedUsers;
	@JsonProperty("giftListUser")
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
		
		
		try {
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
		return sharedUsers;
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
		giftList = new GiftList(listId, occasion, user, expirationDate, null, enabled);
		return giftList;
		
	}
	
	
	
	

}

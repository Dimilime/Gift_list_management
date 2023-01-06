package be.project.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;
import be.project.dao.GiftListDAO;
import be.project.models.GiftList;

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
	
	public GiftList(int listId, String occasion , User giftListUser,  String expirationDate, String key, String enabled) {
		this.listId = listId;
		this.occasion = occasion;
		this.setEnabled(enabled);
		this.key = key;
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
			expirationDate = expirationDate.contains(" ")? expirationDate.split(" ")[0] : expirationDate;
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

	public ArrayList<Gift> getGifts() {
		return gifts;
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

	public User getGiftListUser() {
		return giftListUser;
	}

	public void setGiftListUser(User giftListUser) {
		this.giftListUser = giftListUser;
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public void addGift(Gift gift) {
		gifts.add(gift);
	}
	
	public int create() {
		return giftListDAO.insert(this);
	}
	
	public boolean addUserToSharedList(User u) {
		return ((GiftListDAO)giftListDAO).addUserTosharedList(this, u);
	}
	public static ArrayList<GiftList> getAll() {
		return giftListDAO.findAll();
	}
	
	public static GiftList getGiftList(int id) {
		return giftListDAO.find(id);
	}
	
	public Date parseExpirationDateToDate() {
		try {
		if(expirationDate != null) 
			return Date.valueOf(expirationDate);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String returnEnabledAsString() {
		return enabled ? "Y" : "N";
	}


	@Override
	public String toString() {
		return "GiftList [listId=" + listId + ", occasion=" + occasion + ", expirationDate=" + expirationDate
				+ ", enabled=" + enabled + ", gifts=" + gifts + ", sharedUsers=" + sharedUsers + ", giftListUser="
				+ giftListUser + ", key=" + key + "]";
	}


	public int update() {
		return giftListDAO.update(this);
	}
	
	
	
	

}

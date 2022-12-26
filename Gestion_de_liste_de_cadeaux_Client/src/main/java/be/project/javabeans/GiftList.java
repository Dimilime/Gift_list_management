package be.project.javabeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;

public class GiftList implements Serializable{

	private static final long serialVersionUID = 2490091305208028913L;
	
	private static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static DAO<GiftList> giftListDAO = adf.getGiftListDAO();
	
	private String occasion;
	private LocalDate expirationDate;
	private boolean enabled;
	private ArrayList<Gift> gifts;
	private ArrayList<User> sharedUsers;
	private User giftListUser;
	
	public GiftList() {
	}
	
	public GiftList(String occasion, User giftListUser) {
		this.occasion = occasion;
		this.enabled = true;
		this.giftListUser = giftListUser;
	}
	
	public GiftList(String occasion, LocalDate expirationDate, boolean enabled, ArrayList<Gift> gifts, ArrayList<User> sharedUsers,User giftListUser) {
		this(occasion,giftListUser);
		this.gifts = gifts;
		this.sharedUsers = sharedUsers;
		this.giftListUser = giftListUser;	
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

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public User getGiftListUser() {
		return giftListUser;
	}

	public void setGiftListUser(User giftListUser) {
		this.giftListUser = giftListUser;
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

	public boolean create() {
		return giftListDAO.insert(this);
	}
	
	
	
	
	

}

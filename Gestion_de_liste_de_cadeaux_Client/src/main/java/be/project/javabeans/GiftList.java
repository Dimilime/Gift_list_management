package be.project.javabeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class GiftList implements Serializable{

	private static final long serialVersionUID = 2490091305208028913L;
	
	private String occasion;
	private LocalDate expirationDate;
	private boolean enabled;
	private ArrayList<Gift> gifts;
	private ArrayList<User> sharedUsers;
	private User userGiftList;

	public GiftList(String occasion, User userGiftList) {
		this.occasion = occasion;
		this.enabled = true;
		this.setUserGiftList(userGiftList);
	}
	
	public GiftList(String occasion, LocalDate expirationDate, boolean enabled, ArrayList<Gift> gifts, ArrayList<User> sharedUsers,User userGiftList) {
		this.occasion = occasion;
		this.expirationDate = expirationDate;
		this.enabled = enabled;
		this.setGifts(gifts);
		this.setSharedUsers(sharedUsers);
		this.setUserGiftList(userGiftList);	
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

	public User getUserGiftList() {
		return userGiftList;
	}

	public void setUserGiftList(User userGiftList) {
		this.userGiftList = userGiftList;
	}

	public void addGift(Gift gift) {
		gifts.add(gift);
	}
	
	
	
	
	
	
	

}

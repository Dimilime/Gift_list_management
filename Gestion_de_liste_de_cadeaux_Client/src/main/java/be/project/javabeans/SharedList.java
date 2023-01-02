package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class SharedList implements Serializable{

	private static final long serialVersionUID = -3737339361511174561L;
	
	private User user;
	private GiftList giftList;
	
	public SharedList() {
	}

	public SharedList(User user, GiftList giftList) {
		this.user = user;
		this.giftList = giftList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GiftList getGiftList() {
		return giftList;
	}

	public void setGiftList(GiftList giftList) {
		this.giftList = giftList;
	}
	
	public static  ArrayList<SharedList>getAll() {
		return null;
	}
	
	public boolean insert() {
		return false;
	}
	
	

}

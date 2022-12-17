package be.project.javabeans;

import java.io.Serializable;
import java.time.LocalDate;

public class GiftList implements Serializable{

	private static final long serialVersionUID = 2490091305208028913L;
	
	private String occasion;
	private LocalDate expirationDate;
	private boolean enabled;
	
	public GiftList() {
	}

	public GiftList(String occasion, LocalDate expirationDate, boolean enabled) {
		this.occasion = occasion;
		this.expirationDate = expirationDate;
		this.enabled = enabled;
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
	
	
	
	
	

}

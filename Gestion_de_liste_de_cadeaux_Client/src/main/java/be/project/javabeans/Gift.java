package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Gift implements Serializable{

	private static final long serialVersionUID = -6171468954549901451L;
	
	private int giftId;
	private int priorityLevel;
	private String name;
	private String description;
	private double averagePrice;
	private boolean reserved;
	private String link;
	private String Image;
	private ArrayList<Participation> participations;
	
	public Gift() {
	}
	
	public Gift(int priorityLevel, String name, String description, double averagePrice, boolean reserved, String link,
			String image) {
		super();
		this.priorityLevel = priorityLevel;
		this.name = name;
		this.description = description;
		this.averagePrice = averagePrice;
		this.reserved = reserved;
		this.link = link;
		Image = image;
		this.setParticipations(new ArrayList<Participation>());
	}
	
	public Gift(int priorityLevel, String name, String description, double averagePrice, boolean reserved, String link,
			String image, ArrayList<Participation> participations) {
		this(priorityLevel,name, description, averagePrice, reserved, link, image);
		this.setParticipations(participations);
	}


	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public ArrayList<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(ArrayList<Participation> participations) {
		this.participations = participations;
	}	

}

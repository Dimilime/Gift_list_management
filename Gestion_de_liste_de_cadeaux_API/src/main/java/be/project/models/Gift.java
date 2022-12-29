package be.project.models;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;

public class Gift implements Serializable{

	private static final long serialVersionUID = -6171468954549901451L;
	
	private int giftId;
	private int priorityLevel;
	private String name;
	private String description;
	private double averagePrice;
	private boolean reserved;
	private String link;
	private Blob image;
	private ArrayList<Participation> participations;
	private GiftList giftList;
	
	private static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static DAO<Gift> giftDAO = adf.getGiftDAO();
	
	public Gift() {
	}
	
	public Gift(int giftId, int priorityLevel, String name, String description, double averagePrice, String reserved,String link,
			Blob image, GiftList giftList) {
		this.giftId =giftId;
		this.priorityLevel = priorityLevel;
		this.name = name;
		this.description = description;
		this.averagePrice = averagePrice;
		this.setReserved(reserved);
		this.link = link;
		this.image = image;
		this.participations= new ArrayList<Participation>();
		this.giftList = giftList;
	}
	
	public Gift(int giftId, int priorityLevel, String name, String description, double averagePrice, String reserved, String link,
			GiftList giftList,Blob image, ArrayList<Participation> participations) {
		this(giftId,priorityLevel,name, description, averagePrice,reserved, link, image,giftList);
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

	public void setReserved(String reserved) {
		this.reserved = reserved == "Y" ? true : false;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public ArrayList<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(ArrayList<Participation> participations) {
		this.participations = participations;
	}

	public GiftList getGiftList() {
		return giftList;
	}

	public void setGiftList(GiftList giftList) {
		this.giftList = giftList;
	}
	
	public int create() {
		return giftDAO.insert(this);
	}
	
	public String getReservedAsString() {
		return reserved ? "Y" : "N";
	}
	
	public static Gift getGift(int id) {
		return giftDAO.find(id);
	}
	
	public static ArrayList<Gift> getAll(){
		return giftDAO.findAll();
	}
	

}

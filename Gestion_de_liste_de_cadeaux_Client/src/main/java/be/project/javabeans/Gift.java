package be.project.javabeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import be.project.dao.AbstractDAOFactory;
import be.project.dao.DAO;
import be.project.dao.GiftDAO;
import be.project.javabeans.Gift;

public class Gift implements Serializable{

	private static final long serialVersionUID = -6171468954549901451L;
	
	private int giftId;
	private int priorityLevel;
	private String name;
	private String description;
	private double averagePrice;
	private boolean reserved;
	private String link;
	private String image;
	private GiftList giftList;
	private ArrayList<Participation> participations;
	
	private static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	private static DAO<Gift> giftDAO = adf.getGiftDAO();
	
	public Gift() {
	}
	
	public Gift(int giftId, int priorityLevel, String name, String description, double averagePrice, String link,
			String image, GiftList giftList) {
		this.giftId =giftId;
		this.priorityLevel = priorityLevel;
		this.name = name;
		this.description = description;
		this.averagePrice = averagePrice;
		this.reserved = false;
		this.link = link;
		this.image = image;
		this.participations= new ArrayList<Participation>();
		this.setGiftList(giftList);
	}
	
	public Gift(int giftId, int priorityLevel, String name, String description, double averagePrice, String link,
			GiftList giftList,String image, ArrayList<Participation> participations) {
		this(giftId,priorityLevel,name, description, averagePrice, link, image,giftList);
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
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public GiftList getGiftList() {
		return giftList;
	}

	public void setGiftList(GiftList giftList) {
		this.giftList = giftList;
	}

	public ArrayList<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(ArrayList<Participation> participations) {
		this.participations = participations;
	}	
	
	public int create() {
		return giftDAO.insert(this);
	}
	
	public boolean update() {
		return giftDAO.update(this);
	}
	
	public static ArrayList<Gift> getAll(){
		return giftDAO.findAll();
	}
	
	public boolean isFullyPaid() {
		if(this.getParticipations() == null) 
			return false;
		double somme =0;
		for(Participation participation : this.getParticipations()) {
			somme+= participation.getParticipationpart();
		}
		return this.averagePrice == somme;
	}

	
	public boolean hasAlreadyOffer(int userId) {
		boolean success = false;
		if(this.getParticipations() == null) 
			return false;
		for(Participation participation : this.getParticipations()) {
			if(userId == participation.getParticipant().getUserId()) {
				success=true;
				break;
			}
		}
		return success;
	}
	
	public double priceRemain() {
		if(this.getParticipations() == null) 
			return this.averagePrice;
		
		double price = averagePrice;
		for(Participation participation : this.getParticipations()) {
			price-= participation.getParticipationpart();
		}
		return price;
	}
	public static Gift mapGiftFromJson(JSONObject jsonObject) throws JsonParseException, JsonMappingException, JSONException, IOException, ExpirationDateException  {

		Gift gift = null;
		GiftList giftList = null;
		ArrayList<Participation> participations=null;
		int giftId = jsonObject.getInt("giftId");
		int priorityLevel = jsonObject.getInt("priorityLevel");
		String name = jsonObject.getString("name");
		String description = jsonObject.isNull("description") ? null : jsonObject.getString("description");
		Double averagePrice = jsonObject.getDouble("averagePrice");
		boolean reserved = jsonObject.getBoolean("reserved");
		String link = jsonObject.isNull("link") ? null : jsonObject.getString("link");
		String image = jsonObject.isNull("image") ? null : jsonObject.getString("image");
		JSONArray participationsArray = jsonObject.getJSONArray("participations");
		if(participationsArray.length()>0)
			participations = new ArrayList<Participation>();
		for(int i=0;i<participationsArray.length();i++) {
			JSONObject obj=(JSONObject)participationsArray.getJSONObject(i);
			double price = obj.getDouble("participationpart");
			JSONObject participantObject= obj.getJSONObject("participant");
			int userId= participantObject.getInt("userId");
			User u = new User();
			u.setUserId(userId);
			Participation participation = new Participation(0,u,price,null);
			participations.add(participation);
		}
		JSONObject listObject = jsonObject.getJSONObject("giftList");
		giftList = GiftList.mapListFromJson(listObject);
		gift = new Gift(giftId, priorityLevel, name, description, averagePrice, link, giftList, image, participations);
		gift.setReserved(reserved);
		return gift;
		
	}

	public static Gift get(int giftId) {
		return giftDAO.find(giftId);
	}

	public boolean offer() {
		 return((GiftDAO)giftDAO).addOffer(this);
	}

	public void addParticipation(Participation participation) {
		if(participations==null) {
			participations = new ArrayList<Participation>();
		}
		participations.add(participation);
		
	}
}

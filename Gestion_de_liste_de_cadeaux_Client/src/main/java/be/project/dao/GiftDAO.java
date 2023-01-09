package be.project.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

import be.project.javabeans.Gift;
import be.project.javabeans.Participation;

public class GiftDAO extends DAO<Gift> {
	
	public GiftDAO() {
		super();
	}

	@Override
	public int insert(Gift obj) {

		parameters.clear();
		parameters.add("giftName", obj.getName());
		parameters.add("description", obj.getDescription());
		parameters.add("averagePrice", String.valueOf(obj.getAveragePrice()));
		parameters.add("priorityLevel", String.valueOf(obj.getPriorityLevel()));
		parameters.add("giftImg", obj.getImage());
		parameters.add("link", String.valueOf(obj.getLink()));
		parameters.add("listId", String.valueOf(obj.getGiftList().getListId()));
		clientResponse=resource
				.path("gift")
				.header("key",apiKey)
				.post(ClientResponse.class,parameters);
		
		return clientResponse.getStatus() == 201 ? 1 : 0;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public boolean update(Gift obj) {
		parameters.clear();
		parameters.add("giftId",String.valueOf(obj.getGiftId()));
		parameters.add("giftName",obj.getName());
		parameters.add("description", obj.getDescription());
		parameters.add("price", String.valueOf(obj.getAveragePrice()));
		parameters.add("priorityLevel",String.valueOf(obj.getPriorityLevel()));
		parameters.add("link",obj.getLink());

		clientResponse=resource
				.path("gift")
				.path(String.valueOf(obj.getGiftId()))
				.header("key",apiKey)
				.put(ClientResponse.class,parameters)
				;

		return clientResponse.getStatus() == 204 ? true : false;
	}

	@Override
	public Gift find(int id) {
		clientResponse=resource
				.path("gift")
				.path(String.valueOf(id))
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		Gift gift=null;
		String responseJSON=clientResponse.getEntity(String.class);
		int status=clientResponse.getStatus();
		//gérer cas aucun gift trouvé
		if(status == 404) 
			return null;

		try {
			JSONObject json = new JSONObject(responseJSON);
			gift=Gift.mapGiftFromJson(json);
			return gift;
		} catch (Exception e) {
			System.out.println("error find de GiftDAO client find = "+e.getMessage());
			return null;
		}
	}
	

	@Override
	public ArrayList<Gift> findAll() {
		clientResponse= resource.path("gift")
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		
		String responseJSON=clientResponse.getEntity(String.class);
		int status=clientResponse.getStatus();
		//gérer les listes de cadeaux vides
		if(status == 404) 
			return null;
		ArrayList<Gift> gifts = new ArrayList<>();
		JSONArray arrayResponseJSON = new JSONArray(responseJSON);
		try {
			for(int i = 0; i < arrayResponseJSON.length(); i++) {
				Gift gift = Gift.mapGiftFromJson((JSONObject) arrayResponseJSON.get(i));
				gifts.add(gift);
			}
			return gifts;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error findAll de GiftDAO client = "+e.getMessage());
			return null;
		}
	}

	public boolean addOffer(Gift gift) {
		ArrayList<Participation> participations = gift.getParticipations();
		parameters.clear();
		parameters.add("userId", String.valueOf(participations.get(0).getParticipant().getUserId()));
		parameters.add("giftId", String.valueOf(gift.getGiftId()));
		parameters.add("price",  String.valueOf(participations.get(0).getParticipationpart()));

		clientResponse=resource
				.path("gift")
				.path(String.valueOf(gift.getGiftId()))
				.path("offer")
				.header("key",apiKey)
				.post(ClientResponse.class,parameters);
		return clientResponse.getStatus() == 201 ? true : false;
	}

}

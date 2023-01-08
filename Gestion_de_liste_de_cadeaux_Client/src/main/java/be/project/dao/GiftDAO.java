package be.project.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import be.project.javabeans.Gift;

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
		return false;
	}

	@Override
	public Gift find(int id) {
		return null;
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

}

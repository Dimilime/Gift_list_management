package be.project.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import be.project.javabeans.GiftList;
import be.project.javabeans.User;

public class GiftListDAO extends DAO<GiftList>{
	

	public GiftListDAO() {
		
	}

	@Override
	public int insert(GiftList obj) {
		parameters.clear();
		parameters.add("occasion", obj.getOccasion());
		parameters.add("expirationDate", obj.getExpirationDate().toString());
		parameters.add("email", obj.getGiftListUser().getEmail());
		clientResponse=resource
				.path("giftList")
				.header("key",apiKey)
				.post(ClientResponse.class,parameters);
		
		return clientResponse.getStatus() == 201 ? //if client response equals 201 return the id created or 0
				Integer.valueOf(clientResponse.getHeaders().getFirst("idCreated")) : 0;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public boolean update(GiftList obj) {
		return false;
	}

	@Override
	public GiftList find(int id) {
		String responseJSON=resource
				.path("giftList")
				.path(String.valueOf(id))
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		GiftList giftList=null;
		try {
			JSONObject json = new JSONObject(responseJSON);
			giftList = GiftList.mapListFromJson(json);
			return giftList;
		} catch (Exception e) {
			System.out.println("error find de GiftListDAO client = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<GiftList> findAll() {
		
		String responseJSON = resource.path("giftList")
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		
		ArrayList<GiftList> giftLists = new ArrayList<>();
		try {
			JSONArray arrayResponseJSON = new JSONArray(responseJSON);
			for(int i = 0; i < arrayResponseJSON.length(); i++) {
				GiftList giftList = GiftList.mapListFromJson((JSONObject) arrayResponseJSON.get(i));
				giftLists.add(giftList);
			}
			return giftLists;
		} catch (Exception e) {
			System.out.println("error findAll de GiftListDAO client = "+e.getMessage());
			return null;
		}
	}
	
	public boolean addUserTosharedList(GiftList obj, User u) {
		parameters.clear();
		parameters.add("userId", String.valueOf(u.getUserId()));
		parameters.add("listId", String.valueOf(obj.getListId()));
		clientResponse=resource
				.path("giftList")
				.path("sharedList")
				.header("key",apiKey)
				.post(ClientResponse.class,parameters);
		
		return clientResponse.getStatus() == 201 ? true : false;
	}
	

}

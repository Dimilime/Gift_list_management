package be.project.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.project.javabeans.GiftList;

public class GiftListDAO extends DAO<GiftList>{
	

	public GiftListDAO() {
		
	}

	@Override
	public int insert(GiftList obj) {
		parameters = new MultivaluedMapImpl();
		parameters.add("occasion", obj.getOccasion());
		parameters.add("expirationDate", obj.getExpirationDate().toString());
		parameters.add("userId", String.valueOf(obj.getGiftListUser().getUserId()));
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
		return null;
	}

	@Override
	public ArrayList<GiftList> findAll() {
		
		String responseJSON = resource.path("giftList")
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		JSONArray arrayResponseJSON = new JSONArray(responseJSON);
		ArrayList<GiftList> giftLists = new ArrayList<>();
		try {
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
	
	

}

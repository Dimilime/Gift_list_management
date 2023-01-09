package be.project.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

import be.project.javabeans.GiftList;
import be.project.javabeans.User;
import be.project.utils.Utils;

public class GiftListDAO extends DAO<GiftList>{
	

	public GiftListDAO() {
		
	}

	@Override
	public int insert(GiftList obj) {
		String expirationDate = obj.getExpirationDate() == null? null : obj.getExpirationDate().toString();
		parameters.clear();
		parameters.add("occasion", obj.getOccasion());
		parameters.add("expirationDate", expirationDate);
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
		parameters.clear();
		parameters.add("listId",String.valueOf(obj.getListId()));
		parameters.add("occasion", obj.getOccasion());
		parameters.add("expirationDate",Utils.convertLocalDateToString(obj.getExpirationDate()));
		parameters.add("userListId",String.valueOf(obj.getGiftListUser().getUserId()));
		parameters.add("enabled",Utils.convertBoolToString(obj.isEnabled()));
		clientResponse=resource
				.path("giftList")
				.path(String.valueOf(obj.getListId()))
				.header("key",apiKey)
				.put(ClientResponse.class,parameters)
				;
		return clientResponse.getStatus() == 204 ? true : false;
	}
	
	public boolean addSharedUsers(GiftList obj) {
		JSONArray jsonArray= new JSONArray();
		for(User user : obj.getSharedUsers()) {
			jsonArray.put(user.getUserId());
		}
		parameters.clear();
		parameters.add("listId", String.valueOf(obj.getListId()));
		parameters.add("jsonArrayUsersId", jsonArray.toString());
		
		clientResponse=resource
				.path("giftList")
				.path(String.valueOf(obj.getListId()))
				.path("sharedUsers")
				.header("key",apiKey)
				.post(ClientResponse.class,parameters);
		
		return clientResponse.getStatus() == 201 ? true : false;
	}

	@Override
	public GiftList find(int id) {
		clientResponse=resource
				.path("giftList")
				.path(String.valueOf(id))
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		GiftList giftList=null;
		String responseJSON=clientResponse.getEntity(String.class);
		int status=clientResponse.getStatus();
		//gérer cas aucune liste trouvée
		if(status == 404) 
			return null;
		try {
			JSONObject json = new JSONObject(responseJSON);
			giftList = GiftList.mapListFromJson(json);
			return giftList;
		} catch (Exception e) {
			System.out.println("error find de GiftListDAO client = "+e.getMessage());
			return null;
		}
	}
	
	public GiftList findByKey(String key) {
		String responseJSON=resource
				.path("giftList")
				.path("key")
				.path(key)
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
		clientResponse= resource.path("giftList")
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		
		String responseJSON=clientResponse.getEntity(String.class);
		int status=clientResponse.getStatus();
		//gérer cas aucune listes
		if(status == 404) 
			return null;
		ArrayList<GiftList> giftLists = new ArrayList<>();
		try {
			JSONArray arrayResponseJSON = new JSONArray(responseJSON);
			for(int i = 0; i < arrayResponseJSON.length(); i++) {
				GiftList giftList = GiftList.mapListFromJson((JSONObject) arrayResponseJSON.get(i));
				giftLists.add(giftList);
			}
			return giftLists;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error findAll de GiftListDAO client = "+e.getMessage());
			return null;
		}
	}
	
	


	

}

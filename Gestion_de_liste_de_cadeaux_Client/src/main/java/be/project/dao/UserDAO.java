package be.project.dao;

import java.util.ArrayList;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

import be.project.javabeans.GiftList;
import be.project.javabeans.User;

public class UserDAO extends DAO<User>{
	
	public UserDAO() {
		
	}

	@Override
	public int insert(User obj) {
		
		parameters.add("email", obj.getEmail());
		parameters.add("firstname", obj.getFirstname());
		parameters.add("lastname",obj.getLastname());
		parameters.add("password", obj.getPassword());
		clientResponse=resource
				.path("user")
				.header("key",apiKey)
				.post(ClientResponse.class,parameters);
		
		int httpResponseCode=clientResponse.getStatus();
		
		return httpResponseCode == 201 ? 1 : 0;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public boolean update(User obj) {
		return false;
	}
	
	@Override
	public User find(int id) {
		return null;
	}
	
	public User findByEmail(String email) {
		String responseJSON=resource
				.path("user")
				.path(email)
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		System.out.println(responseJSON);
		User user=null;
		try {
			JSONObject json = new JSONObject(responseJSON);
			user = User.getUserByJSONObject(json);
			return user;
		} catch (Exception e) {
			System.out.println("error find de UserDAO client = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<User> findAll() {
		String responseJSON = resource.path("user")
				.header("key",apiKey)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		JSONArray arrayResponseJSON = new JSONArray(responseJSON);
		ArrayList<User> users = new ArrayList<>();
		try {
			for(int i = 0; i < arrayResponseJSON.length(); i++) {
				User user = User.getUserByJSONObject((JSONObject) arrayResponseJSON.get(i));
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			System.out.println("error findAll de UserDAO client = "+e.getMessage());
			return null;
		}
	}

	public boolean login(String email, String password) {
		boolean success=false;
		parameters.add("email",email);
		parameters.add("password", password);
		clientResponse=resource
				.path("user")
				.path("login")
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class,parameters);
		
		String response=clientResponse.getEntity(String.class);
		int status=clientResponse.getStatus();
		JSONObject jsonResponse = new JSONObject(response);
		if(status==200) {
			//status ok, on verifie la reponse json
			if(jsonResponse.has("connected")) {
				if(jsonResponse.getString("connected") !=null) {
					String connected=jsonResponse.getString("connected");
					if(connected.equals("true")) {
						success=true;
					}
				}
			}
		
		}
		return success;
	}

	
}

package be.project.dao;

import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import be.project.javabeans.User;

public class UserDAO extends DAO<User>{
	
	public UserDAO() {
		
	}

	@Override
	public boolean insert(User obj) {
		boolean success=false;
		String key=getApiKey();
		parameters.add("email", obj.getEmail());
		parameters.add("firstname", obj.getFirstname());
		parameters.add("lastname",obj.getLastname());
		parameters.add("password", obj.getPassword());
		clientResponse=resource
				.path("user")
				.path("create")
				.header("key",key)
				.post(ClientResponse.class,parameters)
				;
		int httpResponseCode=clientResponse.getStatus();
		if(httpResponseCode == 201) {
			success=true;
		}
		return success;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User find(String email) {
		String key=getApiKey();
		parameters.add("email", email);
		String responseJSON=resource
				.path("user")
				.queryParams(parameters)
				.header("key",key)
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
		// TODO Auto-generated method stub
		return null;
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

package be.project.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.project.javabeans.User;

public class UserDAO implements DAO<User>{
	
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public UserDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(User obj) {
		boolean success=false;
		String key=getApiKey();	
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("email", obj.getEmail());
		parameters.add("firstname", obj.getFirstname());
		parameters.add("lastname",obj.getLastname());
		parameters.add("password", obj.getPassword());
		ClientResponse res=resource
				.path("user")
				.path("create")
				.header("key",key)
				.post(ClientResponse.class,parameters)
				;
		int httpResponseCode=res.getStatus();
		if(httpResponseCode == 201) {
			success=true;
		}
		return success;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
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
		String responseJSON=resource
				.path("user")
				.path(email)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
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
		MultivaluedMap<String,String> paramsPost=new MultivaluedMapImpl();
		paramsPost.add("email",email);
		paramsPost.add("password", password);
		ClientResponse res=resource
				.path("user")
				.path("login")
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class,paramsPost)
				;
		String response=res.getEntity(String.class);
		int status=res.getStatus();
		JSONObject jsonResponse = new JSONObject(response);
		if(status==200) {
			//status ok, on verifie la reponse json
			if(jsonResponse.has("connected")) {
				if(jsonResponse.getString("connected") !=null) {
					String connected=jsonResponse.getString("connected");
					if(connected.equals("true")) {
						success=true;
						MultivaluedMap<String, String> headers;
						headers=res.getHeaders();
						List<String> apiKey=headers.get("api-key");
						saveApiKey(apiKey.get(0));
					}
				}
			}
		
		}
		return success;
	}
	
	private static void saveApiKey(String apiKey) {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			env.addToEnvironment("apiKey", apiKey);
		} catch (NamingException e) {
			System.out.println("Error save api key");
		}
	}

}

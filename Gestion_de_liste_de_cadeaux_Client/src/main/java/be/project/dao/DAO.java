package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public abstract class DAO<T> {
	
	public abstract int insert(T obj);
	
	public abstract boolean delete(int id);
	
	public abstract boolean update(T obj);
	
	public abstract T find(int id);
	
	public abstract ArrayList<T> findAll();
	
	protected static  String apiUrl;
	protected Client client;
	protected WebResource resource;
	protected MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
	protected ClientResponse clientResponse;
	protected String apiKey;
	protected ObjectMapper mapper;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public DAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
		apiKey = getApiKey();
		mapper = new ObjectMapper();
	}

	private String getApiUrl() {
		Context ctx;
		String api="";
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
		    api= (String) env.lookup("apiUrl");
		} catch (NamingException e) {
			System.out.println("Error to get api url");
		}
		return api;
	}
	
	protected String getApiKey() {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			return (String) env.lookup("apiKey");
		} catch (NamingException e) {
			System.out.println("Error to get api key");
			return "";
		}
	}

}

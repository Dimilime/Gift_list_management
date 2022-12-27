package be.project.dao;

import java.util.ArrayList;

import com.sun.jersey.api.client.ClientResponse;
import be.project.javabeans.GiftList;

public class GiftListDAO extends DAO<GiftList>{
	

	public GiftListDAO() {
		
	}

	@Override
	public int insert(GiftList obj) {
		
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(GiftList obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GiftList find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GiftList> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

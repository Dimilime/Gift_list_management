package be.project.dao;

import java.util.ArrayList;

import com.sun.jersey.api.client.ClientResponse;
import be.project.javabeans.GiftList;

public class GiftListDAO extends DAO<GiftList>{
	

	public GiftListDAO() {
		
	}

	@Override
	public boolean insert(GiftList obj) {
		boolean success=false;
		parameters.add("occasion", obj.getOccasion());
		clientResponse=resource
				.path("giftList")
				.post(ClientResponse.class,parameters);
		int httpResponseCode=clientResponse.getStatus();
		if(httpResponseCode == 201) {
			success=true;
		}
		return success;
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

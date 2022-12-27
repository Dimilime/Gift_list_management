package be.project.dao;

import java.util.ArrayList;

import com.sun.jersey.api.client.ClientResponse;

import be.project.javabeans.Gift;

public class GiftDAO extends DAO<Gift> {
	
	public GiftDAO() {
	}

	@Override
	public int insert(Gift obj) {
		
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
		return null;
	}

}

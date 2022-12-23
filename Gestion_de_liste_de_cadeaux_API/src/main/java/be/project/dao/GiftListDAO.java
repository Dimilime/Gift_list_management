package be.project.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.project.models.GiftList;

public class GiftListDAO extends DAO<GiftList>{

	public GiftListDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(GiftList obj) {
		return 0;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public int update(GiftList obj) {
		return 0;
	}

	@Override
	public GiftList find(String id) {
		return null;
	}

	@Override
	public ArrayList<GiftList> findAll() {
		return null;
	}

}

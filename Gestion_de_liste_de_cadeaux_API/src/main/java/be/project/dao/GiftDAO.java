package be.project.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.project.models.Gift;

public class GiftDAO extends DAO<Gift> {

	public GiftDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Gift obj) {
		return 0;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public int update(Gift obj) {
		return 0;
	}

	@Override
	public Gift find(String id) {
		return null;
	}

	@Override
	public ArrayList<Gift> findAll() {
		return null;
	}

}

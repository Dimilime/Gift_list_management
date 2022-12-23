package be.project.dao;

import java.util.ArrayList;

import be.project.javabeans.Gift;

public class GiftDAO extends DAO<Gift> {
	
	public GiftDAO() {
	}

	@Override
	public boolean insert(Gift obj) {
		return false;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public boolean update(Gift obj) {
		return false;
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

package be.project.dao;

import java.util.ArrayList;

import be.project.javabeans.Notification;

public class NotificationDAO extends DAO<Notification>{
	
	public NotificationDAO() {
		
	}

	@Override
	public boolean insert(Notification obj) {
		return false;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public boolean update(Notification obj) {
		return false;
	}

	@Override
	public Notification find(String id) {
		return null;
	}

	@Override
	public ArrayList<Notification> findAll() {
		return null;
	}
	

}

package be.project.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.project.models.Notification;

public class NotificationDAO extends DAO<Notification> {

	public NotificationDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Notification obj) {
		return 0;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public int update(Notification obj) {
		return 0;
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

package be.project.dao;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {
	
	protected Connection conn;
	
	public DAO(Connection connection) {
		conn= connection;
	}

	public abstract int insert(T obj);
	
	public abstract boolean delete(int id);
	
	public abstract int update(T obj);
	
	public abstract T find(int id);
	
	public abstract ArrayList<T> findAll();
}

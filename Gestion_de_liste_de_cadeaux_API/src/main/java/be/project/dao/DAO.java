package be.project.dao;

import java.util.ArrayList;

public interface DAO<T> {

	public int insert(T obj);
	
	public boolean delete(String id);
	
	public int update(T obj);
	
	public T find(String id);
	
	public ArrayList<T> findAll();
}

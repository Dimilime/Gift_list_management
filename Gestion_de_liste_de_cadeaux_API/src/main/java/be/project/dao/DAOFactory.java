package be.project.dao;

import java.sql.Connection;

import be.project.models.*;

public class DAOFactory extends AbstractDAOFactory {
	
	protected static final Connection conn = DatabaseConnection.getInstance();
	
	@Override
	public DAO<User> getUserDAO() {
		return new UserDAO(conn);
	}

	@Override
	public DAO<GiftList> getGiftListDAO() {
		return new GiftListDAO(conn);
	}

	@Override
	public DAO<Gift> getGiftDAO() {
		return new GiftDAO(conn);
	}

	@Override
	public DAO<Notification> getNotificationDAO() {
		return new NotificationDAO(conn);
	}

	
	

}

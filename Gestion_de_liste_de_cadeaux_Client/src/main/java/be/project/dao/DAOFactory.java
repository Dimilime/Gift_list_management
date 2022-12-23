package be.project.dao;

import be.project.javabeans.*;

public class DAOFactory extends AbstractDAOFactory {
	
	@Override
	public DAO<User> getUserDAO() {
		return new UserDAO();
	}

	@Override
	public DAO<GiftList> getGiftListDAO() {
		return new GiftListDAO();
	}

	@Override
	public DAO<Gift> getGiftDAO() {
		return new GiftDAO();
	}

	@Override
	public DAO<Notification> getNotificationDAO() {
		return new NotificationDAO();
	}

	
	

}

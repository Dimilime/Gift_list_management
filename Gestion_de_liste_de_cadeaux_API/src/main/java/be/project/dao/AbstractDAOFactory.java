package be.project.dao;
import be.project.models.*;

public abstract class AbstractDAOFactory {
	
	public static final int DAO_FACTORY = 0;
	public static final int XML_DAO_FACTORY = 1;
	
	public abstract DAO<User> getUserDAO();
	
	public abstract DAO<GiftList> getGiftListDAO();
	
	public abstract DAO<Gift> getGiftDAO();
		
	public abstract DAO<Notification> getNotificationDAO();
	
	public static AbstractDAOFactory getFactory(int type){
		switch(type){
		case DAO_FACTORY:
			return new DAOFactory();
			default:
				return null;
		}
	}

}

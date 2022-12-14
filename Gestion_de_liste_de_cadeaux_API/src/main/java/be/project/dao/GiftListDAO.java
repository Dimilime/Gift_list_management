package be.project.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

import be.project.models.GiftList;
import be.project.models.User;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class GiftListDAO extends DAO<GiftList>{

	public GiftListDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(GiftList obj) {
		int id=0;
		String sql="{call INSERT_LIST(?,?,?,?,?)}";
		try(CallableStatement callableStatement = conn.prepareCall(sql)) {
			callableStatement.setDate(1, obj.parseExpirationDateToDate());
			callableStatement.setString(2, obj.getOccasion());
			callableStatement.setString(3, obj.returnEnabledAsString());
			callableStatement.setInt(4, obj.getGiftListUser().getUserId());
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			id = callableStatement.getInt(5);
			
		}catch(SQLException e) {
			return id;
		}
		return id;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public int update(GiftList obj) {
		int codeError = -1;
		try(CallableStatement callableStatement = conn.prepareCall("{call update_giftList(?,?,?,?,?)}")) {
				callableStatement.setInt(1, obj.getListId());
				callableStatement.setString(2, obj.getOccasion());
				callableStatement.setDate(3, obj.parseExpirationDateToDate());
				callableStatement.setString(4, obj.returnEnabledAsString());
				callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
				callableStatement.executeUpdate();
				codeError=callableStatement.getInt(5);
		}catch(SQLException e) {
			System.out.println("Exception dans update update giftList Api "+ e.getMessage());
			return codeError;
		}
		return codeError;
	}

	@Override
	public GiftList find(int id) {
		UserDAO userDao = new UserDAO(conn);
		Struct struc=null;
		GiftList giftList=null;
		String sql="{call getGiftList(?,?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "GIFTLIST_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
 			Object[] objects = struc != null ? struc.getAttributes() : null;
			if(objects != null) {
				int giftListId= Integer.valueOf(objects[0].toString());
				String expirationDate=objects[1] != null ? objects[1].toString() : null;
				String occasion=objects[2].toString();
				String enabled =objects[3].toString();
				String key = (String)objects[4];
				int userId = Integer.valueOf(objects[5].toString());
				User u = userDao.find(userId);
				Array array = (Array) objects[6];
				Object[] sharedUsers = (Object[]) array.getArray();
				ArrayList<User> listSharedUsers = new ArrayList<>();
				if(sharedUsers != null) {
					for (int i = 0; i < sharedUsers.length; i++) {
						String  sharedUserId = sharedUsers[i].toString();
						User sharedUser = userDao.find(Integer.valueOf(sharedUserId) );
						listSharedUsers.add(sharedUser);
					}
				}
				giftList = new GiftList(giftListId,occasion, u, expirationDate, key, enabled);
				giftList.setSharedUsers(listSharedUsers);
			}
		
		} catch (NumberFormatException | SQLException e ) {
			return null;
		} 

		return giftList;
	}
	
	public GiftList findByKey(String key) {
		UserDAO userDao = new UserDAO(conn);
		Struct struc=null;
		GiftList giftList=null;
		String sql="{call getGiftListByKey(?,?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.setString(1, key);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "GIFTLIST_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
 			Object[] objects = struc != null ? struc.getAttributes() : null;
			if(objects != null) {
				int giftListId= Integer.valueOf(objects[0].toString());
				String expirationDate=objects[1] != null ? objects[1].toString() : null;
				String occasion=objects[2].toString();
				String enabled =objects[3].toString();
				String keyDB = (String)objects[4];
				int userId = Integer.valueOf(objects[5].toString());
				User u = userDao.find(userId);
				
				giftList = new GiftList(giftListId,occasion, u, expirationDate, keyDB, enabled);
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return giftList;
	}

	@Override
	public ArrayList<GiftList> findAll() {
		UserDAO userDao = new UserDAO(conn);
		Array array=null;
		ArrayList<GiftList> giftLists= new ArrayList<>();
		String sql="{call getAllList(?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			callableStatement.registerOutParameter(1, OracleTypes.ARRAY, "TABLE_LIST");
			callableStatement.execute();
			array = callableStatement.getArray(1);
			Object [] objects = (Object[]) array.getArray();
			if(objects != null) {
				for (int i = 0; i < objects.length; i++) {
					Object [] os = ((Struct)objects[i]).getAttributes();
					if(os != null) {
						int giftListId= Integer.valueOf(os[0].toString());
						String expirationDate=os[1] != null? os[1].toString() : null;
						String occasion=os[2].toString();
						String enabled =os[3].toString();
						String key = (String)os[4];
						int userId = Integer.valueOf(os[5].toString());
						User u = userDao.find(userId);
						Array arrayShared = (Array) os[6];
						Object[] sharedUsers = (Object[]) arrayShared.getArray();
						ArrayList<User> listSharedUsers = new ArrayList<>();
						if(sharedUsers != null) {
							for (int j = 0; j < sharedUsers.length; j++) {
								String  sharedUserId = sharedUsers[j].toString();
								User sharedUser = userDao.find(Integer.valueOf(sharedUserId));
								listSharedUsers.add(sharedUser);
							}						
						}
						GiftList giftList = new GiftList(giftListId,occasion, u, expirationDate, key, enabled);
						giftList.setSharedUsers(listSharedUsers);
						giftLists.add(giftList);		
					}
				}
			}

		} catch (NumberFormatException | SQLException e) {
			return null;
		} 


		return giftLists;
	}
	


	public boolean addSharedList(int listId, int[] sharedUsersId) {
		int codeError = -1;
		try(CallableStatement callableStatement = conn.prepareCall("{call insert_sharedList(?,?,?)}")) {
				callableStatement.setInt(1, listId);
				ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("NUM_ARRAY", conn);
				ARRAY array = new ARRAY(descriptor, conn, sharedUsersId);
				callableStatement.setArray(2, array);
				callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
				callableStatement.executeUpdate();
				codeError=callableStatement.getInt(3);
		}catch(SQLException e) {
			System.out.println("Exception dans giftlistdao de l'api -> addSharedList "+ e.getMessage());
		}
		return codeError == 0 ? true : false;
	}

}

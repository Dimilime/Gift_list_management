package be.project.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

import be.project.models.Gift;
import be.project.models.GiftList;
import be.project.models.User;
import oracle.jdbc.OracleTypes;

public class GiftDAO extends DAO<Gift> {

	public GiftDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Gift obj) {
		int id=0, cpt=1;
		String sql="{call INSERT_GIFT(?,?,?,?,?,?,?,?,?)}";
		try(CallableStatement callableStatement = conn.prepareCall(sql)) {
			callableStatement.setString(cpt++, obj.getName());
			callableStatement.setString(cpt++, obj.getDescription());
			callableStatement.setDouble(cpt++, obj.getAveragePrice());
			callableStatement.setInt(cpt++, obj.getPriorityLevel());
			callableStatement.setString(cpt++, obj.getReservedAsString());
			callableStatement.setBlob(cpt++, obj.getImage());
			callableStatement.setString(cpt++, obj.getLink());
			callableStatement.setInt(cpt++, obj.getGiftList().getListId());
			callableStatement.registerOutParameter(cpt, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			id = callableStatement.getInt(cpt);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public int update(Gift obj) {
		return 0;
	}

	@Override
	public Gift find(int id) {
		GiftListDAO giftListDAO = new GiftListDAO(conn);
		Struct struc=null;
		Gift gift=null;
		String sql="{call getGift(?,?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "GIFT_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
			Object[] objects = struc.getAttributes();
			if(objects != null) {
				int giftId= Integer.valueOf(objects[0].toString());
				String name=objects[1].toString();
				String description= (String)objects[2];//can be null
				Double averagePrice =Double.valueOf( objects[3].toString());
				int priorityLevel =  Integer.valueOf( objects[4].toString());
				String reserved = objects[5].toString();
				String link = (String)objects[6];
				Blob img = null;//TODO: get image objects[7]
				int listId = Integer.valueOf(objects[8].toString());
				GiftList giftList = giftListDAO.find(listId);
				gift = new Gift(giftId, priorityLevel, name, description, listId, reserved, link, img, giftList);
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
		e.printStackTrace();
		}

		return gift;
	}

	@Override
	public ArrayList<Gift> findAll() {
		return null;
	}

}

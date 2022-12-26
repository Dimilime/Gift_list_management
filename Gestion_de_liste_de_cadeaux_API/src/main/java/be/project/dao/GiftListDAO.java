package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.time.LocalDate;
import java.util.ArrayList;

import be.project.models.GiftList;
import be.project.models.User;
import oracle.jdbc.OracleTypes;

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
			callableStatement.setString(3, obj.getEnabledAsString());
			callableStatement.setInt(4, obj.getGiftListUser().getUserId());
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			id = callableStatement.getInt(5);
			
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
	public int update(GiftList obj) {
		return 0;
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
			Object[] objects = struc.getAttributes();
			if(objects != null) {
				int giftListId= Integer.valueOf(objects[0].toString());
				String expirationDate=objects[1].toString().split(" ")[0].replace("-", "/");
				String occasion=objects[2].toString();
				String enabled =objects[3].toString();
				String key = (String)objects[4];
				int userId = Integer.valueOf(objects[5].toString());
				User u = userDao.find(userId);
				giftList = new GiftList(giftListId,occasion, u, expirationDate, key, enabled);
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
		return null;
	}

}

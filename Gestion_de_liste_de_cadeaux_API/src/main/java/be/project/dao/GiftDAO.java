package be.project.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Base64;

import be.project.models.Gift;
import be.project.models.GiftList;
import be.project.models.Participation;
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
			System.out.println("giftdao api a recu: "+ obj.getImage());
			byte [] bytes = Base64.getDecoder().decode(obj.getImage().getBytes());
			InputStream inputStreamImg = new ByteArrayInputStream(bytes);
			callableStatement.setBlob(cpt++, inputStreamImg);
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
		int codeError = -1;
		try(CallableStatement callableStatement = conn.prepareCall("{call update_gift(?,?,?,?,?,?,?)}")) {
				callableStatement.setInt(1, obj.getGiftId());
				callableStatement.setString(2, obj.getName());
				callableStatement.setString(3, obj.getDescription());
				callableStatement.setDouble(4, obj.getAveragePrice());
				callableStatement.setInt(5, obj.getPriorityLevel());
				callableStatement.setString(6, obj.getLink());
				callableStatement.registerOutParameter(7, java.sql.Types.INTEGER);
				callableStatement.executeUpdate();
				codeError=callableStatement.getInt(7);
		}catch(SQLException e) {
			System.out.println("Exception dans update giftDAO Api "+ e.getMessage());
		}
		return codeError;
	}

	@Override
	public Gift find(int id) {
		GiftListDAO giftListDAO = new GiftListDAO(conn);
		Struct struc=null;
		Gift gift=null;
		ArrayList<Participation> participations=null;
		int cpt=0;
		String sql="{call getGift(?,?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, OracleTypes.STRUCT, "GIFT_OBJECT");
			callableStatement.execute();
			
			struc = (Struct) callableStatement.getObject(2);
			Object[] objects = struc == null ? null : struc.getAttributes();
			if(objects != null) {
				int giftId= Integer.valueOf(objects[0].toString());
				String name=objects[1].toString();
				String description= (String)objects[2];//can be null
				Double averagePrice =Double.valueOf( objects[3].toString());
				int priorityLevel =  Integer.valueOf( objects[4].toString());
				String reserved = objects[5].toString();
				String link = (String)objects[6];
				Blob imgBlob = (Blob)objects[7];
				String img =null;
				if(imgBlob !=null) {
					InputStream inputStream = imgBlob.getBinaryStream();
					byte [] bytes = inputStream.readAllBytes();

					img =  Base64.getEncoder().encodeToString(bytes);
				}
				
				int listId = Integer.valueOf(objects[8].toString());
				
				//participations
				Array array = (Array) objects[9];
				Object[] participationsArray = (Object[])array.getArray();
				if(participationsArray != null && participationsArray.length>0) {
					cpt++;
					if(cpt==1) {
						participations = new ArrayList<Participation>();
					}
					for(int i=0; i<participationsArray.length;i++) {
						Struct structCast = (Struct)participationsArray[i];
						Object[] objCast = structCast.getAttributes();
	
						int userId  = Integer.valueOf(objCast[0].toString());
						int giftID  = Integer.valueOf(objCast[1].toString());
						double pricePart  = Double.valueOf(objCast[2].toString());
						
						User u = new User();
						u.setUserId(userId);
						if(giftID==giftId) {
							Participation participation = new Participation(0,u,pricePart,null);
							participations.add(participation);
						}
					
					}
					
				}
				GiftList giftList = giftListDAO.find(listId);
				gift = new Gift(giftId, priorityLevel, name, description, averagePrice, reserved, link, img, giftList);
				if(participations!=null) {
					gift.setParticipations(participations);
				}
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return gift;
	}

	@Override
	public ArrayList<Gift> findAll() {
		GiftListDAO giftListDAO = new GiftListDAO(conn);
		Array array=null;
		ArrayList<Gift> gifts= new ArrayList<>();
		
		String sql="{call getAllGift(?)}";
		try (CallableStatement callableStatement = conn.prepareCall(sql)){
			
			callableStatement.registerOutParameter(1, OracleTypes.ARRAY, "TABLE_GIFT");
			callableStatement.execute();
			
			array = callableStatement.getArray(1);
			Object [] objects = (Object[]) array.getArray();
			if(objects != null) {
				for (int i = 0; i < objects.length; i++) {
					ArrayList<Participation> participations=null;
					Object [] os = ((Struct)objects[i]).getAttributes();
					if(os != null) {
						int giftId= Integer.valueOf(os[0].toString());
						String name=os[1].toString();
						String description= (String)os[2];
						Double averagePrice =Double.valueOf( os[3].toString());
						int priorityLevel =  Integer.valueOf( os[4].toString());
						String reserved = os[5].toString();
						String link = (String)os[6];
						Blob imgBlob = (Blob)os[7];
						String img =null;
						if(imgBlob !=null) {
							InputStream inputStream = imgBlob.getBinaryStream();
							byte [] bytes = inputStream.readAllBytes();

							img =  Base64.getEncoder().encodeToString(bytes);
						}
						int listId = Integer.valueOf(os[8].toString());
						
						//participations
						Array sqlArray = (Array) os[9];
						Object[] participationsArray = (Object[])sqlArray.getArray();
						if(participationsArray != null && participationsArray.length>0) {
							participations = new ArrayList<Participation>();
							for(int j=0; j<participationsArray.length;j++) {
								
								Struct structCast = (Struct)participationsArray[j];
								Object[] objCast = structCast.getAttributes();
			
								int userId  = Integer.valueOf(objCast[0].toString());
								int giftID  = Integer.valueOf(objCast[1].toString());
								double pricePart  = Double.valueOf(objCast[2].toString());
								User u = new User();
								u.setUserId(userId);
								if(giftID==giftId) {
									Participation participation = new Participation(0,u,pricePart,null);
									participations.add(participation);
								}
							}
						}
						
						GiftList giftList = giftListDAO.find(listId);
						Gift gift = new Gift(giftId, priorityLevel, name, description, averagePrice , reserved, link, img, giftList);
						if(participations!=null) {
							gift.setParticipations(participations);
						}
						
						gifts.add(gift);		
					}	
				}
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return gifts;
	}

	public boolean addOffer(Gift gift) {
		int codeError = -1;
		ArrayList<Participation> participations = gift.getParticipations();
		try(CallableStatement callableStatement = conn.prepareCall("{call insert_offer(?,?,?,?)}")) {
				int userid = participations.get(0).getParticipant().getUserId();
				double price = participations.get(0).getParticipationpart();
				int giftId = gift.getGiftId();
				System.out.println("dans addOffer giftdao api, on passe les valeurs : ");
				System.out.println(giftId);
				System.out.println(price);
				System.out.println(userid);
				callableStatement.setInt(1, giftId);
				callableStatement.setInt(2, userid);
				callableStatement.setDouble(3, price);
				callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
				callableStatement.executeUpdate();
				codeError=callableStatement.getInt(4);
				System.out.println("Code erreur : " + codeError);
		}catch(SQLException e) {
			System.out.println("Exception dans giftdao de l'api -> addOffer "+ e.getMessage());
		}
		return codeError == 0 ? true : false;
	}

}

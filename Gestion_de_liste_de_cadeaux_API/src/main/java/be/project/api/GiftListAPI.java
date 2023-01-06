package be.project.api;

import java.util.ArrayList;
import java.util.Arrays;

//import java.time.LocalDate;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.GiftList;
import be.project.models.User;

@Path("/giftList")
public class GiftListAPI extends API{
	
	@POST
	public Response createGiftList(
			@FormParam("occasion") String occasion,
			@FormParam("expirationDate") String expirationDate,
			@FormParam("email") String email,
			@HeaderParam("key") String key
			)
	{
		
		if(key.equals(apiKey)) 
		{ 
			
			if(occasion == null || email == null)
				return Response.status(Status.BAD_REQUEST).build();
			System.out.println(expirationDate);
			User user = User.getUserByEmail(email);
			GiftList giftList= new GiftList(0,occasion, user, expirationDate,null, "Y");
			int giftListId=giftList.create();
			if(giftListId != 0) {
				return Response
						.status(Status.CREATED)
						.header("Location","/Gestion_de_liste_de_cadeaux_API/api/giftList/"+giftListId).header("idCreated", giftListId)
						.build();
			}
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
		
	}
	
	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateInfraction(@PathParam("id") int listId,
			@FormParam("listId") int listIdForm,
			@FormParam("occasion") String occasion,
			@FormParam("expirationDate") String expirationDate,
			@FormParam("sharedUsersId") String JSONsharedUsersId,
			@FormParam("userListId") int userListId,
			@FormParam("enabled") String enabled,
			@HeaderParam("key") String key) {
		if(key.equals(apiKey)) {
			try {
				if(listIdForm == 0 || occasion == null || userListId ==0 || listId!=listIdForm)
					return Response.status(Status.BAD_REQUEST).build();
				User connectedUser = new User();
				connectedUser.setUserId(userListId);
				//create sharedUser
				ArrayList<User> sharedUsers = new ArrayList<User>();
				String[] sharedUsersID = JSONsharedUsersId.replaceAll("\\[", "")
                          .replaceAll("]", "")
                          .split(",");
				for(int i=0;i<sharedUsersID.length;i++) {
					User user = new User();
					user.setUserId(Integer.valueOf(sharedUsersID[i]));
					sharedUsers.add(user);
				}
			
				GiftList giftList = new GiftList(listId,occasion,expirationDate,null, sharedUsers, connectedUser, null, enabled);
				int updateCode=giftList.update();
				if(updateCode==0){
					return Response.status(Status.NO_CONTENT).build();
				}else {
					return Response.status(Status.OK).build();
				}
			} catch (Exception e) {
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGiftList(@PathParam("id") int id,
			@HeaderParam("key") String key) {
		if(key!=null) {
			if(key.equals(apiKey)) {
				GiftList giftList=GiftList.getGiftList(id);
				if(giftList == null)
					return Response.status(Status.NOT_FOUND).build();
				
				return Response.status(Status.OK).entity(giftList).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllList(@HeaderParam("key") String key) {
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<GiftList> giftLists=GiftList.getAll();
				if(giftLists == null || giftLists.isEmpty())
					return Response.status(Status.NOT_FOUND).build();
				
				return Response.status(Status.OK).entity(giftLists).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	
	
}

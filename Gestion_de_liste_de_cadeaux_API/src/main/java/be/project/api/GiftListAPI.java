package be.project.api;

//import java.time.LocalDate;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
			@FormParam("userId") int userId,
			@HeaderParam("key") String key
			)
	{
		
		if(key.equals(apiKey)) 
		{ 
			
			if(occasion == null || userId != 0)
				return Response.status(Status.BAD_REQUEST).build();
			System.out.println(expirationDate);
			User user = User.getUser(userId);
			GiftList giftList= new GiftList(0,occasion, user, expirationDate,key, "Y");
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
	
}

package be.project.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.GiftList;
import be.project.models.User;

@Path("/sharedList")
public class SharedListAPI extends API {
	
	@POST
	public Response createGiftList(
			@FormParam("userId") int userId,
			@FormParam("listId") int listId,
			@HeaderParam("key") String key
			)
	{
		
		if(key.equals(apiKey)) 
		{ 
			
			if(userId != 0 || listId != 0)
				return Response.status(Status.BAD_REQUEST).build();
			
			User user = User.get(userId);
			GiftList giftList= GiftList.getGiftList(listId);
			
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
}

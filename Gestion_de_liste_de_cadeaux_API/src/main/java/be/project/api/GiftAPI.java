package be.project.api;

import java.sql.Blob;
import java.util.ArrayList;

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

import be.project.models.Gift;
import be.project.models.GiftList;

@Path("/gift")
public class GiftAPI extends API {
	
	
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response getAllGift(@HeaderParam("key") String key) {
			if(key!=null) {
				if(key.equals(apiKey)) {
					ArrayList<Gift> gifts=Gift.getAll();
					if(gifts == null || gifts.isEmpty())
						return Response.status(Status.NOT_FOUND).build();
					
					return Response.status(Status.OK).entity(gifts).build();
				}
			}
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		
		@PUT
		@Path("{id}")
		public Response updateGift(@PathParam("id") int giftId,
				@FormParam("giftId") int giftIdInForm,
				@FormParam("giftName") String name,
				@FormParam("description") String description,
				@FormParam("price") double price,
				@FormParam("priorityLevel") int priorityLevel,
				@FormParam("image") String image,
				@FormParam("link") String link,
				@HeaderParam("key") String key) {
			System.out.println("arrive dans api");
			if(key.equals(apiKey)) {
				try {
					if(giftId!=giftIdInForm)  
						return Response.status(Status.BAD_REQUEST).build();
					
					Gift gift = new Gift(giftId,priorityLevel,name,description,price,"N",link,null,null);
	
					int updateCode=gift.update();
					if(updateCode==0){
						return Response.status(Status.NO_CONTENT).build();
					}else {
						return Response.status(Status.OK).build();
					}
					
				} catch (Exception e) {
					System.out.println("Exception dans giftAPI update " + e.getMessage());
					return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
				}
			}
			return Response.status(Status.UNAUTHORIZED).build();
		}
	
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response createGift(
				@FormParam("giftName") String giftName,
				@FormParam("description") String description,
				@FormParam("averagePrice") double averagePrice,
				@FormParam("priorityLevel") int priorityLevel,
				@FormParam("giftImg") String giftImg,
				@FormParam("link") String link,
				@FormParam("listId") int listId,
				@HeaderParam("key") String key
				)
		{
			
			if(key.equals(apiKey)) 
			{ 
				if(giftName == null || averagePrice == 0 || priorityLevel == 0 || listId == 0)
					return Response.status(Status.BAD_REQUEST).build();
				
				GiftList giftList = GiftList.getGiftList(listId);

				try {

					Double avgPrice = Double.valueOf(averagePrice);
					int priorityLvl = Integer.valueOf(priorityLevel);
					Gift gift= new Gift(0,priorityLvl,giftName, description, avgPrice,"N",link,giftImg,giftList);
					int giftId=gift.create();
					
					if(giftId != 0) {
							return Response
									.status(Status.CREATED)
									.header("Location","/Gestion_de_liste_de_cadeaux_API/api/gift/"+giftId).header("idCreated", giftId)
									.build();
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}
			return Response.status(Status.UNAUTHORIZED).build();
			
		}
		
	
		
		
		
}

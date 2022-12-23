package be.project.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.User;

@Path("/giftList")
public class GiftListAPI extends API{
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createGiftList(
			@FormParam("occasion") String occasion,
			@FormParam("expirationDate") String expirationDate)
	{
		User user=null;
		String apiKey=getApiKey();
			try {
				//user = new User( firstname, lastname, email, password);
				int userId=user.insertUser();
				if(userId != 0) {
					return Response
							.status(Status.CREATED)
							.build();
				}else {
					return Response.status(Status.NOT_ACCEPTABLE).build();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
			}
		
		
		
	}
	
}

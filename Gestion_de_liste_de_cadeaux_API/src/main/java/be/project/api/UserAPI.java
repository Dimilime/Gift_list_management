package be.project.api;

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

import be.project.models.User;

@Path("/user")
public class UserAPI extends API {
	
	@GET
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("email") String email,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				User user=User.getUser(email);
				return Response.status(Status.OK).entity(user).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("email") String email, 
			@FormParam("password") String password) {
		String responseJSON;
		boolean success= User.login(email, password);
		if(success) {
			responseJSON="{\"connected\":\"true\"}";
			String apiKey=getApiKey();
			return Response.status(Status.OK)
					.header("api-key", apiKey)
					.entity(responseJSON).build();
		}else {
			return Response.status(Status.OK).entity("{\"error\":\"login failed\"}").build();
		}
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(
			@FormParam("email") String email,
			@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("password") String password,
			@HeaderParam("key") String key)
	{
		User user=null;
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				user = new User( firstname, lastname, email, password);
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
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		
	}
	
}

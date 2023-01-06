package be.project.api;

import java.util.ArrayList;

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
	public Response getUserByEmail(@PathParam("email") String email,
			@HeaderParam("key") String key) {
		if(key!=null) {
			if(key.equals(apiKey)) {
				User user=User.getUserByEmail(email);
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
			return Response.status(Status.OK)
					.header("api-key", apiKey)
					.entity(responseJSON).build();
		}else {
			return Response.status(Status.OK).entity("{\"error\":\"login failed\"}").build();
		}
	}
	
	@POST
	public Response createUser(
			@FormParam("email") String email,
			@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("password") String password,
			@HeaderParam("key") String key)
	{
		User user=null;
		if(key.equals(apiKey)) {
			try {
				user = new User( firstname, lastname, email, password);
				int userId=user.insertUser();
				if(userId != 0) {
					return Response
							.status(Status.CREATED)
							.header("Location", "/Gestion_de_liste_de_cadeaux_API/api/user/"+userId)
							.build();
				}else {
					return Response.status(Status.SERVICE_UNAVAILABLE).build();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
			}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@HeaderParam("key") String key) {
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<User> users=User.getAll();
				if(users == null || users.isEmpty())
					return Response.status(Status.NOT_FOUND).build();
				
				return Response.status(Status.OK).entity(users).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
}

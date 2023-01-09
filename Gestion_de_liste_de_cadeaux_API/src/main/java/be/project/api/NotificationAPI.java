package be.project.api;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Notification;
import be.project.models.User;

@Path("/notification")
public class NotificationAPI extends API{
		
		@POST
		public Response createNotification(
				@FormParam("title") String title,
				@FormParam("message") String message,
				@FormParam("users") String users,
				@HeaderParam("key") String key
				)
		{
			
			if(key.equals(apiKey)) 
			{ 

				if(title == null || message == null || users == null)
					return Response.status(Status.BAD_REQUEST).build();
				Notification notification = new Notification(0,title,message);
				String emails [] = users.split(",");
				ArrayList<User> us = new ArrayList<>();
				for(String email : emails) {
					us.add(User.getUserByEmail(email));
				}
				notification.setUsers(us);
				int notificationId=notification.create();
				if(notificationId != 0) {
					return Response
							.status(Status.CREATED)
							.header("Location","/Gestion_de_liste_de_cadeaux_API/api/notification/"+notificationId).header("idCreated", notificationId)
							.build();
				}
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}
			return Response.status(Status.UNAUTHORIZED).build();

		}
		
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response getAllNotification(@HeaderParam("key") String key) {
			if(key!=null) {
				if(key.equals(apiKey)) {
					ArrayList<Notification> notifications=Notification.getAll();
					if(notifications == null || notifications.isEmpty())
						return Response.status(Status.NOT_FOUND).build();
					
					return Response.status(Status.OK).entity(notifications).build();
				}
			}
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		@DELETE
		@Path("{id}")
		public Response deleteNotificationMessage(@PathParam("id") int id,@FormParam("userId") int userId) {
			if(id != 0 && userId !=0) {
				Notification notification = new Notification();
				notification.setNotificationId(id);
				if(notification.delete(userId)) {
					return Response.status(Status.NO_CONTENT).build();
				}
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}
			
			return Response.status(Status.BAD_REQUEST).build();
		}

}

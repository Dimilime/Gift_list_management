package be.project.api;

import java.util.ArrayList;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
		
		

}

package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import be.project.javabeans.Notification;
import be.project.javabeans.User;


public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = null;
		if(session != null )
			user = (User)session.getAttribute("connectedUser");
			//supprime l'attribut signifiant la création d'une nouvelle liste de cadeau
			if(session.getAttribute("newList")!= null)
				session.removeAttribute("newList");
		if(user != null) {
			user.findAllGiftList();
			ArrayList<Notification> notifs = user.findAllNotifications();
			if(notifs.size()>0) {
				session.setAttribute("notif", notifs.size());
			}
			session.setAttribute("connectedUser", user);
			request.getRequestDispatcher("/WEB-INF/JSP/home.jsp").forward(request, response);
		}else {
			response.sendRedirect("logout");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//gère la redirection après la création d'une liste
		if(request.getAttribute("giftForward") != null) {
			doGet(request, response);
			return;
		}
		
		HttpSession session = request.getSession(false);
		User user = null;
		int index = -1;
		if(session != null ) {
			user = (User)session.getAttribute("connectedUser");
			if(session.getAttribute("index") != null) 
				index = (int)session.getAttribute("index") ;
		}
		
		if(user !=null && index != -1 && user.getGiftLists().get(index)!=null) {
			try {
				
				if(user.shareList(index)) {
					session.removeAttribute("index");
					session.removeAttribute("giftList");
					request.setAttribute("message", "La liste a bien été partagé!");
				}
					
				else
					request.setAttribute("errorList", "La liste n'a été partagé!");
				request.getRequestDispatcher("/WEB-INF/JSP/home.jsp").forward(request, response);
				return;
			} catch (NumberFormatException e) {
				request.setAttribute("error", "Erreur dans Home "+e.getMessage());
				request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request, response);
			}
		}
			
	}

}

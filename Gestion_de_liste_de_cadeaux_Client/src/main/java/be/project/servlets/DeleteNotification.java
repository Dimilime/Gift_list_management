package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;


public class DeleteNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		User user = null;
		if(session != null )
			user = (User)session.getAttribute("connectedUser");
		if(request.getParameter("index") !=null) {
			try {
				int index = Integer.valueOf(request.getParameter("index"));
				user.findAllNotifications();
				if(user.deleteNotification(index)) {
					response.sendRedirect("notifications");
					return;
				}
				request.setAttribute("errorShareList", "La notification ne s'est pas correctement supprimé !");
				request.getRequestDispatcher("notifications").forward(request, response);
				return;
			}catch(NumberFormatException e) {
				request.setAttribute("error", "Erreur dans DeleteNotification "+e.getMessage());
				request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request, response);
			}
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

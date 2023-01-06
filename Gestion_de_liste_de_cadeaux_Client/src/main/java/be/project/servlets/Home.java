package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import be.project.javabeans.User;


public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = null;
		if(session != null )
			user = (User)session.getAttribute("connectedUser");
		if(user != null)
			request.getRequestDispatcher("/WEB-INF/JSP/home.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = null;
		int index = -1;
		if(session != null ) {
			user = (User)session.getAttribute("connectedUser");
			index = (int)session.getAttribute("index");
		}
		
		if(user !=null && user.getGiftLists().get(index)!=null) {
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

package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddNewGift
 */
public class AddNewGift extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/JSP/addNewGift.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String res = request.getParameter("response");
		if(res!=null) {
			
			if(res.equals("yes")) {
				request.setAttribute("message", "Cadeau ajouté!");
				request.getRequestDispatcher("addGift").forward(request, response);
			}
			else
				response.sendRedirect("home");
			
		}
		
	}

}

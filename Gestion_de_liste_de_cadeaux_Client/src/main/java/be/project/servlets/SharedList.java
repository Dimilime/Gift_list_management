package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.GiftList;
import be.project.javabeans.User;


public class SharedList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User user = null;
		if(session != null )
			user = (User)session.getAttribute("connectedUser");
		if(user != null) {
			int id =Integer.valueOf(request.getParameter("id"));
			GiftList giftList = GiftList.get(id);
			request.setAttribute("invitation", giftList);
			request.getRequestDispatcher("/WEB-INF/JSP/sharedList.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

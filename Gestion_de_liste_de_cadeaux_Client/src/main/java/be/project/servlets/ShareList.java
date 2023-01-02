package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.GiftList;
import be.project.javabeans.User;


public class ShareList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//récupérer l'id de la liste et la mettre en session
		//récupérer l'id de l'user qu'on veut ajouter à la liste partagé
		try 
		{
			HttpSession session = request.getSession(false);
			
			int listId = Integer.valueOf(request.getParameter("id"));
			GiftList giftList = GiftList.get(listId);
			
			if(session != null ) {
				session.setAttribute("giftList", giftList);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		System.out.println("doget shareList");

		request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		GiftList giftList = null;
		User owner = null;
		if(session != null ) {
			giftList = (GiftList) session.getAttribute("giftList");
			owner = (User) session.getAttribute("connectedUser");
		}
		System.out.println("shareList doPost");
		String userEmail = request.getParameter("userEmail");
		User user = User.getUserByEmail(userEmail);
		if(giftList != null) {
			giftList.setSharedUsers(giftList.getSharedUsers() == null 
			|| giftList.getSharedUsers().isEmpty() ? new ArrayList<>() : giftList.getSharedUsers() ); //set list if null or empty

			if(!giftList.getSharedUsers().contains(user)) {
				if(owner.shareList(giftList))
				{
					request.setAttribute("message", user.getFirstname()+" a été invité à participé à la liste !");
					request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
					return;
				}
				request.setAttribute("errorShareList", "Erreur la liste n'a pas été partagé!");
				request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
				return;
			}
			request.setAttribute("errorShareList", "Ce membre a déjà été ajouté!");
			request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
			return;
		}
		//doGet(request, response);
	}

}

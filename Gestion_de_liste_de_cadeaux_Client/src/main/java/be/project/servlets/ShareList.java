package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;


public class ShareList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		int sharedUsersNumber = 0;
		if(session != null ) {
			session.setAttribute("sharedUsersNumber", sharedUsersNumber);
			if(request.getParameter("index") != null){
				int index = Integer.valueOf(request.getParameter("index"));
				session.setAttribute("index", index);
			}
		}
		
		
		System.out.println("doget shareList");

		request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user= null;
		int index = -1;
		int sharedUsersNumber = 0;
		if(session != null ) {
			user = (User) session.getAttribute("connectedUser");
			index = (int)session.getAttribute("index");// get the index of the giftList
			sharedUsersNumber = (int) session.getAttribute("sharedUsersNumber");
		}
		String userEmail = request.getParameter("userEmail");
		User sharedUser = User.getUserByEmail(userEmail);
		sharedUser.setNotifications(new ArrayList<>());
		if(user.getGiftLists().get(index) != null) {

			if(!user.getGiftLists().get(index).getSharedUsers().contains(sharedUser)) {
				
				if(user.getGiftLists().get(index).addUserToSharedList(sharedUser))
				{
					sharedUsersNumber++;
					session.setAttribute("sharedUsersNumber", sharedUsersNumber);
					session.setAttribute("giftList", user.getGiftLists().get(index));
					request.setAttribute("message", sharedUser.getFirstname()+" a été ajouté à la liste de partage temporaire ! N'oublier pas de cliquez sur Valider le partager pour confirmer");
					request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
					return;
				}
				request.setAttribute("errorShareList", "Erreur le membre n'a pas été invité à participé!");
				request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
				return;
			}
			request.setAttribute("errorShareList", "Ce membre a déjà été ajouté!");
			request.getRequestDispatcher("/WEB-INF/JSP/shareList.jsp").forward(request, response);
			return;
		}
	}

}

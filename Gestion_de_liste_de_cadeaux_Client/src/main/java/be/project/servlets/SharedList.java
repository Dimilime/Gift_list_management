package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Gift;
import be.project.javabeans.GiftList;
import be.project.javabeans.User;


public class SharedList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key=null;
		ArrayList<Gift> gifts;
		GiftList giftList;
		HttpSession session = request.getSession(false);
		User user = null;
		if(session != null )
			user = (User)session.getAttribute("connectedUser");
		if(user != null) {
			//cas d'un lien de partage avec key
			if(request.getParameter("key") != null) {
				key = request.getParameter("key");
				giftList = GiftList.getByKey(key);
				//ajouter l'user dans la liste partagée
				if(giftList != null) {
					user.setNotifications(new ArrayList<>());
					giftList.addUserToSharedList(user);
					giftList.share();
					giftList.setGifts(giftList.getGifts());
				}else {
					request.setAttribute("expiredList", "Le lien de partage a expiré");
				}
			}else {
				int id =Integer.valueOf(request.getParameter("id"));
				giftList = GiftList.get(id);
				if(giftList!=null) {
					giftList.setGifts(giftList.getGifts());
				}
			}
			//liste désactivée ou plus existante
			if(giftList !=null && giftList.isEnabled() ) {
				request.setAttribute("GiftList", giftList);
			}else {
				request.setAttribute("GiftList", null);
				request.setAttribute("expiredList", "Cette liste n'est plus consultable, elle a été désactivée ou supprimée");
			}
			request.getRequestDispatcher("/WEB-INF/JSP/sharedList.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

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
import be.project.javabeans.Notification;
import be.project.javabeans.Participation;
import be.project.javabeans.User;


public class OfferGift extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OfferGift() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("id") != null) {
			int giftId = Integer.valueOf(request.getParameter("id")) ;
			Gift gift = Gift.get(giftId);
			request.setAttribute("gift",  gift);
			//offer gift totally
			if(request.getServletPath().substring(1).equals("offergift")){
				request.setAttribute("sharing", "no");
			}
			//share offer
			if(request.getServletPath().substring(1).equals("shareoffer")){
				request.setAttribute("sharing", "yes");
			}
			request.getRequestDispatcher("/WEB-INF/JSP/offerGift.jsp").forward(request, response);
			return;
		}
		
		
		response.sendRedirect("home");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		System.out.println("doPost");
		HttpSession session = request.getSession(false);
		User user=null ;
		double amount;
		boolean sharingOffer=false;
		try {
			if(session != null ) 
				user = (User)session.getAttribute("connectedUser");
			if(request.getParameter("giftId") != null) {
				int id =Integer.valueOf( request.getParameter("giftId"));
				Gift gift = Gift.get(id);
				ArrayList<Participation> participations= new ArrayList<Participation>();
				gift.setParticipations(participations);
				amount=gift.getAveragePrice();
				
				if(request.getParameter("pay")!=null) {
					//payement partager
					//check paye pas tous au final
					//envoyer notifs
					sharingOffer=true;
					amount = Double.valueOf( request.getParameter("pay"));
				}
				Participation participation = new Participation(0,user, amount,null);
				gift.addParticipation(participation);
				participation.setGift(gift);
				if(user.addNewParticipation(participation)){
					request.setAttribute("message", "Votre offre a été confirmée");
					if(sharingOffer) {
						//envoyer les notifs
						GiftList list = GiftList.get(gift.getGiftList().getListId());
						ArrayList<User> usersToNotified = list.getSharedUsers();
						Notification notification = new Notification(0, "Invitation à un paiement partagé",
								user.getLastname() + " a opté pour un paiement partagé, souhaitez-vous y participer ?" +
								"  <a class=\"btn btn-secondary\" href=\"./sharedList?id="+gift.getGiftList().getListId()+"\">Participer</a>",usersToNotified);
						notification.create();
					}
					request.getRequestDispatcher("invitations").forward(request, response);
					return;
				}
			}
		}catch(Exception e) {
			System.out.println("Exception dans doPost de offerGift Servlet : " + e.getMessage());
			response.sendRedirect("connexion");
			return;
		}
		
		response.sendRedirect("home");
		}

}

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
import be.project.utils.Utils;


public class EditGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public EditGift() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = null;
		GiftList currentGiftList=null;
		Gift currentGift = null;
		int giftId, listId;
		giftId=listId=0;
		boolean rightAccess= false;
		try {
			if(session != null )
				user = (User)session.getAttribute("connectedUser");

			ArrayList<GiftList> giftList = user.getGiftLists();
			if(request.getParameter("giftId") != null) {
				giftId = Integer.valueOf(request.getParameter("giftId"));
				listId = Integer.valueOf(request.getParameter("listId"));
				for(GiftList list : giftList) {
					//check si possède bien cette liste
					if(list.getListId() == listId) {
						currentGiftList= list;
						//check si cadeau fait bien parti de la liste
						for(Gift gift : currentGiftList.getGifts()) {
							if(gift.getGiftId() == giftId) {
								rightAccess = true;
								currentGift = gift;
								//ajout listid et giftId dans session -> suppression au niveau consultList
								session.setAttribute("listId", listId);
								session.setAttribute("giftId", giftId);
							}
						}
					}	
				}
				if(rightAccess) {
					request.setAttribute("gift", currentGift);
					request.getRequestDispatcher("/WEB-INF/JSP/editGift.jsp").forward(request, response);
					return;
				}
			}
			
		}catch(Exception ex) {
			System.out.println("Exception dans doGet de editList Servlet : " + ex.getMessage());
			response.sendRedirect("connexion");
			return;
		}
		
		response.sendRedirect("home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//gère les modifs
		int giftId, listId;
		giftId=listId=0;
		User connectedUser = null;
		HttpSession session = request.getSession(false);
		try {
			if(session != null ) {
				listId= (int)session.getAttribute("listId");
				giftId= (int)session.getAttribute("giftId");
				connectedUser = (User)session.getAttribute("connectedUser");
			}
			if(listId!=0 && giftId!=0) {
				if(request.getParameter("giftName")!=null && request.getParameter("description") !=null &&
						request.getParameter("averagePrice")!=null && request.getParameter("priorityLevel") !=null) {
					
					String giftname = request.getParameter("giftName");
					String description = request.getParameter("description");
					double price = Double.valueOf(request.getParameter("averagePrice")) ;
					int priorityLevel = Integer.valueOf(request.getParameter("priorityLevel"));
					//image
					
					String link = request.getParameter("link");
					//TODO
					//vérif des infos avec une méthode reprenant tous les params 
					
					GiftList giftList = new GiftList();
					giftList.setListId(listId);
					Gift gift = new Gift(giftId,priorityLevel,giftname,description,price,link,null,giftList);
					System.out.println("gift crée editGift servlet" + gift);
					if(gift.update()) {
						request.setAttribute("message", "Le cadeau a bien été modifiée");
						request.setAttribute("refreshList", "yes");
						request.getRequestDispatcher("consultList?id="+listId).forward(request, response);
						return;
					}
				}
			}
		}catch(Exception ex) {
			System.out.println("Exception dans EditGift doPost : " + ex.getMessage());
			response.sendRedirect("connexion");
			return;
		}
		response.sendRedirect("home");
	}

}

package be.project.servlets;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import be.project.javabeans.Gift;
import be.project.javabeans.GiftList;



public class AddGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddGift() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/JSP/addGift.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);	
		String img =null;
		try {
			if(request.getPart("giftImg") != null) {
				Part part = request.getPart("giftImg");
				InputStream inputStream = part.getInputStream();
				byte [] bytes = inputStream.readAllBytes();
				//convert inputstream to string
				img =  Base64.getEncoder().encodeToString(bytes);

			}
			
		} catch (Exception e) {
		}


		
		if(request.getAttribute("giftListForward") != null) {
			//attribut permettant de différencier l'ajout d'un cadeau lors de l'ajout d'une liste
			//ou sur le coup d'une modification
			session.setAttribute("newList", "true");
			doGet(request, response);
			return;
		}
		
		
		
		
		
		
		GiftList giftList = null;
		if(session != null ) {
			giftList = (GiftList)session.getAttribute("giftList");
			if(giftList == null) {
				request.setAttribute("errorAddGiftList", "Liste non connu, créez ou modifiez une nouvelle liste!");
				request.getRequestDispatcher("addGiftList").forward(request, response);
				return;
			}
		}
		String giftName = request.getParameter("giftName");
		String description = request.getParameter("description");
		String averagePrice = request.getParameter("averagePrice");
		String priorityLevel =  request.getParameter("priorityLevel");
		String giftImg = img;
		String link = request.getParameter("link");
		boolean addAnother = request.getParameter("addAnother") != null;

		if(giftName != null && averagePrice != null && priorityLevel != null
				&& giftName.trim().length() > 0 && averagePrice.trim().length() > 0 && priorityLevel.trim().length() > 0) {
			try {
					Double avgPrice = Double.valueOf(averagePrice);
					int priorityLvl = Integer.valueOf(priorityLevel);
					Gift gift = null;
					if(avgPrice >0 && priorityLvl > 0) {
						gift = new Gift(0, priorityLvl, giftName, description, avgPrice, link, giftImg, giftList);
						ArrayList<Gift> gifts = new ArrayList<>();
						if(giftList != null) {
							if( giftList.getGifts() == null || giftList.getGifts().isEmpty())
								giftList.setGifts(gifts);
						}
					}

					if(giftList.addGift(gift)) {		
						request.setAttribute("message","Cadeau ajouté!");
						if(addAnother) {
							doGet(request, response);
							return;
						}else if(session.getAttribute("newList") != null && session.getAttribute("newList").equals("true") ) {
							request.setAttribute("message","Liste créée avec succès");
							request.setAttribute("giftForward", "yes");
							request.getRequestDispatcher("home").forward(request, response);
							return;
						}else {
							request.setAttribute("message","Cadeau créée avec succès!");
							request.setAttribute("refreshList", "yes");
							request.getRequestDispatcher("consultList?id="+giftList.getListId()).forward(request, response);
							return;
						}
				}
				request.setAttribute("errorGift","Cadeau non ajouté!");
				doGet(request, response);
				return;
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}else {
			request.setAttribute("errorGift","Nom, prix ou priorité vide!");
			doGet(request, response);
		}
			
	}

}

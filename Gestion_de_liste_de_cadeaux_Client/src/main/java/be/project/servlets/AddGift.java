package be.project.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import be.project.javabeans.Gift;
import be.project.javabeans.GiftList;
import be.project.javabeans.User;


public class AddGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddGift() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet addGift");
		request.getRequestDispatcher("/WEB-INF/JSP/addGift.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		Part part = request.getPart("giftImg");//genere exception
//		InputStream is = part.getInputStream();
//		ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
//		try {
//			List<FileItem> fileItem = sf.parseRequest(request);
//		} catch (FileUploadException e) {
//			e.printStackTrace();
//		}file.getinputstreampour recupe limage
		
		if(request.getAttribute("giftListForward") != null) {
			doGet(request, response);
			return;
		}
			
		HttpSession session = request.getSession(false);
		
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
		String giftImg = request.getParameter("giftImg");
		String link = request.getParameter("link");
		boolean addAnother = request.getParameter("addAnother") != null;
//		System.out.println(giftName);
//		System.out.println(giftImg);
//		System.out.println(link);
		if(giftName != null && averagePrice != null && priorityLevel != null
				&& giftName.trim().length() > 0 && averagePrice.trim().length() > 0 && priorityLevel.trim().length() > 0) {
			try {
					Double avgPrice = Double.valueOf(averagePrice);
					int priorityLvl = Integer.valueOf(priorityLevel);
					Gift gift = null;
					if(avgPrice >0 && priorityLvl > 0) {
						gift = new Gift(0, priorityLvl, giftName, description, avgPrice, link, giftImg, giftList);
						ArrayList<Gift> gifts = new ArrayList<>();
						if( giftList.getGifts() == null || giftList.getGifts().isEmpty())
							giftList.setGifts(gifts);
					}
					if(giftList.addGift(gift)) {		
						//session.setAttribute("giftList", giftList);
						request.setAttribute("message","Cadeau ajouté!");
						if(addAnother) {
							doGet(request, response);
							return;
						}else {
							request.setAttribute("message","Liste créée avec succès");
							request.setAttribute("giftForward", "yes");
							request.getRequestDispatcher("home").forward(request, response);
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

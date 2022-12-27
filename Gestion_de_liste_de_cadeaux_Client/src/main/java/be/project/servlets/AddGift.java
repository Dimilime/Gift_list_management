package be.project.servlets;

import java.io.IOException;
import java.io.InputStream;
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
		request.getRequestDispatcher("/WEB-INF/JSP/addGift.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Part part = request.getPart("giftImg");
		InputStream is = part.getInputStream();
		ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
		try {
			List<FileItem> fileItem = sf.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession(false);
		
		GiftList giftList = null;
		User user = null;
		if(session != null ) {
			giftList = (GiftList)session.getAttribute("giftList");
			user = (User)session.getAttribute("connectedUser");
		}
		String giftName = request.getParameter("giftName");
		String description = request.getParameter("description");
		String averagePrice = request.getParameter("averagePrice");
		String priorityLevel =  request.getParameter("priorityLevel");
		String giftImg = request.getParameter("giftImg");
		String link = request.getParameter("link");
		System.out.println(giftName);
		System.out.println(giftImg);
		System.out.println(link);
		if(giftName != null ||averagePrice != null || priorityLevel != null ) {
			try {
				Double avgPrice = Double.valueOf(averagePrice);
				int priorityLvl = Integer.valueOf(priorityLevel);
				Gift gift = new Gift(0, priorityLvl, giftName, description, avgPrice, link, giftImg, giftList);
				giftList.addGift(gift);
				session.setAttribute("giftList", giftList);
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}else
			doGet(request, response);
	}

}

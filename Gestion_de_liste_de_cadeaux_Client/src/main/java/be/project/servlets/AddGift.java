package be.project.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


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
		
		String giftName = request.getParameter("giftName");
		String description = request.getParameter("description");
		String averagePrice = request.getParameter("averagePrice");
		String priorityLevel = request.getParameter("priorityLevel");
		var giftImg = request.getParameter("giftImg");
		String link = request.getParameter("link");
		System.out.println(giftName);
		System.out.println(giftImg);
		System.out.println(link);
		if(giftName != null ||averagePrice != null || priorityLevel != null ) {
			
		}else
			doGet(request, response);
	}

}

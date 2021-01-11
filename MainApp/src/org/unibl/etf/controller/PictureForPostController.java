package org.unibl.etf.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.unibl.etf.dao.ImageDAO;
import org.unibl.etf.dto.Image;

/**
 * Servlet implementation class PictureForPostController
 */
@WebServlet(
		urlPatterns = "/PictureForPostController",
		initParams = {@WebInitParam(name = "location", value = "C:\\Users\\djord\\OneDrive\\Desktop\\Projektni IP\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\MainApp\\picturesForPost")})
@MultipartConfig
public class PictureForPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PictureForPostController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				//doGet(request, response);					
					Part filePart = request.getPart("pictureFileForPost");
					String fileName = request.getParameter("filename");
					
					//System.out.println("FILE NAME: "+fileName);
					
					if(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg") || fileName.endsWith(".JPEG") || fileName.endsWith(".JPG") || fileName.endsWith(".PNG")) {
					InputStream inputStream = filePart.getInputStream();
					ServletConfig servletConfig=getServletConfig();
					//String videoPath = VideoController.class.getResource("videos").toString();
			    	String picturePath=servletConfig.getInitParameter("location");
			    	
			    	String peroSimoString = new Date().getTime()+"_"+fileName;
			    	File file = new File(picturePath,peroSimoString);
			    	Files.copy(inputStream, file.toPath(),StandardCopyOption.REPLACE_EXISTING);
			    	URL url=new URL("http://localhost:8080/MainApp/picturesForPost/" +peroSimoString );
					String content=url.toString();

					String active = request.getParameter("active");
					String postId = request.getParameter("postId");
			        		       
			        Image image = new Image();
			        image.setPostId(Integer.parseInt(postId));
			        image.setActive(Boolean.parseBoolean(active));
			        image.setContent(content);
					
			        int id = ImageDAO.insertImageInPost(image);
					}
	}

}

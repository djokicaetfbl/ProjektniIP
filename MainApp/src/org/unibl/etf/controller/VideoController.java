package org.unibl.etf.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
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

import org.unibl.etf.dao.PostDAO;
import org.unibl.etf.dto.Post;

/**
 * Servlet implementation class VideoController
 */
@WebServlet(
		urlPatterns = "/VideoController",
		initParams = {@WebInitParam(name = "location", value = "C:\\Users\\djord\\OneDrive\\Desktop\\Projektni IP\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\MainApp\\videos")})
@MultipartConfig
public class VideoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoController() {
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
		Part filePart = request.getPart("videoFile");
		String fileName = request.getParameter("filename");
		
		if(fileName.endsWith(".mp4") || fileName.endsWith(".ogg")) {
		InputStream inputStream = filePart.getInputStream();
		ServletConfig servletConfig=getServletConfig();
		
    	String videoPath=servletConfig.getInitParameter("location");
    	
    	String peroSimoString = new Date().getTime()+"_"+fileName;
    	File file = new File(videoPath,peroSimoString);
    	Files.copy(inputStream, file.toPath(),StandardCopyOption.REPLACE_EXISTING);
    	URL url=new URL("http://localhost:8080/MainApp/videos/" +peroSimoString );
		String content=url.toString();

		String postGroupId = request.getParameter("postGroupIdPostGroup");
		String currentUserId = request.getParameter("userId");
        String shareTime = request.getParameter("shareTime");
        String geographicLatitude = request.getParameter("geographicLatitude");
        String geographicLongitude = request.getParameter("geographicLongitude");
        String emergencyNotification = request.getParameter("emergencyNotification");
        
       
        Post post = new Post();
        post.setPostGroupIdPostGroup(Integer.parseInt(postGroupId));
        post.setUserId(Integer.parseInt(currentUserId));
        post.setShareTime(new Date(shareTime));
        post.setGeographicLatitude(Float.parseFloat(geographicLatitude));
        post.setGeographicLongitude(Float.parseFloat(geographicLongitude));
        post.setEmergencyNotification(Boolean.parseBoolean(emergencyNotification));
        post.setContents(content);
        post.setActive(true);
		
        int id=PostDAO.insertPost(post);
		}
	}

}

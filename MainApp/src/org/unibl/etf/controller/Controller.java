package org.unibl.etf.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.sql.Timestamp;
import java.util.Date;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.commons.codec.binary.Hex;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.unibl.etf.beans.UserBean;
import org.unibl.etf.dao.TokenDAO;
import org.unibl.etf.dao.TokenUtil;
import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.dto.Token;
import org.unibl.etf.dto.User;

/**
 * Servlet implementation class Controller
 */
@WebServlet(
		urlPatterns = "/Controller",
		initParams = {@WebInitParam(name = "location", value = "C:\\Users\\djord\\OneDrive\\Desktop\\Projektni IP\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\MainApp\\images")})
@MultipartConfig

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILEPATH = "images/";
	public static boolean flagLogin = false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String address = "/WEB-INF/pages/login.jsp";
		String action = request.getParameter("action");
		HttpSession session = request.getSession();	
		
		if(action == null || action.equals("")) {
			Token token = null;
			Cookie[] cookies = request.getCookies();
			String tokenReq = null;
			int userId = 0;
			
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals("token")) {
						tokenReq = cookie.getValue();
					}
				}
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals("userId")) {
						userId = Integer.parseInt(cookie.getValue());
					}
				}
				
				if (TokenDAO.getUsersToken(userId).size() > 0) {
				token = TokenDAO.getUsersToken(userId).get(0);
				}
			} 
			
			if(token != null && tokenReq != null) {
				if(token.getToken().equals(tokenReq)) {
				if(TokenUtil.isValid(token)) {
					UserBean userBean = new UserBean();
					User user = UserDAO.getUserById(userId);
					userBean.setUser(user);
					session.setAttribute("user", userBean);
					address = "/WEB-INF/pages/index.jsp";
				} else {
					TokenDAO.reset(userId);
					address = "/WEB-INF/pages/login.jsp";
				}
				}
			} else {		
			address = "/WEB-INF/pages/login.jsp";
			session.setAttribute("notification", "");
			}
		} else if (action.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserBean userBean = new UserBean();
			
			if(username != null && password != null) {
			if(userBean.login(username,password)) {
				UserDAO.setUserLoginCounter(userBean.getUser().getId());
				flagLogin = true;
				TokenDAO.reset(userBean.getUser().getId());
				String generateToken = TokenUtil.generateToken(8, true, true);
				Token token = new Token();
				long currentTime = new Date().getTime();
				long validate = new Date(currentTime + 3_600_000).getTime();
				token.setToken(generateToken);
				token.setCreationTime(new Timestamp(currentTime));
				token.setExpirationTime(new Timestamp(validate));
				token.setActive(true);
				token.setUserId(userBean.getUser().getId());
				TokenDAO.insert(token);
				
				Cookie tokenCookie = new Cookie("token", generateToken);
				Cookie usernameCookie = new Cookie("username", username);
				Cookie userIdCookie = new Cookie("userId", userBean.getUser().getId().toString());
				tokenCookie.setMaxAge(4200);
				usernameCookie.setMaxAge(4200);
				userIdCookie.setMaxAge(4200);
				
				response.addCookie(tokenCookie);
				response.addCookie(usernameCookie);
				response.addCookie(userIdCookie);
				
				session.setAttribute("notification", "");
				session.setAttribute("userBean", userBean);
				address = "/WEB-INF/pages/index.jsp";
			} else {
				flagLogin = false;
				session.setAttribute("notification", "Wrong username or password.");
			}
		} else {
			address = "/WEB-INF/pages/login.jsp";
		}
		} else if(action.equals("signUp")) {
			address = "/WEB-INF/pages/signUp.jsp";			
			
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String firstPassword = request.getParameter("firstPassword");
			String secondPassword = request.getParameter("secondPassword");
						
			UserBean userBean = new UserBean();
			
			try {
			if(firstName != null && lastName != null && email != null && username != null &&  firstPassword != null && secondPassword != null) {
				if(!userBean.isUserNameUsed(username)) {
					if(!userBean.isEmalUsed(email) && userBean.checkEmailNotation(email)) {
						if(firstPassword.equals(secondPassword)) {
							
							User user = new User();
							user.setFirstName(firstName);
							user.setLastName(lastName);
							user.setEmail(email);
							user.setUsername(username);
							user.setUserGroupId(2);
							user.setApproved(false);  // neka je za sada true
							user.setActive(true);
							user.setBlocked(false);
							user.setPassword(hash(firstPassword));
							user.setPicture("");
							user.setCity("");
							user.setCountry("");
							user.setRegion("");
							user.setSignInCounter(0);
							user.setEmergencyNotification(false);
							if(userBean.add(user)) {
								session.setAttribute("userBean", userBean);
								session.setAttribute("notification", "");
								address = "/WEB-INF/pages/updateProfil.jsp";
							}
						} else {
							session.setAttribute("notification", "Email exists.");
							address = "/WEB-INF/pages/signUp.jsp";
						}
					} else {
						session.setAttribute("notification", "Email exists.");
						address = "/WEB-INF/pages/signUp.jsp";
					}
				} else {
					session.setAttribute("notification", "Username exists.");
					address = "/WEB-INF/pages/signUp.jsp";
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		} else if(action.equals("updateProfil")) {				
				UserBean userBean = (UserBean) session.getAttribute("userBean");
				Boolean emergencyNotificationCheckBox = true;
				
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String email = request.getParameter("email");
				String picture = "";
				String emergencyNotification = request.getParameter("emergencyNotification");
				String country = request.getParameter("country");
				String region = request.getParameter("region");
				String city = request.getParameter("city");
				if(!request.getParameter("emergencyNotification").equals("on")) {
					emergencyNotificationCheckBox = false;
				}

				Part filePart = request.getPart("pictureFile");

				InputStream fileContent = filePart.getInputStream(); 

				String fileName = getFilename(filePart);
				
				userBean.getUser().setFirstName(request.getParameter("firstName"));
				userBean.getUser().setLastName(request.getParameter("lastName"));
				userBean.getUser().setEmail(request.getParameter("email"));
				userBean.getUser().setPicture("");
				userBean.getUser().setEmergencyNotification(emergencyNotificationCheckBox);
				userBean.getUser().setCountry(request.getParameter("country"));
				userBean.getUser().setRegion(request.getParameter("region"));
				userBean.getUser().setCity(request.getParameter("city"));
								
				picture = uploadFileToProject(fileName,fileContent,userBean,request);
				userBean.getUser().setPicture(picture);
				
				if(userBean.updateUserProfile(userBean.getUser())) {
					address = "/WEB-INF/pages/login.jsp";
					session.setAttribute("notification", "Successfully set up profile");
				} else {
					address = "/WEB-INF/pages/updateProfil.jsp";
					session.setAttribute("notification", "Successfully set up profile");
				}

		} else if ("logout".equals(action)) {
			Cookie[] cookies = request.getCookies();
			Token token = null;
			String tokenReq = null;
			int userId = 0;
						
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals("token")) {
						tokenReq = cookie.getValue();
					} 
					if(cookie.getName().equals("userId")) {
						userId = Integer.parseInt(cookie.getValue());
					}
				}	
				if (TokenDAO.getUsersToken(userId).size() > 0)
				token = TokenDAO.getUsersToken(userId).get(0);
			}
			
			TokenDAO.reset(userId);

			deleteCookie(response);
			session.invalidate();
			address = "/WEB-INF/pages/login.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String uploadFileToProject(String fileName, InputStream fileContent,UserBean userBean,HttpServletRequest request) {
		String picture = "";
		if(fileName != null && !fileName.isEmpty()) {
			String fileExtension = "";
			if(fileName.lastIndexOf('.') > 0) {
				fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
			} else {
				fileExtension = "";
			}
			
			try {
				ServletConfig servletConfig = getServletConfig();
				String projectImagesPath = servletConfig.getInitParameter("location");
				File file = new File(projectImagesPath,userBean.getUser().getUsername() + "." + fileExtension.toLowerCase());
				Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

				picture = FILEPATH + userBean.getUser().getUsername() + "." + fileExtension.toLowerCase();// hesirati :D
			}catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			picture = request.getParameter("countryFlag");
		}
		return picture;
	}
	
	private void deleteCookie(HttpServletResponse response) {
		Cookie tokenCookie = new Cookie("token", "");
		Cookie usernameCookie = new Cookie("username", "");
		Cookie userIdCookie = new Cookie("userId", "");
		
		tokenCookie.setMaxAge(0);
		usernameCookie.setMaxAge(0);
		userIdCookie.setMaxAge(0);
		response.addCookie(tokenCookie);
		response.addCookie(usernameCookie);
		response.addCookie(userIdCookie);
	}
	
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}
	
	public String hash(String tekst) throws NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest mda;
		mda = MessageDigest.getInstance("SHA-512", "BC");
		byte[] digest = mda.digest(tekst.trim().getBytes());
		return Hex.encodeHexString(digest);
	}

}

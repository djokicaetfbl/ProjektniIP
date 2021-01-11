package org.unibl.etf.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.dto.User;

@Path("user")
public class UserService {

	@GET
	@Path("/{currentUserId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("currentUserId") int currentUserId) {
		User user = UserDAO.getUserById(currentUserId);
		if(user != null) {
			return Response.status(200).entity(user).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAll(){
		return UserDAO.getAllUsers();
	}
}

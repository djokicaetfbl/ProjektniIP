package org.unibl.etf.service;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.dao.EmergencyCallDAO;
import org.unibl.etf.dto.EmergencyCall;

@Path("emergencyCall")
public class EmergencyCallService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EmergencyCall> getAll(){
		return EmergencyCallDAO.getAllEmergencyCallPosts();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEmergencyCall(@PathParam("id") int id) {
		boolean success = EmergencyCallDAO.deleteEmergencyCall(id);
		if(success)
			return Response.status(200).entity(success).build();
		else
			return Response.status(404).build();
	}
	
	@POST
	@Path("/blockEmergencyCall/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response blockEmergencyCall(@PathParam("id") int id) {
		boolean result = EmergencyCallDAO.blockEmergencyCall(id);
		if(result) {
			return Response.status(200).entity(result).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	@POST
	@Path("/reportFakekEmergencyCall/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reportFakekEmergencyCall(@PathParam("id") int id) {
		boolean result = EmergencyCallDAO.reportFakekEmergencyCall(id);
		if(result) {
			return Response.status(200).entity(result).build();
		} else {
			return Response.status(404).build();
		}
	}
}

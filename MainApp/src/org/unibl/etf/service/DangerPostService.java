package org.unibl.etf.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.dao.DangerPostGroupDAO;
import org.unibl.etf.dao.PostHasDangerPostGroupDAO;

@Path("/dangerPost")
public class DangerPostService {
	
	@GET
	@Path("/{getPostGroupName}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPostGroupName(@PathParam("getPostGroupName") int getPostGroupName) {
		return DangerPostGroupDAO.getPostGroupName(getPostGroupName);
	}	
}

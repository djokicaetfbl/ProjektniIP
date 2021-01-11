package org.unibl.etf.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.unibl.etf.rss.FeedMessage;
import org.unibl.etf.rss.RSSReader;

@Path("/rss")
public class RssService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FeedMessage> getRSSFeed(){
		return (ArrayList<FeedMessage>) RSSReader.readRSS();
	}
}

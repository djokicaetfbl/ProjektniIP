package org.unibl.etf.rssService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unibl.etf.dao.EmergencyCallCategoryDAO;
import org.unibl.etf.dao.EmergencyCallDAO;
import org.unibl.etf.dto.EmergencyCall;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndCategoryImpl;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.SyndFeedOutput;

/**
 * Servlet implementation class EmergencyCallRSSServlet
 */
@WebServlet("/EmergencyCallRSSServlet")
public class EmergencyCallRSSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmergencyCallRSSServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("Events");
		feed.setDescription("Assistance system");
		feed.setLink("https://etf.unibl.org");
		
		List<SyndEntry> entries = new ArrayList<>();
		ArrayList<EmergencyCall> emergencyCallArrayList = EmergencyCallDAO.getAllEmergencyCallPosts();
		
		for(EmergencyCall emergencyCall : emergencyCallArrayList) {
			SyndEntry item = new SyndEntryImpl();
			item.setTitle(emergencyCall.getName());
			SyndContent description = new SyndContentImpl();
			
			description.setType("text/html");
			StringBuilder descriptionBuilder = new StringBuilder();
			descriptionBuilder.append("Event time:  ");
			descriptionBuilder.append(new SimpleDateFormat("dd.MM.yyyy.").format(emergencyCall.getTime()));
			descriptionBuilder.append(" ");
			descriptionBuilder.append(emergencyCall.getDescription());
			description.setValue(descriptionBuilder.toString());
			
			item.setDescription(description);
			
			List<SyndCategory> categories = new ArrayList<>();
			SyndCategory category = new SyndCategoryImpl();
			category.setName(EmergencyCallCategoryDAO.getCategoryName(emergencyCall.getEmergencyCategoryId()));
			categories.add(category);
			
			item.setCategories(categories);
			item.setLink(emergencyCall.getPircutreURL());
			item.setAuthor("ETFBL");
			item.setPublishedDate(emergencyCall.getShareTime());
			entries.add(item);
		}		
		feed.setEntries(entries);
		try {
			response.getWriter().println(new SyndFeedOutput().outputString(feed));
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(500);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

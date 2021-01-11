package org.unibl.etf.rss;

import java.util.List;

public class RSSReader {

	public static List<FeedMessage> readRSS() {
		RSSFeedParser parser = new RSSFeedParser("https://europa.eu/newsroom/calendar.xml_en?field_nr_events_by_topic_tid=151");
		Feed feed = parser.readFeed();
		//feed.getMessages().forEach(System.out::println);
		return feed.getMessages();

	}
}

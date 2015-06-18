package info.line.livedoor;

import java.io.Serializable;

/**
 * A representation of an rss item from the list.
 * 
 * @author Veaceslav Grec
 * 
 */
public class RssItem implements Serializable {

	private String title;
	private String link;
    private String pubDate;
    private String description;
    private static final long serialVersionUID = 1L;

    public RssItem(){}

	public RssItem(String title, String link, String pubDate, String description) {
		this.title = title;
		this.link = link;
        this.pubDate = pubDate;
        this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }
}

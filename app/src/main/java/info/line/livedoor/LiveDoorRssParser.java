package info.line.livedoor;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LiveDoorRssParser {

	// We don't use namespaces
	private final String ns = null;

	public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(inputStream, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			inputStream.close();
		}
	}

	private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "rss");
		String title = null;
		String link = null;
        String pubDate = null;
        String description = null;
		List<RssItem> items = new ArrayList<RssItem>();
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				title = readTitle(parser);
			} else if (name.equals("link")) {
				link = readLink(parser);
			} else if (name.equals("pubDate")) {
                pubDate = readPubDate(parser);
            }else if (name.equals("description")) {
                description = readDescription(parser);
            }

			if (title != null && link != null && pubDate != null && description != null) {
				RssItem item = new RssItem(title, link ,pubDate, description);
				items.add(item);
				title = null;
				link = null;
                pubDate = null;
			}
		}
		return items;
	}

	private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "link");
		String link = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "link");
		return link;
	}

	private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return title;
	}

    private String readPubDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return title;
    }

	// For the tags title and link, extract their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
}

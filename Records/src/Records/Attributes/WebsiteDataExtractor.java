package Records.Attributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// TODO: Auto-generated Javadoc
/**
 * The Class WebsiteDataExtractor.
 *
 * @author Alexander
 */
class WebsiteDataExtractor {

	/**
	 * Extract web data to array.
	 *
	 * @param site
	 *            the site
	 * @param docSelect
	 *            the doc select
	 * @param docAttribute
	 *            the doc attribute
	 * @param numOfSongs
	 *            the num of songs
	 * @param token
	 *            the token
	 * @param location
	 *            the location
	 * @return the string[][]
	 */
	static String[][] extractWebDataToArray(String site, String docSelect,
			String docAttribute, int numOfSongs, String token, String location) {
		System.out.println("WebsiteDataExtractor.webExtractToArray(" + site
				+ ", " + docSelect + ", " + docAttribute + ", " + numOfSongs
				+ ", " + token + ", " + location + ")");

		int counter = 0;
		Document doc = getJSONDoc(site, docSelect, docAttribute, numOfSongs, token, location);
		if (doc != null) {
			Elements metalinks = doc.select(docSelect);
			TopRss = new String[numOfSongs][2];
			for (Element link : metalinks) {
				String musicLink = link.attr(docAttribute);
				if ("Beatport".equals(location)) {
					musicLink = parseBeatportJson(musicLink);
				}
				StringTokenizer stringtokenizer = new StringTokenizer(musicLink, token);
				if (stringtokenizer.hasMoreTokens()) {
						TopRss[counter][0] = stringtokenizer.nextToken();
						TopRss[counter][1] = stringtokenizer.nextToken();
				}
				counter++;
			}
		}
		return TopRss;
	}

  /**
   * @param site
   * @param docSelect
   * @param docAttribute
   * @param numOfSongs
   * @param token
   * @param location
   * @return
   */
  private static Document getJSONDoc(String site, String docSelect, String docAttribute,
      int numOfSongs, String token, String location) {
    Document doc = null;
		try {
			doc = Jsoup.connect(site).get();
		} catch (SocketTimeoutException ex) {
			extractWebDataToArray(site, docSelect, docAttribute, numOfSongs,
					token, location);
		} catch (IOException ex) {
			Logger.getLogger(WebsiteDataExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
    return doc;
  }

	/**
	 * Parses the beatport json.
	 *
	 * @param jsonInput
	 *            the json input
	 * @return the string
	 */
	private static String parseBeatportJson(String jsonInput) {
		System.out.println("WebsiteDataExtractor.parseBeatportJson("
				+ jsonInput + ")");

		String title = null;
		String artist = null;
		try {
			JSONObject outerObject = new JSONObject(jsonInput);
			title = outerObject.getString("name");
			JSONArray jsonArray = outerObject.getJSONArray("artists");
			JSONObject objectInArray = jsonArray.getJSONObject(0);
			artist = objectInArray.getString("name");
		} catch (JSONException ex) {
			Logger.getLogger(WebsiteDataExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return title + " - " + artist;
	}

	/** The Top rss. */
	private static String[][] TopRss;
}

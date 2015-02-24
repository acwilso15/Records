/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// TODO: Auto-generated Javadoc
/**
 * The Class BeatportInfoRetriever.
 *
 * @author Alexander
 */
class BeatportInfoRetriever {

	/**
	 * Gets the album.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the album
	 */
	private static String getAlbum(JSONObject objectInArray) {
		String album;
		try {
			album = AttributeCleaner.clean(objectInArray.getString("mixName"));
		} catch (JSONException ex) {
			album = "";
		}
		System.out.println("getAlbum() - Returns - " + album);
		return album;
	}

	/**
	 * Gets the bpm.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the bpm
	 */
	private static String getBPM(JSONObject objectInArray) {
		String bpm = objectInArray.optString("bpm");
		if (bpm.equals("")) {
			bpm = "000";
		}
		System.out.println("getBPM() - Returns - " + bpm);
		return bpm;
	}

	/**
	 * Gets the genre.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the genre
	 */
	private static String getGenre(JSONObject objectInArray) {
		String genre;
		try {
			JSONArray genreArray = objectInArray.getJSONArray("genres");
			objectInArray = genreArray.getJSONObject(0);
			genre = objectInArray.getString("name");
		} catch (JSONException ex) {
			genre = "";
		}
		System.out.println("getGenre() - Returns - " + genre);
		return genre;
	}

	/**
	 * Gets the info.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @return the info
	 */
	static ArrayList<String> getInfo(String title, String artist) {
		System.out.println("BeatportInfoRetriever.getInfo(" + title + ", "
				+ artist + ")");
		title = AttributeCleaner.cleanAttribute("Title", title).replaceAll(" ",
				"+");
		artist = AttributeCleaner.cleanAttribute("Artist", artist).replaceAll(
				" ", "+");
		startConnection(title, artist);

		return beatportValueSet;
	}

	/**
	 * Gets the key.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the key
	 */
	private static String getKey(JSONObject objectInArray) {
		String key;
		try {
			JSONObject keyObject = objectInArray.getJSONObject("key");
			key = keyObject.optString("shortName").replace("min", "")
					.replace("maj", "").replace("&#9839", "");
		} catch (JSONException ex) {
			key = "";
		}
		System.out.println("getKey() - Returns - " + key);
		return key;
	}

	/**
	 * Gets the purchase link.
	 *
	 * @param site
	 *            the site
	 * @return the purchase link
	 */
	private static String getPurchaseLink(String site) {
		String link = site;
		System.out.println("getPurchaseLink() - Returns - " + link);
		return link;
	}

	/**
	 * Gets the track url.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the track url
	 */
	private static String getTrackURL(JSONObject objectInArray) {
		String TrackURL;
		try {
			TrackURL = objectInArray.getString("sampleUrl");
		} catch (JSONException ex) {
			TrackURL = "";
		}
		System.out.println("getTrackURL() - Returns - " + TrackURL);
		return TrackURL;
	}

	/**
	 * Start connection.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 */
	private static void startConnection(String title, String artist) {
		System.out.println("BeatportInfoRetriever.startConnection(" + title
				+ ", " + artist + ")");
		beatportValueSet = new ArrayList<String>();
		String site = "http://classic.beatport.com/search?query=" + title + "+"
				+ artist + "&facets[0]=fieldType%3Atrack";
		System.out.println(site);
		try {
			Document doc = Jsoup.connect(site).get();
			Elements link = doc
					.select("span[class = play-queue play-queue-med-small]");
			String musicLink = link.attr("data-json");
			JSONObject objectInArray = null;
			objectInArray = new JSONObject(musicLink);
			beatportValueSet.add(getTrackURL(objectInArray));
			beatportValueSet.add(getPurchaseLink(site));
			beatportValueSet.add(getAlbum(objectInArray));
			beatportValueSet.add(getGenre(objectInArray));
			// valSet.add(getTitle(objectInArray));
			// valSet.add(getArtist(objectInArray));
			beatportValueSet.add(getBPM(objectInArray));
			beatportValueSet.add(getKey(objectInArray));
		} catch (IOException | JSONException ex) {
			System.err.println("Error Connecting to Beatport");
			beatportValueSet.add("");
			beatportValueSet.add("");
			beatportValueSet.add("");
			beatportValueSet.add("");
			// valSet.add(title);
			// valSet.add(artist);
			beatportValueSet.add("");
			beatportValueSet.add("");
		}
	}

	/*
	 * public static String getTitle(JSONObject objectInArray) { String title;
	 * try { title = AttributeCleaner.cleanAttribute("Title",
	 * objectInArray.getString("name")); } catch (JSONException ex) { title =
	 * ""; } return title; }
	 * 
	 * public static String getArtist(JSONObject objectInArray) { String artist;
	 * try { JSONArray artistArray = objectInArray.getJSONArray("artists");
	 * objectInArray = artistArray.getJSONObject(0); artist =
	 * AttributeCleaner.cleanAttribute("Artist",
	 * objectInArray.getString("name")); } catch (JSONException ex) { artist =
	 * ""; } return artist; }
	 */

	/** The beatport value set. */
	private static ArrayList<String> beatportValueSet;
}

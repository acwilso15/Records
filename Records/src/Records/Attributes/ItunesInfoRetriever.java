package Records.Attributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ItunesInfoRetriever.
 *
 * @author Alexander
 */
class ItunesInfoRetriever {

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
			album = AttributeCleaner.clean(objectInArray
					.getString("collectionName"));
		} catch (JSONException ex) {
			album = "";
		}
		System.out.println("getAlbum() - Returns - " + album);
		return album;
	}

	/**
	 * Gets the artist.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the artist
	 */
	private static String getArtist(JSONObject objectInArray) {
		String artist;
		try {
			artist = AttributeCleaner.cleanAttribute("Artist",
					objectInArray.getString("artistName"));
		} catch (JSONException ex) {
			artist = "";
		}
		System.out.println("getArtist() - Returns - " + artist);
		return artist;
	}

	/**
	 * Gets the connection.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @return the connection
	 */
	private static String getConnection(String title, String artist) {
		System.out.println("GetItunesInfo.startConnection(" + title + ", "
				+ artist + ")");
		itunesValueSet = new ArrayList<String>();
		URL url;
		String path = "";
		try {
			url = new URL("https://itunes.apple.com/search?term="
					+ title.replaceAll(" ", "+").replaceAll("/", "-") + "%20"
					+ artist.replaceAll(" ", "+").replaceAll("/", "-")
					+ "&limit=1");
			System.out.println(url.toString());
			URLConnection connection = url.openConnection();
			connection.connect();
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				path = path + line;
			}
		} catch (IOException ex) {
			Logger.getLogger(ItunesInfoRetriever.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return path;
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
			genre = AttributeCleaner.clean(objectInArray
					.getString("primaryGenreName"));
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
		System.out.println("GetItunesInfo.getInfo(" + title + ", " + artist
				+ ")");
		getJsonInfo(title, artist);
		return itunesValueSet;
	}

	/**
	 * Gets the json info.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @return the json info
	 */
	private static void getJsonInfo(String title, String artist) {
		System.out.println("GetItunesInfo.startConnection(" + title + ", "
				+ artist + ")");
		itunesValueSet = new ArrayList<String>();
		String path = getConnection(title, artist);
		JSONObject outerObject;
		JSONObject objectInArray;

		try {
			outerObject = new JSONObject(path);
			JSONArray jsonArray = outerObject.getJSONArray("results");
			objectInArray = jsonArray.getJSONObject(0);

			itunesValueSet.add(getTrackURL(objectInArray));
			itunesValueSet.add(getPurchaseLink(objectInArray));
			itunesValueSet.add(getAlbum(objectInArray));
			itunesValueSet.add(getGenre(objectInArray));
			itunesValueSet.add(getTitle(objectInArray));
			itunesValueSet.add(getArtist(objectInArray));
		} catch (JSONException ex) {
			System.err.println("Error Connecting to Itunes");
			itunesValueSet.add("");
			itunesValueSet.add("");
			itunesValueSet.add("");
			itunesValueSet.add("");
			itunesValueSet.add(title);
			itunesValueSet.add(artist);
		}
	}

	/**
	 * Gets the purchase link.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the purchase link
	 */
	private static String getPurchaseLink(JSONObject objectInArray) {
		String link;
		try {
			link = objectInArray.getString("trackViewUrl");
		} catch (JSONException ex) {
			link = "";
		}
		System.out.println("getPurchaseLink() - Returns - " + link);
		return link;
	}

	/**
	 * Gets the title.
	 *
	 * @param objectInArray
	 *            the object in array
	 * @return the title
	 */
	private static String getTitle(JSONObject objectInArray) {
		String title;
		try {
			title = AttributeCleaner.cleanAttribute("Title",
					objectInArray.getString("trackName"));
		} catch (JSONException ex) {
			title = "";
		}
		System.out.println("getTitle() - Returns - " + title);
		return title;
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
			TrackURL = objectInArray.getString("previewUrl");
		} catch (JSONException ex) {
			TrackURL = "";
		}
		System.out.println("getTrackURL() - Returns - " + TrackURL);
		return TrackURL;
	}

	/** The itunes value set. */
	private static ArrayList<String> itunesValueSet;
}

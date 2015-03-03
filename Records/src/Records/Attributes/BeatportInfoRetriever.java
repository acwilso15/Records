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
public class BeatportInfoRetriever {

  /**
   * Gets the info.
   *
   * @param title
   *          the title
   * @param artist
   *          the artist
   * @return the info
   */
  public static ArrayList<String> getBeatportInfo(String title, String artist) {
    System.out.println("BeatportInfoRetriever.getInfo(" + title + ", " + artist + ")");
    title = AttributeCleaner.cleanAttribute("Title", title).replaceAll(" ", "+");
    artist = AttributeCleaner.cleanAttribute("Artist", artist).replaceAll(" ", "+");
    String site = "http://classic.beatport.com/search?query=" + title + "+" + artist
        + "&facets[0]=fieldType%3Atrack";
    return getValueSet(getBeatportObject(site), site);
  }

  /**
   * Start connection.
   *
   * @param title
   *          the title
   * @param artist
   *          the artist
   */
  private static JSONObject getBeatportObject(String site) {
    System.out.println("BeatportInfoRetriever.startConnection(" + site + ")");
    JSONObject objectInArray;
    try {
      Document doc = Jsoup.connect(site).get();
      Elements link = doc.select("span[class = play-queue play-queue-med-small]");
      String musicLink = link.attr("data-json");
      objectInArray = new JSONObject(musicLink);
    } catch (IOException | JSONException ex) {
      System.err.println("Error Connecting to Beatport");
      objectInArray = null;
    }
    return objectInArray;
  }

  public static ArrayList<String> getValueSet(JSONObject beatportObject, String site) {
    System.out.println("BeatportInfoRetriever.getValueSet(" + beatportObject + ", " + site + ")");
    ArrayList<String> beatportValueSet = new ArrayList<String>();
    if (beatportObject != null) {
      beatportValueSet.add(getTrackURL(beatportObject));
      beatportValueSet.add(getPurchaseLink(beatportObject, site));
      beatportValueSet.add(getAlbum(beatportObject));
      beatportValueSet.add(getGenre(beatportObject));
      beatportValueSet.add(getBPM(beatportObject));
      beatportValueSet.add(getKey(beatportObject));
    }
    else{
      beatportValueSet.add("");
      beatportValueSet.add("");
      beatportValueSet.add("");
      beatportValueSet.add("");
      beatportValueSet.add("");
      beatportValueSet.add("");
    }
    return beatportValueSet;
  }

  /**
   * Gets the album.
   *
   * @param beatportObject
   *          the object in array
   * @return the album
   */
  private static String getAlbum(JSONObject beatportObject) {
    String album;
    try {
      album = AttributeCleaner.clean(beatportObject.getString("mixName"));
    } catch (JSONException ex) {
      album = "";
    }
    System.out.println("getAlbum() - Returns - " + album);
    return album;
  }

  /**
   * Gets the bpm.
   *
   * @param beatportObject
   *          the object in array
   * @return the bpm
   */
  private static String getBPM(JSONObject beatportObject) {
    String bpm = beatportObject.optString("bpm");
    if (bpm.equals("")) {
      bpm = "000";
    }
    System.out.println("getBPM() - Returns - " + bpm);
    return bpm;
  }

  /**
   * Gets the genre.
   *
   * @param beatportObject
   *          the object in array
   * @return the genre
   */
  private static String getGenre(JSONObject beatportObject) {
    String genre;
    try {
      JSONArray genreArray = beatportObject.getJSONArray("genres");
      beatportObject = genreArray.getJSONObject(0);
      genre = beatportObject.getString("name");
    } catch (JSONException ex) {
      genre = "";
    }
    System.out.println("getGenre() - Returns - " + genre);
    return genre;
  }

  /**
   * Gets the key.
   *
   * @param beatportObject
   *          the object in array
   * @return the key
   */
  private static String getKey(JSONObject beatportObject) {
    String key;
    try {
      JSONObject keyObject = beatportObject.getJSONObject("key");
      key = keyObject.optString("shortName").replace("min", "").replace("maj", "")
          .replace("&#9839", "");
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
   *          the site
   * @return the purchase link
   */
  private static String getPurchaseLink(JSONObject beatportObject, String site) {
    String link = "";
    if (beatportObject != null) {
      link = site;
      System.out.println("getPurchaseLink() - Returns - " + link);
    }
    return link;
  }

  /**
   * Gets the track url.
   *
   * @param beatportObject
   *          the object in array
   * @return the track url
   */
  private static String getTrackURL(JSONObject beatportObject) {
    String TrackURL;
    try {
      TrackURL = beatportObject.getString("sampleUrl");
    } catch (JSONException ex) {
      TrackURL = "";
    }
    System.out.println("getTrackURL() - Returns - " + TrackURL);
    return TrackURL;
  }
}

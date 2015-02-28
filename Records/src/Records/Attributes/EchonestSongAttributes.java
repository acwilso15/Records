package Records.Attributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class EchonestSongAttributes.
 *
 * @author Alexander
 */
class EchonestSongAttributes {

  /**
   * Adds the zeros.
   *
   * @param attributeToConvert the attribute to convert
   * @return the string
   */
  public static String addZeros(String attributeToConvert) {
    if (attributeToConvert == null || "".equals(attributeToConvert)) {
      attributeToConvert = "000";
    } else {
      double value = Double.parseDouble(attributeToConvert);
      if (value < 10) {
        attributeToConvert = "00" + value;
      } else if (value < 100 && value > 10) {
        attributeToConvert = "0" + value;
      } else {
        attributeToConvert = "" + value;
      }
      attributeToConvert = attributeToConvert.split("[.]")[0];
    }
    return attributeToConvert;
  }

  /**
   * Convert number to key.
   *
   * @return the string
   */
  private static String convertNumberToKey() {
    System.out.println("EchonestSongAttributes.convertNumberToKey()");

    String keys = "unknown";
    switch (key) {
    case "0":
      keys = "C";
      break;
    case "1":
      keys = "C#";
      break;
    case "2":
      keys = "D";
      break;
    case "3":
      keys = "D#";
      break;
    case "4":
      keys = "E";
      break;
    case "5":
      keys = "F";
      break;
    case "6":
      keys = "F#";
      break;
    case "7":
      keys = "G";
      break;
    case "8":
      keys = "G#";
      break;
    case "9":
      keys = "A";
      break;
    case "10":
      keys = "A#";
      break;
    case "11":
      keys = "B";
      break;
    }
    return keys;
  }

  /**
   * Creates the value set.
   *
   * @return the array list
   */
  private static ArrayList<String> createValueSet() {
    System.out.println("EchonestSongAttributes.createValueSet()");

    ArrayList<String> valSet = new ArrayList<String>();
    if (DuplicateChecker.isDuplicate(titles, artists, byPass)) {
      valSet = null;
    } else {
      try {
        parseJSON(artists, titles);
      } catch (Exception e) {
        e.printStackTrace();
      }

      String Key = "", BPM = "";

      if (bpms.equals("")) {
        BPM = addZeros(beatBPM);
      } else {
        BPM = addZeros(bpms);
      }

      if (!key.equals("")) {
        Key = convertNumberToKey();
      } else if (beatKey != null) {
        Key = beatKey;
      }
      String Dance = addZeros(danceability);
      String EnergyAmount = addZeros(energy);
      String Acoustic = addZeros(acousticness);

      valSet.add(titles);
      valSet.add(artists);
      valSet.add(BPM);
      valSet.add(Key);
      valSet.add(Dance);
      valSet.add(EnergyAmount);
      valSet.add(Acoustic);
      valSet.add(albumTitles);
      valSet.add(RssGenres);
      valSet.add(PreviewURL); // FilePathWay
      valSet.add(PurchaseLink);
    }
    return valSet;
  }

  /**
   * Gets the echonest input stream.
   *
   * @param artistLine the artist line
   * @param titleLine the title line
   * @return the echonest input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static InputStream getEchonestInputStream(String artistLine, String titleLine)
      throws IOException {
    String site = "http://developer.echonest.com/api/v4/song/search?api_key=LXSEHQYDQJBY8GYMO&format=json&artist="
        + artistLine.replaceAll(" ", "%20")
        + "&title="
        + titleLine.replaceAll(" ", "%20").replaceAll("&", "")
        + "&results=1&bucket=audio_summary&bucket=song_type&bucket=song_hotttnesss&bucket=song_discovery";
    URL url =  new URL(site);
    System.out.println(url);
    InputStream is = url.openStream();
    trackTime();
    return is;
  }


  /**
   * Gets the json string object.
   *
   * @param sumObject the sum object
   * @param lookupName the lookup name
   * @return the json string object
   */
  private static String getJsonStringObject(JSONObject sumObject, String lookupName) {
    String attribute;
    try {
      attribute = sumObject.optString(lookupName);
    } catch (Exception ex) {
      attribute = "";
      Logger.getLogger(ItunesInfoRetriever.class.getName()).log(Level.SEVERE, null, ex);
    }
    return attribute;
  }

  /**
   * Retrieve info.
   *
   * @param artistLine          the artist line
   * @param titleLine          the title line
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void parseJSON(String artistLine, String titleLine) throws IOException {
    System.out
        .println("EchonestSongAttributes.parseJSON(" + artistLine + ", " + titleLine + ")");
    String Path ="";
    InputStream is = getEchonestInputStream(artistLine, titleLine);
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line = "";
    while ((line = br.readLine()) != null) {
      Path = Path + line;
    }
    try {
      JSONObject sumObject = sumObject(Path);
      bpms = getJsonStringObject(sumObject, "tempo");
      key = getJsonStringObject(sumObject, "key");
      danceability = getJsonStringObject(sumObject, "danceability");
      energy = ""
          + ((Double.parseDouble(getJsonStringObject(sumObject, "energy")) + (Double
              .parseDouble(getJsonStringObject(sumObject, "valence")))) / 2);
      acousticness = getJsonStringObject(sumObject, "acousticness");
    } catch (JSONException ex) {
      if (!(!resetNoNum)) {
        bpms = "";
        key = "";
        danceability = "";
        energy = "";
        acousticness = "";
      } else {
        System.out.println("No numbers...Resetting...");
        String resetTitle = titleLine.replaceAll("[0-9]", "").replace("/", "-");
        resetNoNum = true;
        String sub = "";
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(resetTitle);
        if (m.find()) {
          sub = m.group(1);
        }
        resetTitle = titleLine.replace(sub, "");
        parseJSON(AttributeCleaner.cleanAttribute("Title", resetTitle), artistLine);
      }
    }
  }

  /**
   * Sum object.
   *
   * @param Path the path
   * @return the JSON object
   * @throws JSONException the JSON exception
   */
  private static JSONObject sumObject(String Path)
      throws JSONException{
    JSONObject sumObject = null;
    JSONObject outerObject;
    JSONObject objectInArray = null;
    outerObject = new JSONObject(Path);
    JSONObject innerObject = outerObject.getJSONObject("response");
    JSONArray songArray = innerObject.getJSONArray("songs");
    objectInArray = songArray.getJSONObject(0);
    sumObject = objectInArray.getJSONObject("audio_summary");
    return sumObject;
  }

  /**
   * Parses the xml.
   *
   * @param artist
   *          the artist
   * @param title
   *          the title
   * @param bypassDuplicate
   *          the bypass duplicate
   * @return the array list
   */
  static ArrayList<String> retrieveEchoInfo(String artist, String title, boolean bypassDuplicate) {
    System.out.println("EchonestSongAttributes.parseXml(" + artist + ", " + title + ", "+ bypassDuplicate + ")");
    byPass = bypassDuplicate;
    artists = artist.trim();
    if (!(title.contains("(") || title.contains(")"))) {
      titles = title;
    } else {
      titles = stripPerenthesis(title);
    }

    ArrayList<String> ItunesValSet = ItunesInfoRetriever.getInfo(titles, artists);
    PreviewURL = ItunesValSet.get(0).toString();
    PurchaseLink = ItunesValSet.get(1).toString();
    albumTitles = ItunesValSet.get(2).toString().replaceAll("'", "");
    RssGenres = ItunesValSet.get(3).toString();
    titles = ItunesValSet.get(4).toString().replaceAll("'", "");
    artists = ItunesValSet.get(5).toString().replaceAll("'", "");

    if (PreviewURL.equals("") || PurchaseLink.equals("") || albumTitles.equals("")
        || RssGenres.equals("")) {
      ArrayList<String> beatportValSet = BeatportInfoRetriever.getBeatportInfo(title, artist);
      if (PurchaseLink.equals("")) {
        PurchaseLink = beatportValSet.get(1).toString();
      }
      if (albumTitles.equals("")) {
        albumTitles = beatportValSet.get(2).toString();
      }
      if (RssGenres.equals("")) {
        RssGenres = beatportValSet.get(3).toString();
      }
      beatBPM = beatportValSet.get(4).toString();
      beatKey = beatportValSet.get(5).toString();
    }
    ArrayList<String> valSet = createValueSet();
    // valSet.add(Path);
    return valSet;
  }

  /**
   * Strip perenthesis.
   *
   * @param titleLine
   *          the title line
   * @return the string
   */
  private static String stripPerenthesis(String titleLine) {
    System.out.println("EchonestSongAttributes.stripPerenthesis(" + titleLine + ")");

    String newTitle = titleLine;
    if (!(titleLine.contains("remix[)]") || titleLine.contains("Remix[)]")
        || titleLine.contains("edit[)]") || titleLine.contains("mashup[)]"))) {
      newTitle = newTitle.split("[(]")[0];
    }
    return newTitle;
  }

  /**
   * Track time.
   */
  private static void trackTime() {
    System.out.println("EchonestSongAttributes.timeTrack()");
    if (limitUsed == 0) {
      startTime = System.currentTimeMillis() / 1000;
      System.out.println("Setting startTime = " + startTime);
    }
    CurrentTime = System.currentTimeMillis() / 1000;
    long timeInSeconds = (CurrentTime - startTime);
    limitUsed++;
    int outOfLimit = 120;
    System.out.println(limitUsed + " out of " + outOfLimit);
    int timeLeft = (int) (60 - timeInSeconds);
    System.out.println("Time left = " + (timeLeft));
    if ((limitUsed >= (outOfLimit)) && (timeInSeconds < 60)) {
      try {
        System.out.println("Waiting for the next available connection...");
        TimeUnit.SECONDS.sleep(60 - timeInSeconds);
      } catch (InterruptedException ex) {
        Logger.getLogger(EchonestSongAttributes.class.getName()).log(Level.SEVERE, null, ex);
      }
      limitUsed = 1;
      startTime = System.currentTimeMillis() / 1000;
      System.out.println("Resetting startTime = " + startTime);
    } else if (timeInSeconds >= 60) {
      limitUsed = 1;
      startTime = (System.currentTimeMillis() / 1000) + timeLeft;
      System.out.println("Resetting startTime = " + startTime);
    }
  }

  /** The beat key. */
  private static String key, artists, titles, bpms, danceability, energy, acousticness,
      albumTitles, RssGenres, PreviewURL, PurchaseLink, beatBPM, beatKey;

  /** The by pass. */
  private static boolean resetNoNum, byPass;

  /** The Current time. */
  private static long CurrentTime;

  /** The limit used. */
  private static int limitUsed;

  /** The start time. */
  private static long startTime;
}

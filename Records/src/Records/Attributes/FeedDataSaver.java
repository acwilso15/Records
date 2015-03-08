package Records.Attributes;

import java.util.ArrayList;
import java.util.HashMap;

import Records.Database.DatabaseSetup;
import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedDataSaver.
 *
 * @author Alexander
 */
public class FeedDataSaver {

  /**
   * Gets the cur saved song.
   *
   * @return the CurSavedSong
   */
  public static String getCurSavedSong() {
    return CurSavedSong;
  }

  /**
   * Gets the rss song counter.
   *
   * @return the arrayCounter
   */
  public static int getRssSongCounter() {
    return RssSongCounter;
  }

  /**
   * Sets the cur saved song.
   *
   * @param aCurSavedSong
   *          the CurSavedSong to set
   */
  private static void setCurSavedSong(String RssTitle, String musicians, String location) {
    System.out.println("Importing " + RssTitle + " by: " + musicians);
    CurSavedSong = "Importing: " + RssTitle + " - " + musicians + " - " + location;
  }

  /**
   * Sets the rss song counter.
   *
   * @param aArrayCounter
   *          the arrayCounter to set
   */
  private static void setRssSongCounter(int aArrayCounter) {
    System.out.println("FeedDataSaver.setArrayCounter(" + aArrayCounter + ")");
    RssSongCounter = aArrayCounter;
    System.out.println("-------------" + getRssSongCounter() + "/"
        + DatabaseSetup.splash.preRssExtractCount + "--------------------");
  }

  /** The Rss song counter. */
  private static int RssSongCounter;

  /** The Cur saved song. */
  private static String CurSavedSong;


  /**
   * Read feeds.
   *
   * @param key
   *          the key
   */
  void readFeeds(FeedDataPaths key) {
    System.out.println("FeedDataSaver.readFeeds()");
    String Location = key.getLocation();
    String Order = key.getOrder();
    String[][] rssSongsArray = key.getRssSongsArray();
    int numOfSongs = key.getNumOfSongs();

    for (int i = 0; i < numOfSongs; i++) {
      String title = "";
      String artist = "";
      if (Order.equals("Artist_Title")) {
        artist = rssSongsArray[i][0];
        title = rssSongsArray[i][1];
      } else {// Title_Artist
        title = rssSongsArray[i][1];
        artist = rssSongsArray[i][0];
      }
      title = AttributeCleaner.cleanAttribute("Title", title).replace("~", "");
      artist = AttributeCleaner.cleanAttribute("Artist", artist).replace("~", "");
      save(Location, title, artist);
    }
  }

  /**
   * Save.
   *
   * @param Token
   *          the token
   * @param Location
   *          the location
   * @param RssString
   *          the rss string
   */
  void save(String Location, String title, String artist) {
    System.out.println("FeedDataSaver.Save(" + Location + ", " + title + ", " + artist + ")");
    String bpms, theKeys, Danceability, Energy, Acousticness, albumTitles, RssGenres;
    setRssSongCounter(getRssSongCounter() + 1);
    setCurSavedSong(title, artist, Location);
    ArrayList<String> valSet = DuplicateChecker.getValues(Location, title.replaceAll("/", "-"),
        artist);

    if (valSet == null) {// If duplicate was found
      String previousLocation;
      HashMap<Object, FeedDataStore> feedDataSet = RecordsMain.getFeedSongHash();
      FeedDataStore feedVal = feedDataSet.get(title + "," + artist);
      if (title.equals(feedVal.getRssTitle()) && artist.equals(feedVal.getMusicians())) {
        previousLocation = feedVal.getLocation();
        feedVal.setLocation(previousLocation + " " + Location);
      }
    } else {
      title = valSet.get(0);
      artist = valSet.get(1);
      bpms = valSet.get(2);
      theKeys = valSet.get(3).replace(";", "");
      Danceability = valSet.get(4);
      Energy = valSet.get(5);
      Acousticness = valSet.get(6);
      albumTitles = valSet.get(7);
      RssGenres = valSet.get(8);
      String FilePathway = valSet.get(9);
      String PurchaseLink = valSet.get(10);
      String Song_ID = title + "_" + artist + "_" + albumTitles;
      String images = "";

      RecordsMain.getFeedSongHash().put(
          title + "," + artist,
          (new FeedDataStore(Song_ID, title, bpms, RssGenres, Danceability, Energy, Acousticness,
              theKeys, Location, FilePathway, PurchaseLink, albumTitles, images, artist)));
    }
  }
}

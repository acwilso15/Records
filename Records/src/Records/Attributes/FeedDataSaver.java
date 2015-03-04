package Records.Attributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Records.Database.DatabaseSetup;

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

  /** The feed data. */
  private final ArrayList<FeedDataStore> feedData;

  /** The song. */
  private static ArrayList<String> song;

  /** The Rss song counter. */
  private static int RssSongCounter;

  /** The Cur saved song. */
  private static String CurSavedSong;

  /**
   * Instantiates a new feed data saver.
   */
  public FeedDataSaver() {
    System.out.println("FeedDataSaver.FeedDataSaver()");
    feedData = new ArrayList<>();
  }

  /**
   * Gets the feed data.
   *
   * @return the feedData
   */
  public ArrayList<FeedDataStore> getFeedData() {
    System.out.println("FeedDataSaver.getFeedData()");
    return feedData;
  }

  /**
   * Gets the track nodes.
   *
   * @param key
   *          the key
   * @param UrlPath
   *          the url path
   * @param xpathTitle
   *          the xpath title
   * @param xpathArtist
   *          the xpath artist
   * @param x
   *          the x
   * @return the track nodes
   * @throws MalformedURLException
   *           the malformed url exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException
   *           the parser configuration exception
   * @throws SAXException
   *           the SAX exception
   * @throws XPathExpressionException
   *           the x path expression exception
   */
  private NodeList getTrackNodes(FeedDataPaths key, String UrlPath, String path) {
    System.out.println("FeedDataSaver.getTrackNodes(" + key + "," + UrlPath + "," + path + ")");
    NodeList trackNameNodes = null;
    try {
      XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
      XPath xPathEvaluator = XPATH_FACTORY.newXPath();
      XPathExpression nameExpr = xPathEvaluator.compile(path);
      Document document = generateDocument(UrlPath, key);
      trackNameNodes = (NodeList) nameExpr.evaluate(document, XPathConstants.NODESET);
    } catch (XPathExpressionException | IOException | ParserConfigurationException | SAXException e) {
      e.printStackTrace();
    }
    return trackNameNodes;
  }

  /**
   * Generate document.
   *
   * @param UrlPath
   *          the url path
   * @param key
   *          the key
   * @return the document
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException
   *           the parser configuration exception
   * @throws SAXException
   *           the SAX exception
   */
  private Document generateDocument(String UrlPath, FeedDataPaths key) throws java.io.IOException,
      javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException {
    System.out.println("FeedDataSaver.generateDocument(" + key + "," + UrlPath + ")");
    URL url = new URL(UrlPath);
    DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    URLConnection connection = url.openConnection();
    DocumentBuilder db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
    Document document = null;
    try {
      document = db.parse(connection.getInputStream());
    } catch (java.net.SocketException ex) {
      ex.printStackTrace();
      readFeeds(key);
    }
    return document;
  }

  /**
   * Read feeds.
   *
   * @param key
   *          the key
   */
  void readFeeds(FeedDataPaths key) {
    System.out.println("FeedDataSaver.readFeeds()");
    String UrlPath = key.getUrlPath();
    String Location = key.getLocation();
    int numVar = key.getNumVar();
    String Order = key.getOrder();
    String xpathTitle = key.getXpathTitle();
    String xpathArtist = key.getXpathArtist();
    String InType = key.getInType();
    String[][] RssString = key.getRssString();
    int numOfSongs = key.getNumOfSongs();

    if (InType.equals("XML")) {
      for (int i = 0; i < numOfSongs; i++) {
        System.out.println("SAVE from XML");
        String title = "";
        String artist = "";
        if (numVar != 1) {
          title = getTrackNodes(key, UrlPath, xpathTitle).item(i).getTextContent()
              .replace(" - ", "~").replace((i + 1) + ":", "").trim();
          artist = getTrackNodes(key, UrlPath, xpathArtist).item(i).getTextContent()
              .replace(" - ", "~").replace((i + 1) + ":", "").trim();
        } else {
          String trackString = getTrackNodes(key, UrlPath, xpathTitle).item(i).getTextContent()
              .replace(" - ", "~").replace((i + 1) + ":", "").trim();
          if (Order.equals("Artist_Title")) {
            artist = trackString.substring(0, trackString.indexOf("~")).replace("~", "");
            title = trackString.replace(artist, "");
          } else if (Order.equals("Title_Artist")) {
            title = trackString.substring(0, trackString.indexOf("~")).replace("~", "");
            artist = trackString.replace(title, "");
          }
        }
        title = AttributeCleaner.cleanAttribute("Title", title).replace("~", "");
        artist = AttributeCleaner.cleanAttribute("Artist", artist).replace("~", "");
        save(Location, title, artist);
      }
    } else {
      for (int i = 0; i < numOfSongs; i++) {
        String title = "";
        String artist = "";
        System.out.println("SAVE not from XML");
        if (Order.equals("Artist_Title")) {
          artist = RssString[i][0];
          title = RssString[i][1];
        } else {// Title_Artist
          title = RssString[i][1];
          artist = RssString[i][0];
        }
        title = AttributeCleaner.cleanAttribute("Title", title).replace("~", "");
        artist = AttributeCleaner.cleanAttribute("Artist", artist).replace("~", "");
        save(Location, title, artist);
      }
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
    ArrayList<String> valSet = DuplicateChecker.getValues(Location, title.replaceAll("/", "-"), artist);

    if (valSet == null) {
      String previousLocation;
      int feedDataSize = getFeedData().size();
      ArrayList<FeedDataStore> feedDataSet = getFeedData();

      for (int x = 0; x < feedDataSize; x++) {
        FeedDataStore feedVal = feedDataSet.get(x);
        if (title.equals(feedVal.getRssTitle()) && artist.equals(feedVal.getMusicians())) {
          previousLocation = feedVal.getLocation();
          feedVal.setLocation(previousLocation + " " + Location);
          break;
        }
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

      getFeedData().add(
          new FeedDataStore(Song_ID, title, bpms, RssGenres, Danceability, Energy, Acousticness,
              theKeys, Location, FilePathway, PurchaseLink, albumTitles, images, artist));
    }
  }
}

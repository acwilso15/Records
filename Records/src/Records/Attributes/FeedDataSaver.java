package Records.Attributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	 *            the CurSavedSong to set
	 */
	private static void setCurSavedSong(String aCurSavedSong) {
		System.out.println("FeedDataSaver.setCurSavedSong(" + aCurSavedSong
				+ ")");
		CurSavedSong = aCurSavedSong;
	}

	/**
	 * Sets the rss song counter.
	 *
	 * @param aArrayCounter
	 *            the arrayCounter to set
	 */
	private static void setRssSongCounter(int aArrayCounter) {
		System.out.println("FeedDataSaver.setArrayCounter(" + aArrayCounter
				+ ")");
		RssSongCounter = aArrayCounter;
	}

	/** The feed data. */
	private final ArrayList<FeedDataStore> feedData;

	/** The song. */
	private static ArrayList<String> song;

	/** The Rss song counter. */
	private static int RssSongCounter;

	/** The val set. */
	private static ArrayList<String> valSet;

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
	 * Evaluate xpath.
	 *
	 * @param xpathTitle the xpath title
	 * @param xpathArtist the xpath artist
	 * @param x the x
	 * @return the x path expression
	 * @throws XPathExpressionException the x path expression exception
	 */
	private XPathExpression evaluateXpath(String xpathTitle,
			String xpathArtist, int x)
			throws javax.xml.xpath.XPathExpressionException {
		String path;
		if (x == 0) {
			path = xpathTitle;
		} else {
			path = xpathArtist;
		}
		XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
		XPath xPathEvaluator;
		xPathEvaluator = XPATH_FACTORY.newXPath();
		XPathExpression nameExpr;
		nameExpr = xPathEvaluator.compile(path);
		return nameExpr;
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
	 *            the key
	 * @param UrlPath
	 *            the url path
	 * @param xpathTitle
	 *            the xpath title
	 * @param xpathArtist
	 *            the xpath artist
	 * @param x
	 *            the x
	 * @return the track nodes
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	private NodeList getTrackNodes(FeedDataPaths key, String UrlPath,
			String xpathTitle, String xpathArtist, int x)
			throws MalformedURLException, IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException {
		NodeList trackNameNodes;
		XPathExpression nameExpr = evaluateXpath(xpathTitle, xpathArtist, x);
		URL url = new URL(UrlPath);
		DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory
				.newInstance();
		URLConnection connection = url.openConnection();
		DocumentBuilder db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
		Document document = null;
		try {
			document = db.parse(connection.getInputStream());
		} catch (java.net.SocketException ex) {
			readFeeds(key);
		}
		trackNameNodes = (NodeList) nameExpr.evaluate(document,
				XPathConstants.NODESET);
		return trackNameNodes;
	}

	/**
	 * Read feeds.
	 *
	 * @param key
	 *            the key
	 */
	public void readFeeds(FeedDataPaths key) {
		System.out.println("FeedDataSaver.readFeeds(" + key + ")");

		String UrlPath = key.getUrlPath();
		String Location = key.getLocation();
		String Token = key.getToken();
		int numVar = key.getNumVar();
		String Order = key.getOrder();
		String xpathTitle = key.getXpathTitle();
		String xpathArtist = key.getXpathArtist();
		String InType = key.getInType();
		String[][] RssString = key.getRssString();
		int numOfSongs = key.getNumOfSongs();
		NodeList trackNameNodes;
		for (int i = 0; i < numOfSongs; i++) {
			try {
				song = new ArrayList<String>();
				if (!(InType.equals("XML"))) {
					song = new ArrayList<String>();
					if (Order.equals("Artist_Title")) {
						song.add(RssString[i][0]);
						song.add(RssString[i][1]);
					} else {// Title_Artist
						song.add(RssString[i][1]);
						song.add(RssString[i][0]);
					}
				} else {
					for (int x = 0; x < numVar; x++) {
						trackNameNodes = getTrackNodes(key, UrlPath,
								xpathTitle, xpathArtist, x);
						if (trackNameNodes != null) {
							Node trackNameNode = trackNameNodes.item(i);
							String b = trackNameNode.getTextContent().trim();
							String theString = b.replace(" - ", "~")
									.replace((i + 1) + ":", "").trim();
							if (!(numVar == 1)) {
								song.add(theString);
							} else {
								if (Order.equals("Artist_Title")) {
									String theString2 = theString.substring(0,
											theString.indexOf("~")).replace(
											"~", "");
									String theString1 = theString.replace(
											theString2, "");
									song.add(theString1);
									song.add(theString2);
								} else {
									String theString1 = theString.substring(0,
											theString.indexOf("~")).replace(
											"~", "");
									song.add(theString1);
									String theString2 = theString.replace(
											theString1, "");
									song.add(theString2);
								}
							}
						}
					}
				}
				Save(Token, Location, song);
			} catch (Exception Ex) {
				Ex.printStackTrace();
			}
		}

	}

	/**
	 * Save.
	 *
	 * @param Token
	 *            the token
	 * @param Location
	 *            the location
	 * @param RssString
	 *            the rss string
	 */
	private void Save(String Token, String Location, ArrayList<String> RssString) {
		System.out.println("FeedDataSaver.Save(" + Token + ", " + Location
				+ ", " + RssString + ")");
		String originalTitle, originalMusicians, RssTitle = null, musicians = null;
		StringTokenizer st;
		int count = 0;
		String bpms, theKeys, Danceability, Energy, Acousticness, albumTitles, RssGenres;
		while (count < 2) {
			st = new StringTokenizer(RssString.get(count).toString(), Token);
			if (count == 0) {
				originalTitle = st.nextElement().toString();
				RssTitle = AttributeCleaner.cleanAttribute("Title",
						originalTitle);
			}
			if (count == 1) {
				originalMusicians = st.nextElement().toString();
				musicians = AttributeCleaner.cleanAttribute("Artist",
						originalMusicians);
			}
			count++;
		}
		setRssSongCounter(getRssSongCounter() + 1);
		if (RssTitle != null && musicians != null) {
			valSet = DuplicateChecker.getValues(RssTitle.replaceAll("/", "-"),
					musicians);
			if (!(valSet != null)) {
				String previousLocation;
				int feedDataSize = getFeedData().size();
				ArrayList<FeedDataStore> feedDataSet = getFeedData();

				for (int x = 0; x < feedDataSize; x++) {
					FeedDataStore feedVal = feedDataSet.get(x);
					if (RssTitle.equals(feedVal.getRssTitle())) {
						if (musicians.equals(feedVal.getMusicians())) {
							previousLocation = feedVal.getLocation();
							feedVal.setLocation(previousLocation + " "
									+ Location);
							System.out.println(feedVal.getLocation());
							break;
						}
					}
				}
			} else {
				RssTitle = valSet.get(0);
				musicians = valSet.get(1);
				bpms = valSet.get(2);
				theKeys = valSet.get(3).replace(";", "");
				Danceability = valSet.get(4);
				Energy = valSet.get(5);
				Acousticness = valSet.get(6);
				albumTitles = valSet.get(7);
				RssGenres = valSet.get(8);
				String FilePathway = valSet.get(9);
				String PurchaseLink = valSet.get(10);
				String Song_ID = RssTitle + "_" + musicians + "_" + albumTitles;
				String images = "";

				setCurSavedSong("Importing: " + RssTitle + " - " + musicians
						+ " - " + Location);
				System.out.println("");
				System.out.println("Importing " + RssTitle + " by: "
						+ musicians);

				getFeedData().add(
						new FeedDataStore(Song_ID, RssTitle, bpms, RssGenres,
								Danceability, Energy, Acousticness, theKeys,
								Location, FilePathway, PurchaseLink,
								albumTitles, images, musicians));
			}
		}
	}
}

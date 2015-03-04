package Records.Attributes;

import Records.Database.DatabaseSetup;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedDataPaths.
 *
 * @author Alexander
 */
class FeedDataPaths {

	/** The Url path. */
	private final String UrlPath;

	/** The Location. */
	private final String Location;

	/** The xpath title. */
	private final String xpathTitle;

	/** The xpath artist. */
	private final String xpathArtist;

	/** The num of songs. */
	private final int numOfSongs;

	/** The num var. */
	private final int numVar;

	/** The Rss string. */
	private final String[][] RssString;

	/** The In type. */
	private final String InType;

	/** The Order. */
	private final String Order;

	/**
	 * Instantiates a new feed data paths.
	 *
	 * @param UrlPath
	 *            the url path
	 * @param Location
	 *            the location
	 * @param Token
	 *            the token
	 * @param xpathTitle
	 *            the xpath title
	 * @param xpathArtist
	 *            the xpath artist
	 * @param InType
	 *            the in type
	 * @param RssString
	 *            the rss string
	 * @param numOfSongs
	 *            the num of songs
	 * @param numVar
	 *            the num var
	 * @param Order
	 *            the order
	 */
	FeedDataPaths(String UrlPath, String Location,String xpathTitle, String xpathArtist, String InType,
			String[][] RssString, int numOfSongs, int numVar, String Order) {
		this.UrlPath = UrlPath;
		this.Location = Location;
		this.xpathTitle = xpathTitle;
		this.xpathArtist = xpathArtist;
		this.numOfSongs = numOfSongs;
		this.numVar = numVar;
		this.RssString = RssString;
		this.InType = InType;
		this.Order = Order;
		DatabaseSetup.splash.addToPreRssExtractCount(numOfSongs);
	}

	/**
	 * Gets the in type.
	 *
	 * @return the InType
	 */
	public String getInType() {
		return InType;
	}

	/**
	 * Gets the location.
	 *
	 * @return the Location
	 */
	public String getLocation() {
		return Location;
	}

	/**
	 * Gets the num of songs.
	 *
	 * @return the numOfSongs
	 */
	public int getNumOfSongs() {
		return numOfSongs;
	}

	/**
	 * Gets the num var.
	 *
	 * @return the numVar
	 */
	public int getNumVar() {
		return numVar;
	}

	/**
	 * Gets the order.
	 *
	 * @return the Order
	 */
	public String getOrder() {
		return Order;
	}

	/**
	 * Gets the rss string.
	 *
	 * @return the RssString
	 */
	public String[][] getRssString() {
		return RssString;
	}

	/**
	 * Gets the url path.
	 *
	 * @return the UrlPath
	 */
	public String getUrlPath() {
		return UrlPath;
	}

	/**
	 * Gets the xpath artist.
	 *
	 * @return the xpathArtist
	 */
	public String getXpathArtist() {
		return xpathArtist;
	}

	/**
	 * Gets the xpath title.
	 *
	 * @return the xpathTitle
	 */
	public String getXpathTitle() {
		return xpathTitle;
	}

}

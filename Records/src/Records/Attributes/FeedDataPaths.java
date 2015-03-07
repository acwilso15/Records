package Records.Attributes;

import Records.Database.DatabaseSetup;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedDataPaths.
 *
 * @author Alexander
 */
class FeedDataPaths {

	/** The Location. */
	private final String Location;

	/** The num of songs. */
	private final int numOfSongs;

	/** The Rss string. */
	private final String[][] rssSongsArray;

	/** The Order. */
	private final String Order;


	FeedDataPaths(String Location,String Order, String[][] RssString, int numOfSongs) {
		this.Location = Location;
    this.Order = Order;
    this.rssSongsArray = RssString;
    this.numOfSongs = numOfSongs;
		DatabaseSetup.splash.addToPreRssExtractCount(numOfSongs);
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
	 * @return the rssSongsArray
	 */
	public String[][] getRssSongsArray() {
		return rssSongsArray;
	}

}

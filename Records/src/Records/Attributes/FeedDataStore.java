/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedDataStore.
 *
 * @author Alexander
 */
public class FeedDataStore {

	/** The Song_ id. */
	private final String Song_ID;

	/** The rss title. */
	private final String rssTitle;

	/** The rss bpm. */
	private final String rssBPM;

	/** The rss genre. */
	private final String rssGenre;

	/** The Danceability. */
	private final String Danceability;

	/** The Energy. */
	private final String Energy;

	/** The Acousticness. */
	private final String Acousticness;

	/** The rss key. */
	private final String rssKey;

	/** The Location. */
	private String Location;

	/** The File pathway. */
	private final String FilePathway;

	/** The Purchase link. */
	private final String PurchaseLink;

	/** The rss album. */
	private final String rssAlbum;

	/** The images. */
	private final String images;

	/** The rss artist. */
	private final String rssArtist;

	/**
	 * Instantiates a new feed data store.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param RssTitle
	 *            the rss title
	 * @param bpms
	 *            the bpms
	 * @param RssGenres
	 *            the rss genres
	 * @param Danceability
	 *            the danceability
	 * @param Energy
	 *            the energy
	 * @param Acousticness
	 *            the acousticness
	 * @param theKeys
	 *            the the keys
	 * @param Location
	 *            the location
	 * @param FilePathway
	 *            the file pathway
	 * @param PurchaseLink
	 *            the purchase link
	 * @param albumTitles
	 *            the album titles
	 * @param images
	 *            the images
	 * @param musicians
	 *            the musicians
	 */
	FeedDataStore(String Song_ID, String RssTitle, String bpms,
			String RssGenres, String Danceability, String Energy,
			String Acousticness, String theKeys, String Location,
			String FilePathway, String PurchaseLink, String albumTitles,
			String images, String musicians) {
		this.Song_ID = Song_ID;
		this.rssTitle = RssTitle;
		this.rssBPM = bpms;
		this.rssGenre = RssGenres;
		this.Danceability = Danceability;
		this.Energy = Energy;
		this.Acousticness = Acousticness;
		this.rssKey = theKeys;
		this.Location = Location;
		this.FilePathway = FilePathway;
		this.PurchaseLink = PurchaseLink;
		this.rssAlbum = albumTitles;
		this.images = images;
		this.rssArtist = musicians;
	}

	/**
	 * Gets the acousticness.
	 *
	 * @return the Acousticness
	 */
	public String getAcousticness() {
		return Acousticness;
	}

	/**
	 * Gets the album titles.
	 *
	 * @return the albumTitles
	 */
	public String getAlbumTitles() {
		return rssAlbum;
	}

	/**
	 * Gets the bpms.
	 *
	 * @return the bpms
	 */
	public String getBpms() {
		return rssBPM;
	}

	/**
	 * Gets the danceability.
	 *
	 * @return the Danceability
	 */
	public String getDanceability() {
		return Danceability;
	}

	/**
	 * Gets the energy.
	 *
	 * @return the Energy
	 */
	public String getEnergy() {
		return Energy;
	}

	/**
	 * Gets the file pathway.
	 *
	 * @return the FilePathway
	 */
	public String getFilePathway() {
		return FilePathway;
	}

	/**
	 * Gets the images.
	 *
	 * @return the images
	 */
	public String getImages() {
		return images;
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
	 * Gets the musicians.
	 *
	 * @return the musicians
	 */
	public String getMusicians() {
		return rssArtist;
	}

	/**
	 * Gets the purchase link.
	 *
	 * @return the PurchaseLink
	 */
	public String getPurchaseLink() {
		return PurchaseLink;
	}

	/**
	 * Gets the rss genres.
	 *
	 * @return the RssGenres
	 */
	public String getRssGenres() {
		return rssGenre;
	}

	/**
	 * Gets the rss title.
	 *
	 * @return the RssTitle
	 */
	public String getRssTitle() {
		return rssTitle;
	}

	/**
	 * Gets the song_ id.
	 *
	 * @return the Song_ID
	 */
	public String getSong_ID() {
		return Song_ID;
	}

	/**
	 * Gets the the keys.
	 *
	 * @return the theKeys
	 */
	public String getTheKeys() {
		return rssKey;
	}

	/**
	 * Insert feed data.
	 */
	void insertFeedData() {
		System.out.println("FeedDataStore.insertFeedData()");
		RecordsMain.dba.insertIntoSongs(getSong_ID(), getRssTitle(), getBpms(),
				getRssGenres(), getDanceability(), getEnergy(),
				getAcousticness(), getTheKeys(), getLocation(),
				getFilePathway(), getPurchaseLink());
		RecordsMain.dba.insertIntoAlbums(getSong_ID(), getAlbumTitles(),
				getImages());
		RecordsMain.dba.insertIntoArtists(getSong_ID(), getMusicians());
	}

	/**
	 * Sets the location.
	 *
	 * @param Location
	 *            the Location to set
	 */
	public void setLocation(String Location) {
		this.Location = Location;
	}
}

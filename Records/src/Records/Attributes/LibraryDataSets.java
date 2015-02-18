/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryDataSets.
 *
 * @author Alexander
 */
public class LibraryDataSets {

	/**
	 * Adds the row.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @param album
	 *            the album
	 * @param genre
	 *            the genre
	 * @param energy
	 *            the energy
	 * @param danceability
	 *            the danceability
	 * @param acousticness
	 *            the acousticness
	 * @param bpm
	 *            the bpm
	 * @param key
	 *            the key
	 */
	private static void addRow(String title, String artist, String album,
			String genre, String energy, String danceability,
			String acousticness, String bpm, String key) {
		System.out.println("LibraryDataSets.addRow(" + title + ", " + artist
				+ ", " + album + ", " + genre + ", " + energy + ", "
				+ danceability + ", " + acousticness + ", " + bpm + ", " + key
				+ ")");
		DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI()
				.getUserLibrary().getModel();
		LinkedList<Object> songDetails = new LinkedList<>();
		// Build a Vector of Strings for the table row
		songDetails.add(title);
		songDetails.add(artist);
		songDetails.add(album);
		songDetails.add(genre);
		songDetails.add(energy);
		songDetails.add(danceability);
		songDetails.add(acousticness);
		songDetails.add(bpm);
		songDetails.add(key);

		model.addRow(songDetails.toArray());
		RecordsMain.getUI().NumSongs.setText("" + model.getRowCount()
				+ " Songs");
	}

	/**
	 * Gets the acousticness.
	 *
	 * @return the Acousticness
	 */
	private String getAcousticness() {
		return acousticness;
	}

	/**
	 * Gets the album.
	 *
	 * @return the album
	 */
	private String getAlbum() {
		return album;
	}

	/**
	 * Gets the artist.
	 *
	 * @return the artist
	 */
	private String getArtist() {
		return artist;
	}

	/**
	 * Gets the bpm.
	 *
	 * @return the bpm
	 */
	private String getBpm() {
		return bpm;
	}

	/**
	 * Gets the danceability.
	 *
	 * @return the Danceability
	 */
	private String getDanceability() {
		return danceability;
	}

	/**
	 * Gets the energy.
	 *
	 * @return the Energy
	 */
	private String getEnergy() {
		return energy;
	}

	/**
	 * Gets the file path.
	 *
	 * @return the FilePath
	 */
	private String getFilePath() {
		return FilePath;
	}

	/**
	 * Gets the genre.
	 *
	 * @return the genre
	 */
	private String getGenre() {
		return genre;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	private String getImage() {
		return image;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	private String getKey() {
		return key;
	}

	/**
	 * Gets the location.
	 *
	 * @return the Location
	 */
	private String getLocation() {
		return Location;
	}

	/**
	 * Gets the song_ id.
	 *
	 * @return the Song_ID
	 */
	private String getSong_ID() {
		return Song_ID;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	private String getTitle() {
		return title;
	}

	/** The Song_ id. */
	private String Song_ID;

	/** The title. */
	private String title;

	/** The bpm. */
	private String bpm;

	/** The genre. */
	private String genre;

	/** The danceability. */
	private String danceability;

	/** The energy. */
	private String energy;

	/** The acousticness. */
	private String acousticness;

	/** The key. */
	private String key;

	/** The Location. */
	private String Location;

	/** The File path. */
	private String FilePath;

	/** The album. */
	private String album;

	/** The image. */
	private String image;

	/** The artist. */
	private String artist;

	/**
	 * Instantiates a new library data sets.
	 *
	 * @param set
	 *            the set
	 */
	public LibraryDataSets(ArrayList<String> set) {
		this.Song_ID = set.get(0).toString();
		this.title = set.get(1).toString();
		this.artist = set.get(2).toString();
		this.album = set.get(3).toString();
		this.genre = set.get(4).toString();
		this.energy = set.get(5).toString();
		this.danceability = set.get(6).toString();
		this.acousticness = set.get(7).toString();
		this.bpm = set.get(8).toString();
		this.key = set.get(9).toString();
		this.image = set.get(10).toString();
		this.FilePath = set.get(11).toString();
		this.Location = "Directory";
	}

	/**
	 * Insert library data.
	 */
	public void insertLibraryData() {
		System.out.println("LibraryDataSets.InsertLibraryData()");
		RecordsMain.dba.insertIntoLibSongs(getSong_ID(), getTitle(), getBpm(),
				getGenre(), getDanceability(), getEnergy(), getAcousticness(),
				getKey(), getLocation(), getFilePath());
		RecordsMain.dba.insertIntoLibAlbums(getSong_ID(), getAlbum(),
				getImage());
		RecordsMain.dba.insertIntoLibArtists(getSong_ID(), getArtist());
		RecordsMain.dba.insertIntoLibFilePaths(getSong_ID(), getFilePath());
		addRow(getTitle(), getArtist(), getAlbum(), getGenre(), getEnergy(),
				getDanceability(), getAcousticness(), getBpm(), getKey());
	}
}

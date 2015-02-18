package Records.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class DBSQL.
 *
 * @author Sean
 */
public class DBSQL {

	/** The db connection. */
	Connection dbConnection;

	/** The statement. */
	Statement statement;

	/**
	 * Adds the lib charts to pref.
	 *
	 * @param genre
	 *            the genre
	 * @param bpmMax
	 *            the bpm max
	 * @param bpmMin
	 *            the bpm min
	 * @param Added
	 *            the added
	 * @param DanceMax
	 *            the dance max
	 * @param DanceMin
	 *            the dance min
	 * @param EnergyMax
	 *            the energy max
	 * @param EnergyMin
	 *            the energy min
	 * @param AcousticMax
	 *            the acoustic max
	 * @param AcousticMin
	 *            the acoustic min
	 * @return the result set
	 */
	public ResultSet addLibChartsToPref(String genre, String bpmMax,
			String bpmMin, String Added, String DanceMax, String DanceMin,
			String EnergyMax, String EnergyMin, String AcousticMax,
			String AcousticMin) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT Songs.Song_ID,"
							+ "       Songs.song_title,"
							+ "       Artists.ARTIST," + "       Albums.ALBUM,"
							+ "       Songs.genre,"
							+ "       songs.Danceability,"
							+ "       songs.Energy,"
							+ "       songs.Acousticness,"
							+ "       songs.BPM," + "       songs.The_Key,"
							+ "       songs.Location" + " FROM Songs,"
							+ "     Artists," + "     Albums"
							+ " WHERE Songs.Song_ID = Artists.Song_ID"
							+ "  AND Songs.Song_ID = Albums.Song_ID"
							+ "  AND songs.genre LIKE ?"
							+ "  AND songs.BPM < ?" + "  AND songs.BPM > ?"
							+ "  AND songs.Danceability < ?"
							+ "  AND songs.Danceability > ?"
							+ "  AND songs.Energy < ?"
							+ "  AND songs.Energy > ?"
							+ "  AND songs.Acousticness < ?"
							+ "  AND songs.Acousticness > ?" + "  AND ("
							+ Added
							+ ")"
							+ " UNION"
							+ " SELECT LibSongs.Song_ID,"
							+ "       LibSongs.song_title,"
							+ "       LibArtists.ARTIST,"
							+ "       LibAlbums.ALBUM,"
							+ "       LibSongs.genre,"
							+ "       LibSongs.Danceability,"
							+ "       LibSongs.Energy,"
							+ "       LibSongs.Acousticness,"
							+ "       LibSongs.BPM,"
							+ "       LibSongs.The_Key,"
							+ "       LibSongs.Location"
							+ " FROM LibSongs,"
							+ "     LibArtists,"
							+ "     LibAlbums"
							+ " WHERE LibSongs.Song_ID = LibArtists.Song_ID"
							+ "  AND LibSongs.Song_ID = LibAlbums.Song_ID"
							+ "  AND LibSongs.genre LIKE ?"
							+ "  AND LibSongs.BPM < ?"
							+ "  AND LibSongs.BPM > ?"
							+ "  AND LibSongs.Danceability < ?"
							+ "  AND LibSongs.Danceability > ?"
							+ "  AND LibSongs.Energy < ?"
							+ "  AND LibSongs.Energy > ?"
							+ "  AND LibSongs.Acousticness < ?"
							+ "  AND LibSongs.Acousticness > ?");
			stmt.setString(1, (genre + "%"));
			stmt.setString(2, bpmMax);
			stmt.setString(3, bpmMin);
			stmt.setString(4, DanceMax);
			stmt.setString(5, DanceMin);
			stmt.setString(6, EnergyMax);
			stmt.setString(7, EnergyMin);
			stmt.setString(8, AcousticMax);
			stmt.setString(9, AcousticMin);
			stmt.setString(10, (genre + "%"));
			stmt.setString(11, bpmMax);
			stmt.setString(12, bpmMin);
			stmt.setString(13, DanceMax);
			stmt.setString(14, DanceMin);
			stmt.setString(15, EnergyMax);
			stmt.setString(16, EnergyMin);
			stmt.setString(17, AcousticMax);
			stmt.setString(18, AcousticMin);

			// System.out.println("RESULTSET: addLibChartsToPref");
			result = stmt.executeQuery();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * All charts.
	 *
	 * @return the result set
	 * @throws SQLException
	 *             the SQL exception
	 */
	ResultSet AllCharts() throws SQLException {
		ResultSet result = null;
		PreparedStatement stmt = dbConnection
				.prepareStatement("SELECT Songs.Song_ID,"
						+ "       songs.song_title," + "       Artists.ARTIST,"
						+ "       Albums.ALBUM," + "       songs.genre,"
						+ "       songs.BPM," + "       songs.The_Key,"
						+ "       songs.Location" + " FROM songs,"
						+ "     artists," + "     albums"
						+ " WHERE songs.Song_ID = artists.Song_ID"
						+ "  AND songs.Song_ID = albums.Song_ID");
		// System.out.println("RESULTSET: AllCharts");
		result = stmt.executeQuery();
		// System.out.println("quere successfull!!");
		return result;
	}

	/**
	 * Creates the chart tables.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	void createChartTables() throws SQLException {
		executeQuere("create table Songs"
				+ "(Song_ID VARCHAR(500) primary key, "
				+ "Song_Title VARCHAR(256)," + "BPM VARCHAR(8),"
				+ "Genre VARCHAR(64)," + "Danceability VARCHAR(8),"
				+ "Energy VARCHAR(8)," + "Acousticness VARCHAR(8),"
				+ "The_Key VARCHAR(8)," + "Location VARCHAR(200),"
				+ "FilePathway VARCHAR(200), " + "Purchase_Link VARCHAR(200))");
		executeQuere("create table Albums" + "(Song_ID VARCHAR(500),"
				+ " Album VARCHAR(128)," + " Image VARCHAR(200),"
				+ " constraint Song_PK primary key (Song_ID))");
		executeQuere("create table Artists" + "(Song_ID VARCHAR(500),"
				+ " Artist VARCHAR(128),"
				+ " constraint Artists_PK primary key (Song_ID))");
		executeQuere("create table ChartAddedDate"
				+ "(dateUpdated VARCHAR(128))");
	}

	/**
	 * Creates the directory.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void createDirectory() throws SQLException {
		executeQuere("create table LibDirectory" + "(Name VARCHAR(200),"
				+ "Directory VARCHAR(200))");
	}

	/**
	 * Creates the lib tables.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	void createLibTables() throws SQLException {
		executeQuere("create table LibSongs"
				+ "(Song_ID VARCHAR(500) primary key, "
				+ "Song_Title VARCHAR(256)," + "BPM VARCHAR(8),"
				+ "Genre VARCHAR(64)," + "Danceability VARCHAR(8),"
				+ "Energy VARCHAR(8)," + "Acousticness VARCHAR(8),"
				+ "The_Key VARCHAR(8)," + "Location VARCHAR(200),"
				+ "FilePathway VARCHAR(200))");
		executeQuere("create table LibAlbums" + "(Song_ID VARCHAR(500),"
				+ " Album VARCHAR(128)," + " Image VARCHAR(200),"
				+ " constraint LibSong_PK primary key (Song_ID))");
		executeQuere("create table LibArtists" + "(Song_ID VARCHAR(500),"
				+ " Artist VARCHAR(128),"
				+ " constraint LibArtists_PK primary key (Song_ID))");
		executeQuere("create table LibFilePaths" + "(Song_ID VARCHAR(500),"
				+ "FilePath VARCHAR(200),"
				+ " constraint LibFilePaths_PK primary key (Song_ID))");
	}

	/**
	 * Def querey.
	 *
	 * @param genre
	 *            the genre
	 * @param bpmMax
	 *            the bpm max
	 * @param bpmMin
	 *            the bpm min
	 * @param DanceMax
	 *            the dance max
	 * @param DanceMin
	 *            the dance min
	 * @param EnergyMax
	 *            the energy max
	 * @param EnergyMin
	 *            the energy min
	 * @param AcousticMax
	 *            the acoustic max
	 * @param AcousticMin
	 *            the acoustic min
	 * @return the result set
	 */
	public ResultSet defQuerey(String genre, String bpmMax, String bpmMin,
			String DanceMax, String DanceMin, String EnergyMax,
			String EnergyMin, String AcousticMax, String AcousticMin) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT LibSongs.Song_ID,"
							+ "       LibSongs.song_title,"
							+ "       LibArtists.ARTIST,"
							+ "       LibAlbums.ALBUM,"
							+ "       LibSongs.genre,"
							+ "       LibSongs.Danceability,"
							+ "       LibSongs.Energy,"
							+ "       LibSongs.Acousticness,"
							+ "       LibSongs.BPM,"
							+ "       LibSongs.The_Key,"
							+ "       LibSongs.Location" + " FROM LibSongs,"
							+ "     LibArtists," + "     LibAlbums"
							+ " WHERE LibSongs.Song_ID = LibArtists.Song_ID"
							+ "  AND LibSongs.Song_ID = LibAlbums.Song_ID"
							+ "  AND LibSongs.genre LIKE ?"
							+ "  AND LibSongs.BPM < ?"
							+ "  AND LibSongs.BPM > ?"
							+ "  AND LibSongs.Danceability < ?"
							+ "  AND LibSongs.Danceability > ?"
							+ "  AND LibSongs.Energy < ?"
							+ "  AND LibSongs.Energy > ?"
							+ "  AND LibSongs.Acousticness < ?"
							+ "  AND LibSongs.Acousticness > ?");
			stmt.setString(1, (genre + "%"));
			stmt.setString(2, bpmMax);
			stmt.setString(3, bpmMin);
			stmt.setString(4, DanceMax);
			stmt.setString(5, DanceMin);
			stmt.setString(6, EnergyMax);
			stmt.setString(7, EnergyMin);
			stmt.setString(8, AcousticMax);
			stmt.setString(9, AcousticMin);

			// System.out.println("RESULTSET: defQuerey");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Delete by id.
	 *
	 * @param table
	 *            the table
	 * @param id
	 *            the id
	 */
	void deleteByID(String table, String id) {
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("DELETE "
					+ "FROM " + table + " " + "WHERE song_ID = ?");
			// stmt.setString(1, table);
			stmt.setString(1, id);
			// //System.out.println("DELETE: deleteByID");
			stmt.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Drop directory.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void dropDirectory() throws SQLException {
		executeQuere("drop TABLE LibDirectory");
	}

	/**
	 * Execute quere.
	 *
	 * @param stmt
	 *            the stmt
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void executeQuere(String stmt) throws SQLException {
		statement.execute(stmt);
	}

	/**
	 * Gets the all file paths.
	 *
	 * @return the all file paths
	 */
	public ResultSet getAllFilePaths() {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT * FROM LibFilePaths");
			// System.out.println("RESULTSET: getAllFilePaths");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Gets the all library.
	 *
	 * @return the all library
	 */
	public ResultSet getAllLibrary() {
		ResultSet result = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("SELECT *"
					+ " FROM LibSongs," + "     LibArtists," + "     LibAlbums"
					+ " WHERE LibSongs.Song_ID = LibArtists.Song_ID"
					+ "  AND LibSongs.Song_ID = LibAlbums.Song_ID");

			result = stmt.executeQuery();
		} catch (SQLException ex) {
			// Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null,
			// ex);
		}
		return result;
	}

	/**
	 * Gets the chart added date.
	 *
	 * @return the chart added date
	 */
	public ResultSet getChartAddedDate() {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT dateUpdated"
							+ " FROM ChartAddedDate");
			// System.out.println("RESULTSET: getChartAddedDate");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Gets the charts.
	 *
	 * @param Added
	 *            the added
	 * @return the charts
	 */
	public ResultSet getCharts(String Added) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT Songs.Song_ID,"
							+ "       Songs.song_title,"
							+ "       Artists.ARTIST," + "       Albums.ALBUM,"
							+ "       Songs.genre," + "       Songs.BPM,"
							+ "       Songs.The_Key," + "       Songs.Location"
							+ " FROM Songs," + "     Artists," + "     Albums"
							+ " WHERE Songs.Song_ID = Artists.Song_ID"
							+ "  AND Songs.Song_ID = Albums.Song_ID"
							+ "  AND (" + Added + ")");

			// System.out.println("RESULTSET: getCharts");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Gets the file pathway.
	 *
	 * @param Song
	 *            the song
	 * @param Artist
	 *            the artist
	 * @return the file pathway
	 */
	public ResultSet getFilePathway(String Song, String Artist) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT songs.FilePathway,"
							+ "       artists.artist,"
							+ "       songs.song_title" + " FROM Songs,"
							+ "     Artists" + " WHERE Artists.artist = ?"
							+ "  AND songs.song_title = ?");
			stmt.setString(1, Artist);
			stmt.setString(2, Song);

			// System.out.println("RESULTSET: getFilePathway");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Gets the ID by path.
	 *
	 * @param Path
	 *            the path
	 * @return the ID by path
	 * @throws SQLException
	 *             the SQL exception
	 */
	ResultSet getIDByPath(String Path) throws SQLException {
		ResultSet result = null;
		PreparedStatement stmt = this.dbConnection.prepareStatement("SELECT *"
				+ " FROM LibSongs " + " WHERE FilePathway = ?");
		stmt.setString(1, Path);

		// System.out.println("RESULTSET: getIDByPath");
		result = stmt.executeQuery();
		return result;
	}

	/**
	 * Gets the imagepath.
	 *
	 * @param album
	 *            the album
	 * @return the imagepath
	 */
	public ResultSet getImagepath(String album) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT Image" + " FROM LibAlbums"
							+ " WHERE Album= '" + album + "'");
			// System.out.println("RESULTSET: getImagepath");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Gets the lib directory.
	 *
	 * @return the lib directory
	 * @throws SQLException
	 *             the SQL exception
	 */
	public ResultSet getLibDirectory() throws SQLException {
		ResultSet result = null;
		PreparedStatement stmt = this.dbConnection.prepareStatement("SELECT *"
				+ " FROM LibDirectory");
		// System.out.println("RESULTSET: getLibDirectory");
		result = stmt.executeQuery();
		return result;
	}

	/**
	 * Gets the lib file pathway.
	 *
	 * @param Song
	 *            the song
	 * @param Artist
	 *            the artist
	 * @return the lib file pathway
	 */
	public ResultSet getLibFilePathway(String Song, String Artist) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT Libsongs.FilePathway,"
							+ "       Libartists.artist,"
							+ "       Libsongs.song_title" + " FROM LibSongs,"
							+ "     LibArtists"
							+ " WHERE LibArtists.artist = ?"
							+ "  AND Libsongs.song_title = ?");
			stmt.setString(1, Artist);
			stmt.setString(2, Song);

			// System.out.println("RESULTSET: getLibFilePathway");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Gets the purchase url.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @return the purchase url
	 */
	public ResultSet getPurchaseURL(String title, String artist) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement("SELECT Purchase_Link" + " FROM Songs,"
							+ "Artists" + " WHERE songs.song_title= ?"
							+ "AND Artists.artist=?");
			stmt.setString(1, title);
			stmt.setString(2, artist);
			// System.out.println("RESULTSET: getPurchaseURL");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Insert into albums.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param album
	 *            the album
	 * @param image
	 *            the image
	 */
	public void insertIntoAlbums(String Song_ID, String album, String image) {
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into Albums" + " values ('"
							+ Song_ID + "', '" + album + "', '" + image + "')");
			// System.out.println("INSERT: insertIntoAlbums");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Insert into artists.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param artist
	 *            the artist
	 */
	public void insertIntoArtists(String Song_ID, String artist) {
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into Artists " + "values ('"
							+ Song_ID + "', '" + artist + "')");
			// System.out.println("INSERT: insertIntoArtists");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Insert into lib albums.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param album
	 *            the album
	 * @param image
	 *            the image
	 */
	public void insertIntoLibAlbums(String Song_ID, String album, String image) {
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into LibAlbums" + " values ('"
							+ Song_ID + "', '" + album + "', '" + image + "')");
			// System.out.println("INSERT: insertIntoLibAlbums");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Insert into lib artists.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param artist
	 *            the artist
	 */
	public void insertIntoLibArtists(String Song_ID, String artist) {
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into LibArtists " + "values ('"
							+ Song_ID + "', '" + artist + "')");
			// System.out.println("INSERT: insertIntoLibArtists");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Insert into lib file paths.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param FilePath
	 *            the file path
	 */
	public void insertIntoLibFilePaths(String Song_ID, String FilePath) {
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into LibFilePaths " + "values ('"
							+ Song_ID + "', '" + FilePath + "')");
			// //System.out.println("INSERT: insertIntoLibFilePaths");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Insert into lib songs.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param title
	 *            the title
	 * @param bpm
	 *            the bpm
	 * @param genre
	 *            the genre
	 * @param Danceability
	 *            the danceability
	 * @param Energy
	 *            the energy
	 * @param Acousticness
	 *            the acousticness
	 * @param theKey
	 *            the the key
	 * @param Location
	 *            the location
	 * @param FilePathway
	 *            the file pathway
	 */
	public void insertIntoLibSongs(String Song_ID, String title, String bpm,
			String genre, String Danceability, String Energy,
			String Acousticness, String theKey, String Location,
			String FilePathway) {
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into LibSongs values ('"
							+ Song_ID + "', '" + title + "', '" + bpm + "', '"
							+ genre + "', '" + Danceability + "', '" + Energy
							+ "', '" + Acousticness + "', '" + theKey + "', '"
							+ Location + "', '" + FilePathway + "')");
			// System.out.println("INSERT: insertIntoLibSongs");
			stmt.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Insert into songs.
	 *
	 * @param Song_ID
	 *            the song_ id
	 * @param title
	 *            the title
	 * @param bpm
	 *            the bpm
	 * @param genre
	 *            the genre
	 * @param Danceability
	 *            the danceability
	 * @param Energy
	 *            the energy
	 * @param Acousticness
	 *            the acousticness
	 * @param theKey
	 *            the the key
	 * @param Location
	 *            the location
	 * @param FilePathway
	 *            the file pathway
	 * @param PurchaseLink
	 *            the purchase link
	 */
	public void insertIntoSongs(String Song_ID, String title, String bpm,
			String genre, String Danceability, String Energy,
			String Acousticness, String theKey, String Location,
			String FilePathway, String PurchaseLink) {
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into Songs values ('" + Song_ID
							+ "', '" + title + "', '" + bpm + "', '" + genre
							+ "', '" + Danceability + "', '" + Energy + "', '"
							+ Acousticness + "', '" + theKey + "', '"
							+ Location + "', '" + FilePathway + "', '"
							+ PurchaseLink + "')");
			// //System.out.println("INSERT: insertIntoSongs");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Insert lib directory path.
	 *
	 * @param Path
	 *            the path
	 */
	public void insertLibDirectoryPath(String Path) {
		String name = "Directory";
		try {
			PreparedStatement stmt = dbConnection
					.prepareStatement("insert into LibDirectory values ('"
							+ name + "', '" + Path + "')");
			// System.out.println("INSERT: insertLibDirectoryPath");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Search bar.
	 *
	 * @param Search
	 *            the search
	 * @return the result set
	 */
	public ResultSet SearchBar(String Search) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = this.dbConnection
					.prepareStatement(" SELECT *" + " FROM LibSongs,"
							+ "     LibArtists," + "     LibAlbums"
							+ "  WHERE (((LOWER(song_title) LIKE '%" + Search
							+ "%') OR (LOWER(album) LIKE '%" + Search
							+ "%') OR (LOWER(artist) LIKE '%" + Search + "%'))"
							+ "  AND LibSongs.Song_ID = LibArtists.Song_ID"
							+ "  AND LibSongs.Song_ID  = LibAlbums.Song_ID )");
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Search lib by id.
	 *
	 * @param id
	 *            the id
	 * @return the result set
	 */
	ResultSet SearchLibByID(String id) {
		ResultSet result = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement(" SELECT *"
					+ " FROM LibSongs," + "     LibArtists," + "     LibAlbums"
					+ "  WHERE (LibSongs.song_ID = ?"
					+ "  AND LibSongs.Song_ID = LibArtists.Song_ID"
					+ "  AND LibSongs.Song_ID  = LibAlbums.Song_ID )");

			stmt.setString(1, id);
			result = stmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Truncate chart tables.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void truncateChartTables() throws SQLException {
		executeQuere("TRUNCATE TABLE Songs");
		executeQuere("TRUNCATE TABLE Artists");
		executeQuere("TRUNCATE TABLE Albums");
		executeQuere("TRUNCATE TABLE ChartAddedDate");
	}

	/**
	 * Truncate lib tables.
	 */
	public void truncateLibTables() {
		try {
			executeQuere("TRUNCATE TABLE LibSongs");
			executeQuere("TRUNCATE TABLE LibArtists");
			executeQuere("TRUNCATE TABLE LibAlbums");
			executeQuere("TRUNCATE TABLE LibFilePaths");
		} catch (SQLException ex) {
			Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

package Records.Main;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Records.Attributes.FeedDataStore;
import Records.Database.DBSQL;
import Records.Database.DatabaseSetup;
import Records.Email.EmailWindow;
import Records.UserInterface.Marquee;
import Records.UserInterface.SplashClass;
import Records.UserInterface.UserInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordsMain.
 *
 * @author Alexander
 */
public class RecordsMain {

  private static HashMap<Object, FeedDataStore> feedSongHash;
  
	/**
	 * Adds the to file path list.
	 *
	 * @param element
	 *            the element
	 */
	public static void addToFilePathList(String element) {
		System.out.println("RecordsMain.addToFilePathList(" + element + ")");
		filePathList.add(element);
	}

	/**
	 * Clear file path list.
	 */
	public static void clearFilePathList() {
		System.out.println("RecordsMain.addToFilePathList()");
		filePathList.clear();
	}

	/**
	 * Gets the file path list.
	 *
	 * @return the filePathList
	 */
	public static ArrayList<String> getFilePathList() {
		System.out.println("RecordsMain.getFilePathList()");
		return filePathList;
	}

	/**
	 * Gets the ui.
	 *
	 * @return the UI
	 */
	public static UserInterface getUI() {
		return UI;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws ParseException
	 *             the parse exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static void main(String args[]) throws ParseException, SQLException {
		// public static void start(){
		System.out.println("RecordsMain.main()");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(SplashClass.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		setFeedSongHash(new HashMap<Object, FeedDataStore>());

		UI = new UserInterface();
		dialog = new EmailWindow();
		filePathList = new ArrayList<String>();
		dba = new DBSQL();
		marqTest = new Marquee("No Current Song");

		DatabaseSetup.Setup();
	}

	public static HashMap<Object, FeedDataStore> getFeedSongHash() {
    return feedSongHash;
  }

  static void setFeedSongHash(HashMap<Object, FeedDataStore> feedSongHash) {
    RecordsMain.feedSongHash = feedSongHash;
  }

  /** The dialog. */
	public static EmailWindow dialog;

	/** The dba. */
	public static DBSQL dba;

	/** The ui. */
	private static UserInterface UI;

	/** The file path list. */
	private static ArrayList<String> filePathList;

	/** The marq test. */
	public static Marquee marqTest;
}

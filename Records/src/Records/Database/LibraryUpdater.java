/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;

import Records.Attributes.LibraryDataSets;
import Records.Attributes.LibraryMetadataExtractor;
import Records.Main.RecordsMain;
import Records.UserInterface.SplashClass;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryUpdater.
 *
 * @author Alexander
 */
public class LibraryUpdater implements Runnable {

	/**
	 * Adds the file.
	 *
	 * @param filePath
	 *            the file path
	 */
	private static void addFile(String filePath) {
		System.out.println("LibraryUpdater.addFile(" + filePath + ")");

		LibraryMetadataExtractor metadata;
		if (FilenameUtils.isExtension(filePath, "m4a")
				|| FilenameUtils.isExtension(filePath, "mp3")) {
			metadata = new LibraryMetadataExtractor(new File(filePath));
			LibraryDataSets LDS = new LibraryDataSets(metadata.extract());
			LDS.insertLibraryData();
			RecordsMain.clearFilePathList();
			ResultSet res = RecordsMain.dba.getAllFilePaths();
			try {
				while (res.next()) {
					String fPath = res.getString("Filepath");
					RecordsMain.addToFilePathList(fPath);
					System.out.println("Adding file to list: " + fPath);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null,
						ex);
			}
		}
	}

	/**
	 * Count directory files.
	 *
	 * @param topFile
	 *            the top file
	 */
	private static void countDirectoryFiles(File topFile) {
		System.out.println("LibraryUpdater.countDirectoryFiles(" + topFile
				+ ")");
		if (topFile.isDirectory()) {
			for (File f : topFile.listFiles()) {
				countDirectoryFiles(f);
			}
		} else if (topFile.isFile()) {
			if (!(paths.contains(topFile.getAbsolutePath()))) {
				addFile(topFile.getAbsolutePath());
			} else {
				paths.remove(topFile.getAbsolutePath());
			}
		}
	}

	/**
	 * Delete file.
	 */
	private static void deleteFile() {
		System.out.println("LibraryUpdater.deleteFile()");

		DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI()
				.getUserLibrary().getModel();
		String ID = null;
		for (int x = 0; x < paths.size(); x++) {
			try (ResultSet Result = RecordsMain.dba.getIDByPath(paths.get(x)
					.toString())) {
				if (Result.next()) {
					ID = Result.getString("Song_ID");
					ResultSet results = RecordsMain.dba.SearchLibByID(ID);
					try {
						// While there are more results to process
						if (results.next()) {
							String title = results.getString("SONG_TITLE");
							String artist = results.getString("ARTIST");
							String album = results.getString("ALBUM");
							String genre = results.getString("GENRE");

							for (int y = 0; y < model.getRowCount(); y++) {
								if (model.getValueAt(y, 0).equals(title)
										&& model.getValueAt(y, 1)
												.equals(artist)
										&& model.getValueAt(y, 2).equals(album)
										&& model.getValueAt(y, 3).equals(genre)) {
									// System.out.println("Removing Row!");
									model.removeRow(y);
									RecordsMain.dba.deleteByID("LibSongs", ID);
									RecordsMain.dba.deleteByID("LibAlbums", ID);
									RecordsMain.dba
											.deleteByID("LibArtists", ID);
									RecordsMain.dba.deleteByID("LibFilePaths",
											ID);
								}
							}
							RecordsMain.getUI().NumSongs.setText(""
									+ model.getRowCount() + " Songs");
						}
						results.close();

					} catch (SQLException ex) {
						Logger.getLogger(LibraryUpdater.class.getName()).log(
								Level.SEVERE, null, ex);
					}
					RecordsMain.clearFilePathList();
					ResultSet res = RecordsMain.dba.getAllFilePaths();
					try {
						while (res.next()) {
							String fPath = res.getString("Filepath");
							RecordsMain.addToFilePathList(fPath);

						}
					} catch (SQLException ex) {
						Logger.getLogger(DBSQL.class.getName()).log(
								Level.SEVERE, null, ex);
					}

				}
			} catch (SQLException ex) {
				Logger.getLogger(LibraryUpdater.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}
	}

	/** The paths. */
	private static ArrayList paths;

	/** The running. */
	private volatile boolean running = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("LibraryUpdater.run()");
		DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI()
				.getUserLibrary().getModel();
		if (model.getRowCount() != 0) {
			ResultSet res = null;
			try {
				res = RecordsMain.dba.getLibDirectory();
			} catch (SQLException ex) {
				Logger.getLogger(LibraryUpdater.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			String Dir = null;
			try {
				if (!(res.next())) {
					// System.err.println("No Directory!");
				} else {
					Dir = res.getString("Directory");
					// System.out.println(Dir);
				}
				res.close();

			} catch (SQLException ex) {
				Logger.getLogger(LibraryUpdater.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			while (running) {
				System.out.println("Updating");
				try {
					paths = new ArrayList(RecordsMain.getFilePathList());
					System.out.println("Counting Files");
					countDirectoryFiles(new File(Dir));
					if (!paths.isEmpty()) {
						deleteFile();
					}
					System.out.println("Sleeping...");
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					Logger.getLogger(SplashClass.class.getName()).log(
							Level.SEVERE, null, e);
					running = false;
				}
			}
		}
	}

	/**
	 * Terminate.
	 */
	void terminate() {
		System.out.println("LibraryUpdater.terminate()");
		running = false;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Records.Main.RecordsMain;
import Records.UserInterface.SplashClass;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseSetup.
 *
 * @author Alexander
 */
public class DatabaseSetup {

	/**
	 * Import library table.
	 */
	private static void importLibraryTable() {
		try {
			ResultSet result = RecordsMain.dba.getAllLibrary();
			int rowCount = 0;
			while (result.next()) {
				rowCount++;
			}

			result = RecordsMain.dba.getAllLibrary();
			// boolean check = result.next();
			// System.out.println("Row Count = " + rowCount);

			DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI()
					.getUserLibrary().getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			while (result.next()) {
				LinkedList<Object> data = new LinkedList<>();
				// Build a Vector of Strings for the table row
				data.add(result.getString("SONG_TITLE"));
				data.add(result.getString("ARTIST"));
				data.add(result.getString("ALBUM"));
				data.add(result.getString("GENRE"));
				data.add(result.getString("ENERGY"));
				data.add(result.getString("DANCEABILITY"));
				data.add(result.getString("ACOUSTICNESS"));
				data.add(result.getString("BPM"));
				data.add(result.getString("THE_KEY"));

				// System.out.println("Adding row");
				model.addRow(data.toArray());
				RecordsMain.getUI().NumSongs.setText(model.getRowCount()
						+ " Songs");
			}
			// RecordsMain.getUI().setVisible(true);
			RecordsMain.clearFilePathList();
			ResultSet res = RecordsMain.dba.getAllFilePaths();
			try {
				while (res.next()) {
					String fPath = res.getString("Filepath");
					RecordsMain.addToFilePathList(fPath);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			if (rowCount != 0) {
				LibraryUpdaterImp.startUpdate();
			}
		} catch (Exception ex) {
			// Create Library Tables
			try {
				RecordsMain.dba.createLibTables();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			System.out.println("Creating LibTables");
		}
		// System.out.println("Connection success!!");
	}

	/**
	 * Setup.
	 */
	public static void Setup() {
		System.out.println("DatabaseSetup.Setup()");
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		try {
			// Check for database
			Class.forName(driver);
			String url = "jdbc:derby:recordsDB";
			RecordsMain.dba.dbConnection = DriverManager.getConnection(url);
			RecordsMain.dba.statement = RecordsMain.dba.dbConnection
					.createStatement();
		} catch (SQLException | ClassNotFoundException ex) {
			try {
				// Create Database
				Class.forName(driver);
				String url = "jdbc:derby:recordsDB;create=true";
				RecordsMain.dba.dbConnection = DriverManager.getConnection(url);
				RecordsMain.dba.statement = RecordsMain.dba.dbConnection
						.createStatement();
			} catch (ClassNotFoundException | SQLException ex1) {
				Logger.getLogger(DBSQL.class.getName()).log(Level.SEVERE, null,
						ex1);
			}
		}

		// Check for Charts Table
		ResultSet Result;
		try {
			Result = RecordsMain.dba.AllCharts();
			boolean check = Result.next();
			Result.close();
			int reply = JOptionPane.showConfirmDialog(null,
					"Would you like to refresh your chart data?", "Refresh",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				splash = new SplashClass();
				splash.start();
			} else {
				importLibraryTable();
				RecordsMain.getUI().setVisible(true);
			}
		} catch (SQLException ex) {
			try {
				RecordsMain.dba.createChartTables();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			importLibraryTable();
			splash = new SplashClass();
			splash.start();
		}

	}

	/** The splash. */
	public static SplashClass splash;
}

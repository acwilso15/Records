/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Database;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import Records.Attributes.DirectorySelector;
import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryUpdaterImp.
 *
 * @author Alexander
 */
public class LibraryUpdaterImp {

	/**
	 * Start library.
	 */
	public static void startLibrary() {
		System.out.println("LibraryUpdaterImp.startLibrary()");
		stopUpdate();
		runLibraryStart = new DirectorySelector();
		threadLibraryStart = new Thread(runLibraryStart);
		// System.out.println("Starting Library thread: " + threadLibraryStart);
		threadLibraryStart.start();
		// System.out.println("Library successfully started.");
	}

	/**
	 * Start update.
	 */
	public static void startUpdate() {
		System.out.println("LibraryUpdaterImp.startUpdate()");
		DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI()
				.getUserLibrary().getModel();
		if (model.getRowCount() == 0) {
			startLibrary();
		}
		runUpdater = new LibraryUpdater();
		threadUpdate = new Thread(runUpdater);
		System.out.println("Starting Update thread: " + threadUpdate);
		threadUpdate.start();
		System.out.println("Update successfully started.");
	}

	/**
	 * Stop library.
	 */
	private static void stopLibrary() {
		System.out.println("LibraryUpdaterImp.stopLibrary()");

		// System.out.println("Stopping Library thread: " + threadLibraryStart);
		if (threadLibraryStart != null) {
			try {
				runLibraryStart.terminate();
				threadLibraryStart.join();
				System.out.println("Library Thread successfully stopped.");
			} catch (InterruptedException ex) {
				Logger.getLogger(LibraryUpdaterImp.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Stop update.
	 */
	private static void stopUpdate() {
		System.out.println("LibraryUpdaterImp.stopUpdate()");
		stopLibrary();
		// System.out.println("Stopping Update thread: " + threadUpdate);
		if (threadUpdate != null) {
			try {
				runUpdater.terminate();
				threadUpdate.join();
				// System.out.println("Update Thread successfully stopped.");
			} catch (InterruptedException ex) {
				Logger.getLogger(LibraryUpdaterImp.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/** The thread update. */
	private static Thread threadUpdate = null;

	/** The run updater. */
	private static LibraryUpdater runUpdater = null;

	/** The thread library start. */
	private static Thread threadLibraryStart = null;

	/** The run library start. */
	private static DirectorySelector runLibraryStart = null;
}

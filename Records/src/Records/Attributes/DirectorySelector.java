/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import Records.Database.LibraryUpdaterImp;
import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectorySelector.
 *
 * @author Alexander
 */
public class DirectorySelector implements Runnable {

	/** The running. */
	private volatile boolean running = true;

	/**
	 * Checks if is running.
	 *
	 * @return true, if is running
	 */
	public boolean isRunning() {
		return running;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("DirectorySelector.run()");
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText("Import");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showSaveDialog(chooser);
		if (!(result == JFileChooser.APPROVE_OPTION))
			if (result == JFileChooser.CANCEL_OPTION) {
				System.out
						.println("Cancel was selected...Restarting update...");
				LibraryUpdaterImp.startUpdate();
			} else {
				File f = chooser.getSelectedFile();

				RecordsMain.dba.truncateLibTables();
				RecordsMain.clearFilePathList();
				try {
					RecordsMain.dba.createDirectory();
					RecordsMain.dba.insertLibDirectoryPath(f.getAbsolutePath());
				} catch (SQLException ex) {
					try {
						RecordsMain.dba.dropDirectory();
						RecordsMain.dba.createDirectory();
						RecordsMain.dba.insertLibDirectoryPath(f
								.getAbsolutePath());
					} catch (SQLException ex1) {
						Logger.getLogger(DirectorySelector.class.getName())
								.log(Level.SEVERE, null, ex1);
					}
				}

				DefaultTableModel model = (DefaultTableModel) RecordsMain
						.getUI().getUserLibrary().getModel();

				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}

				FileTraverser.importSongs(f.getAbsolutePath());
			}
	}

	/**
	 * Sets the running.
	 *
	 * @param running
	 *            the new running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Terminate.
	 */
	public void terminate() {
		System.out.println("DirectorySelector.terminate()");
		setRunning(false);
	}
}

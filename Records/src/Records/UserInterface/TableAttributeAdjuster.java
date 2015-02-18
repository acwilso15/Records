/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.UserInterface;

import java.util.ArrayList;

import javax.swing.JTable;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class TableAttributeAdjuster.
 *
 * @author Alexander Wilson
 */
class TableAttributeAdjuster {

	/**
	 * Adds the attributes.
	 *
	 * @param tableName
	 *            the table name
	 * @param rowNumber
	 *            the row number
	 * @param columnNumber
	 *            the column number
	 */
	private static void addAttributes(JTable tableName, int rowNumber,
			int columnNumber) {
		songAttributes.add(retrieveTableAttribute(tableName, rowNumber,
				columnNumber));
	}

	/**
	 * Adjust attributes.
	 *
	 * @param song
	 *            the song
	 * @param songView
	 *            the song view
	 * @param tableName
	 *            the table name
	 * @param rowNumber
	 *            the row number
	 * @return the array list
	 */
	static ArrayList<String> AdjustAttributes(String song, String songView,
			JTable tableName, int rowNumber) {
		songAttributes = new ArrayList<String>();
		if (!tableName.equals(RecordsMain.getUI().getUserLibrary())) {
			if (!song.equals(songView)) {
				for (int columnNumber = 0; columnNumber < 7; columnNumber++) {
					addAttributes(tableName, rowNumber, columnNumber);
				}
			} else {
				for (int columnNumber = 0; columnNumber < 7; columnNumber++) {
					addAttributes(tableName, rowNumber, columnNumber);
				}
			}
		} else {
			if (!song.equals(songView)) {
				for (int columnNumber = 0; columnNumber < 9; columnNumber++) {
					addAttributes(tableName, rowNumber, columnNumber);
				}
			} else {
				for (int columnNumber = 0; columnNumber < 9; columnNumber++) {
					addAttributes(tableName, rowNumber, columnNumber);
				}
			}
		}
		return songAttributes;
	}

	// TODO Remove unused code found by UCDetector
	// public static String retrieveModelAttribute(JTable tableName,
	// int rowNumber, int columnNumber) {
	// return tableName.getModel().getValueAt(rowNumber, columnNumber)
	// .toString();
	// }

	/**
	 * Retrieve table attribute.
	 *
	 * @param tableName
	 *            the table name
	 * @param rowNumber
	 *            the row number
	 * @param columnNumber
	 *            the column number
	 * @return the string
	 */
	private static String retrieveTableAttribute(JTable tableName,
			int rowNumber, int columnNumber) {
		return tableName.getValueAt(rowNumber, columnNumber).toString();
	}

	/** The song attributes. */
	private static ArrayList<String> songAttributes;
}
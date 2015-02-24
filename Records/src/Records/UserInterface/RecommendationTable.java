/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.UserInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class RecommendationTable.
 *
 * @author Alexander
 */
class RecommendationTable {

	/**
	 * Adds the to check counter.
	 *
	 * @param checkBox
	 *            the check box
	 */
	private static void addToCheckCounter(JCheckBox checkBox) {
		System.out.println("RecommendationTable.addToCheckCounter()");

		if (checkBox.isSelected()) {
			checkCount++;
		}
	}

	/**
	 * Adds the zero.
	 *
	 * @param num
	 *            the num
	 * @return the string
	 */
	private static String AddZero(int num) {
		System.out.println("RecommendationTable.AddZero(" + num + ")");

		String numString = "";
		if (num < 10) {
			numString = "00" + num;
		} else if (num < 100 && num > 10) {
			numString = "0" + num;
		} else {
			numString = "" + num;
		}
		return numString;
	}

	/**
	 * Append sql.
	 *
	 * @param checkBox
	 *            the check box
	 * @param loopCount
	 *            the loop count
	 * @param location
	 *            the location
	 */
	private static void appendSQL(JCheckBox checkBox, int loopCount,
			String location) {
		System.out.println("RecommendationTable.appendSQL()");
		if (checkBox.isSelected()) {
			if (checkCount < 2 || loopCount == 0) {
				FinalSQL = "songs.Location LIKE '%" + location + "%'";
			}
			if (checkCount > 1 && loopCount > 0) {
				NewSQL = " OR songs.Location LIKE '%" + location + "%'";
				FinalSQL = FinalSQL + NewSQL;
			}
		}
	}

	/**
	 * Recommendations.
	 *
	 * @param song
	 *            the song
	 * @param theKey
	 *            the the key
	 * @param bpm
	 *            the bpm
	 * @param genre
	 *            the genre
	 * @param album
	 *            the album
	 * @param artist
	 *            the artist
	 * @param location
	 *            the location
	 * @param Danceability
	 *            the danceability
	 * @param Energy
	 *            the energy
	 * @param Acousticness
	 *            the acousticness
	 */
	static void recommendations(String song, String theKey, String bpm,
			String genre, String album, String artist, String location,
			String Danceability, String Energy, String Acousticness) {
		System.out.println("RecommendationTable.recommendations(" + song + ", "
				+ theKey + "," + bpm + ", " + genre + "," + album + ", "
				+ artist + "," + location + ", " + Danceability + ", " + Energy
				+ ", " + Acousticness + ")");

		ResultSet results = results(bpm, genre, Danceability, Energy,
				Acousticness);
		try {
			DefaultTableModel originalModel = (DefaultTableModel) RecordsMain
					.getUI().songPrefTable.getModel();

			// clear existing rows
			while (originalModel.getRowCount() > 0) {
				originalModel.removeRow(0);
				if (RecordsMain.getUI().recommendationsTabPane.getSelectedIndex() == 1) {
					RecordsMain.getUI().BasedOn.setVisible(false);
				} else {
					RecordsMain.getUI().BasedOn.setVisible(true);
					DefaultTableModel model2 = (DefaultTableModel) RecordsMain
							.getUI().songPrefTable.getModel();
					setChartSongNumLabel(originalModel, model2);
				}
			}
			// While there are more results to process
			while (results.next()) {
				// Build a Vector of Strings for the table row
				List<String> data = new LinkedList<String>();
				data.add(results.getString("SONG_TITLE"));
				data.add(results.getString("ARTIST"));
				data.add(results.getString("ALBUM"));
				data.add(results.getString("GENRE"));
				data.add(results.getString("BPM"));
				data.add(results.getString("THE_KEY"));
				data.add(results.getString("LOCATION"));

				originalModel.addRow(data.toArray());
				if (RecordsMain.getUI().recommendationsTabPane.getSelectedIndex() == 1) {
					RecordsMain.getUI().BasedOn.setVisible(false);
				} else {
					RecordsMain.getUI().BasedOn.setVisible(true);
					DefaultTableModel model2 = (DefaultTableModel) RecordsMain
							.getUI().songPrefTable.getModel();
					setChartSongNumLabel(originalModel, model2);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			Logger.getLogger(RecommendationTable.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Results.
	 *
	 * @param bpm the bpm
	 * @param genre the genre
	 * @param Danceability the danceability
	 * @param Energy the energy
	 * @param Acousticness the acousticness
	 * @return the result set
	 * @throws NumberFormatException the number format exception
	 */
	private static ResultSet results(String bpm, String genre,
			String Danceability, String Energy, String Acousticness)
			throws java.lang.NumberFormatException {
		ResultSet results = null;
		int bpmI = Integer.parseInt(bpm);
		int Max = bpmI + 5;
		int Min = bpmI - 5;
		int DMax = Integer.parseInt(Danceability) + 20;
		int DMin = Integer.parseInt(Danceability) - 20;
		int EMax = Integer.parseInt(Energy) + 15;
		int EMin = Integer.parseInt(Energy) - 15;
		int AMax = Integer.parseInt(Acousticness) + 15;
		int AMin = Integer.parseInt(Acousticness) - 15;
		String DanceMax = AddZero(DMax);
		String DanceMin = AddZero(DMin);
		String EnergyMax = AddZero(EMax);
		String EnergyMin = AddZero(EMin);
		String AcousticMax = AddZero(AMax);
		String AcousticMin = AddZero(AMin);
		String bpmMax = AddZero(Max);
		String bpmMin = AddZero(Min);
		checkCount = 0;
		addToCheckCounter(RecordsMain.getUI().BillboardCheckbox);
		addToCheckCounter(RecordsMain.getUI().BillboardEDMCheckbox);
		addToCheckCounter(RecordsMain.getUI().BillboardCountryCheckbox);
		addToCheckCounter(RecordsMain.getUI().BillboardRnBCheckbox);
		addToCheckCounter(RecordsMain.getUI().hypemCheckbox);
		addToCheckCounter(RecordsMain.getUI().ShazamCheckbox);
		addToCheckCounter(RecordsMain.getUI().ItunesCheckbox);
		addToCheckCounter(RecordsMain.getUI().ItunesCountryCheckbox);
		addToCheckCounter(RecordsMain.getUI().ItunesRnBCheckbox);
		addToCheckCounter(RecordsMain.getUI().ItunesEDMCheckbox);
		addToCheckCounter(RecordsMain.getUI().UKCheckbox);
		addToCheckCounter(RecordsMain.getUI().BeatportCheckbox);
		if (checkCount == 0) {
			results = RecordsMain.dba.defQuerey(genre, bpmMax, bpmMin,
					DanceMax, DanceMin, EnergyMax, EnergyMin, AcousticMax,
					AcousticMin);
		} else {
			NewSQL = "";
			FinalSQL = "";
			for (int x = 0; x < checkCount; x++) {
				appendSQL(RecordsMain.getUI().BillboardCheckbox, x,
						"Billboard-Pop");
				appendSQL(RecordsMain.getUI().BillboardEDMCheckbox, x,
						"Billboard-EDM");
				appendSQL(RecordsMain.getUI().BillboardCountryCheckbox, x,
						"Billboard-Country");
				appendSQL(RecordsMain.getUI().BillboardRnBCheckbox, x,
						"Billboard-RnB");
				appendSQL(RecordsMain.getUI().hypemCheckbox, x, "Hypem");
				appendSQL(RecordsMain.getUI().ShazamCheckbox, x, "Shazam");
				appendSQL(RecordsMain.getUI().ItunesCheckbox, x, "Itunes-Pop");
				appendSQL(RecordsMain.getUI().ItunesCountryCheckbox, x,
						"Itunes-Country");
				appendSQL(RecordsMain.getUI().ItunesRnBCheckbox, x,
						"Itunes-RnB");
				appendSQL(RecordsMain.getUI().ItunesEDMCheckbox, x,
						"Itunes-EDM");
				appendSQL(RecordsMain.getUI().UKCheckbox, x, "UK40");
				appendSQL(RecordsMain.getUI().BeatportCheckbox, x, "Beatport");
			}
			results = RecordsMain.dba.addLibChartsToPref(genre, bpmMax, bpmMin,
					FinalSQL, DanceMax, DanceMin, EnergyMax, EnergyMin,
					AcousticMax, AcousticMin);
		}
		return results;
	}

	/**
	 * Sets the chart song num label.
	 *
	 * @param model
	 *            the model
	 * @param model2
	 *            the model2
	 */
	private static void setChartSongNumLabel(DefaultTableModel model,
			DefaultTableModel model2) {
		System.out.println("RecommendationTable.setChartSongNumLabel()");
		if (model.getRowCount() == 1) {
			RecordsMain.getUI().chartSongNum.setText(model2.getRowCount()
					+ " Song");
		} else {
			RecordsMain.getUI().chartSongNum.setText(model2.getRowCount()
					+ " Songs");
		}

	}

	/** The check count. */
	private static int checkCount;

	/** The New sql. */
	private static String NewSQL;

	/** The Final sql. */
	private static String FinalSQL;
}

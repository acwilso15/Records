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
   *          the check box
   */
  private static void appendSQL(JCheckBox checkBox, String location) {
    System.out.println("RecommendationTable.appendSQL("+checkBox+","+location+")");
    if (checkBox.isSelected()) {
      if (checkCount < 1) {
        FinalSQL = "songs.Location LIKE '%" + location + "%'";
      }
      if (checkCount > 1) {
        NewSQL = " OR songs.Location LIKE '%" + location + "%'";
        FinalSQL = FinalSQL + NewSQL;
      }
      checkCount++;
    }
  }

  /**
   * Adds the zero.
   *
   * @param num
   *          the num
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
   * Recommendations.
   *
   * @param song
   *          the song
   * @param theKey
   *          the the key
   * @param bpm
   *          the bpm
   * @param genre
   *          the genre
   * @param album
   *          the album
   * @param artist
   *          the artist
   * @param location
   *          the location
   * @param Danceability
   *          the danceability
   * @param Energy
   *          the energy
   * @param Acousticness
   *          the acousticness
   */
  static void recommendations(String song, String theKey, String bpm, String genre, String album,
      String artist, String location, String Danceability, String Energy, String Acousticness) {
    System.out.println("RecommendationTable.recommendations(" + song + ", " + theKey + "," + bpm
        + ", " + genre + "," + album + ", " + artist + "," + location + ", " + Danceability + ", "
        + Energy + ", " + Acousticness + ")");

    ResultSet results = results(bpm, genre, Danceability, Energy, Acousticness);
    try {
      DefaultTableModel originalModel = (DefaultTableModel) RecordsMain.getUI().songPrefTable.getModel();

      // clear existing rows
      while (originalModel.getRowCount() > 0) {
        originalModel.removeRow(0);
        if (RecordsMain.getUI().recommendationsTabPane.getSelectedIndex() == 1) {
          RecordsMain.getUI().BasedOn.setVisible(false);
        } else {
          RecordsMain.getUI().BasedOn.setVisible(true);
          DefaultTableModel model2 = (DefaultTableModel) RecordsMain.getUI().songPrefTable.getModel();
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
          DefaultTableModel model2 = (DefaultTableModel) RecordsMain.getUI().songPrefTable.getModel();
          setChartSongNumLabel(originalModel, model2);
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Results.
   *
   * @param bpm
   *          the bpm
   * @param genre
   *          the genre
   * @param Danceability
   *          the danceability
   * @param Energy
   *          the energy
   * @param Acousticness
   *          the acousticness
   * @return the result set
   * @throws NumberFormatException
   *           the number format exception
   */
  private static ResultSet results(String bpm, String genre, String Danceability, String Energy,
      String Acousticness) throws java.lang.NumberFormatException {

    System.out.println("RecommendationTable.results(" + bpm + ", " + genre + "," + Danceability+ ", " + Energy + ", " + Acousticness + ")");
    ResultSet results = null;
    int bpmI = Integer.parseInt(bpm);
    int Max = bpmI + 5;
    int Min = bpmI - 5;
    int DMax = Integer.parseInt(Danceability) + 20;
    int DMin = Integer.parseInt(Danceability) - 20;
    int EMax = Integer.parseInt(Energy) + 15;
    int EMin = Integer.parseInt(Energy) - 15;
    int AMax = Integer.parseInt(Acousticness) + 25;
    int AMin = Integer.parseInt(Acousticness) - 25;
    String DanceMax = AddZero(DMax);
    String DanceMin = AddZero(DMin);
    String EnergyMax = AddZero(EMax);
    String EnergyMin = AddZero(EMin);
    String AcousticMax = AddZero(AMax);
    String AcousticMin = AddZero(AMin);
    String bpmMax = AddZero(Max);
    String bpmMin = AddZero(Min);
    checkCount = 0;
    NewSQL = "";
    FinalSQL = "";
    appendSQL(RecordsMain.getUI().BillboardCheckbox, "Billboard-Pop");
    appendSQL(RecordsMain.getUI().BillboardEDMCheckbox, "Billboard-EDM");
    appendSQL(RecordsMain.getUI().BillboardCountryCheckbox, "Billboard-Country");
    appendSQL(RecordsMain.getUI().BillboardRnBCheckbox, "Billboard-RnB");
    appendSQL(RecordsMain.getUI().hypemCheckbox, "Hypem");
    appendSQL(RecordsMain.getUI().ShazamCheckbox, "Shazam");
    appendSQL(RecordsMain.getUI().ItunesCheckbox, "Itunes-Pop");
    appendSQL(RecordsMain.getUI().ItunesCountryCheckbox, "Itunes-Country");
    appendSQL(RecordsMain.getUI().ItunesRnBCheckbox, "Itunes-RnB");
    appendSQL(RecordsMain.getUI().ItunesEDMCheckbox, "Itunes-EDM");
    appendSQL(RecordsMain.getUI().UKCheckbox, "UK40");
    appendSQL(RecordsMain.getUI().BeatportCheckbox, "Beatport");
    System.out.println("--------------------------------" + checkCount);
    System.out.println("--------------------------------" + FinalSQL);
    if (checkCount == 0) {
      results = RecordsMain.dba.defQuerey(genre, bpmMax, bpmMin, DanceMax, DanceMin, EnergyMax,EnergyMin, AcousticMax, AcousticMin);
    } else {
      results = RecordsMain.dba.addLibChartsToPref(genre, bpmMax, bpmMin, FinalSQL, DanceMax, DanceMin, EnergyMax, EnergyMin, AcousticMax, AcousticMin);
    }
    return results;
  }

  /**
   * Sets the chart song num label.
   *
   * @param model
   *          the model
   * @param model2
   *          the model2
   */
  private static void setChartSongNumLabel(DefaultTableModel model, DefaultTableModel model2) {
    System.out.println("RecommendationTable.setChartSongNumLabel()");
    if (model.getRowCount() == 1) {
      RecordsMain.getUI().chartSongNum.setText(model2.getRowCount() + " Song");
    } else {
      RecordsMain.getUI().chartSongNum.setText(model2.getRowCount() + " Songs");
    }

  }

  /** The check count. */
  private static int checkCount;

  /** The New sql. */
  private static String NewSQL;

  /** The Final sql. */
  private static String FinalSQL;
}

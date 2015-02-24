package Records.Attributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckboxModifier.
 *
 * @author Alexander Wilson
 */
public class CheckboxModifier {

	/**
	 * Adds the rows.
	 *
	 * @param model
	 *            the model
	 * @param SQLStatement
	 *            the SQL statement
	 */
	private static void addRows(DefaultTableModel model, String SQLStatement) {
		System.out.println("CheckboxModifier.addRows(" + model + ", "
				+ SQLStatement + ")");

		ResultSet results = RecordsMain.dba.getCharts(SQLStatement);
		removeRows(model);

		// While there are more results to process
		try {
			while (results.next()) {
				// Build a List of Strings for the table row
				List<String> songAttributes = new LinkedList<>();
				songAttributes.add(results.getString("SONG_TITLE"));
				songAttributes.add(results.getString("ARTIST"));
				songAttributes.add(results.getString("ALBUM"));
				songAttributes.add(results.getString("GENRE"));
				songAttributes.add(results.getString("BPM"));
				songAttributes.add(results.getString("THE_KEY"));
				String Location = results.getString("LOCATION");
				for (int x = 0; x < notCheckedBoxes.size(); x++) {
					Location = Location.replace(
							(notCheckedBoxes.get(x).toString()), "");
				}
				songAttributes.add(Location.trim().replace("   ", " ")
						.replace("  ", " ").replace(" ", "/"));

				model.addRow(songAttributes.toArray());
				setChartCountLabel(model);
			}
		} catch (SQLException ex) {
			Logger.getLogger(CheckboxModifier.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Append sql.
	 *
	 * @param numOfCheckedBoxes
	 *            the num of checked boxes
	 * @param SQLStatement
	 *            the SQL statement
	 * @param SQLToAdd
	 *            the SQL to add
	 * @return the string
	 */
	private static String appendSQL(int numOfCheckedBoxes, String SQLStatement,
			String SQLToAdd) {
		System.out.println("CheckboxModifier.appendSQL(" + numOfCheckedBoxes
				+ ", " + SQLStatement + ", " + SQLToAdd + ")");
		if (numOfCheckedBoxes < 2) {
			SQLStatement = SQLToAdd;
		}
		if (numOfCheckedBoxes > 1) {
			SQLStatement = SQLStatement + " OR " + SQLToAdd;
		}
		return SQLStatement;
	}

	/**
	 * Locate rss charts.
	 */
	public static void locateRssCharts() {
		System.out.println("CheckboxModifier.rssChartLocation()");
		String SQLStatement = "";
		notCheckedBoxes = new ArrayList<String>();
		int numOfCheckedBoxes = 0;
		if (!(RecordsMain.getUI().BillboardCheckbox.isSelected())) {
			notCheckedBoxes.add("Billboard-Pop");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Billboard-Pop%'");
		}
		if (!(RecordsMain.getUI().BillboardEDMCheckbox.isSelected())) {
			notCheckedBoxes.add("Billboard-EDM");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Billboard-EDM%'");
		}
		if (!(RecordsMain.getUI().BillboardCountryCheckbox.isSelected())) {
			notCheckedBoxes.add("Billboard-Country");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Billboard-Country%'");
		}
		if (!(RecordsMain.getUI().BillboardRnBCheckbox.isSelected())) {
			notCheckedBoxes.add("Billboard-RnB");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Billboard-RnB%'");
		}
		if (!(RecordsMain.getUI().HypeCheckbox.isSelected())) {
			notCheckedBoxes.add("Hypem");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Hypem%'");
		}

		if (!(RecordsMain.getUI().ItunesCheckbox.isSelected())) {
			notCheckedBoxes.add("Itunes-Pop");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Itunes-Pop%'");
		}
		if (!(RecordsMain.getUI().ItunesCountryCheckbox.isSelected())) {
			notCheckedBoxes.add("Itunes-Country");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Itunes-Country%'");
		}
		if (!(RecordsMain.getUI().ItunesRnBCheckbox.isSelected())) {
			notCheckedBoxes.add("Itunes-RnB");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Itunes-RnB%'");
		}
		if (!(RecordsMain.getUI().ItunesEDMCheckbox.isSelected())) {
			notCheckedBoxes.add("Itunes-EDM");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Itunes-EDM%'");
		}
		if (!(RecordsMain.getUI().UKCheckbox.isSelected())) {
			notCheckedBoxes.add("UK40");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%UK40%'");
		}
		if (!(RecordsMain.getUI().ShazamCheckbox.isSelected())) {
			notCheckedBoxes.add("Shazam");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Shazam%'");
		}
		if (!(RecordsMain.getUI().BeatportCheckbox.isSelected())) {
			notCheckedBoxes.add("Beatport");
		} else {
			numOfCheckedBoxes++;
			SQLStatement = appendSQL(numOfCheckedBoxes, SQLStatement,
					"songs.Location LIKE '%Beatport%'");
		}

		DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI().rssChartsTable
				.getModel();
		if (!(numOfCheckedBoxes > 0)) {
			removeRows(model);
		} else {
			addRows(model, SQLStatement);
		}
	}

	/**
	 * Removes the rows.
	 *
	 * @param model
	 *            the model
	 */
	private static void removeRows(DefaultTableModel model) {
		System.out.println("CheckboxModifier.removeRows(" + model + ")");
		while (model.getRowCount() > 0) {
			model.removeRow(0);
			setChartCountLabel(model);
		}
	}

  /**
   * Sets the chart count label.
   *
   * @param model the new chart count label
   */
  private static void setChartCountLabel(DefaultTableModel model) {
    if (RecordsMain.getUI().tabbedPane_1.getSelectedIndex() == 1) {
    	RecordsMain.getUI().BasedOn.setVisible(false);
    	if (model.getRowCount() == 1) {
    		RecordsMain.getUI().chartSongNum.setText(model
    				.getRowCount() + " Song");
    	} else {
    		RecordsMain.getUI().chartSongNum.setText(model
    				.getRowCount() + " Songs");
    	}
    } else {
    	RecordsMain.getUI().BasedOn.setVisible(true);
    	DefaultTableModel model2 = (DefaultTableModel) RecordsMain
    			.getUI().songPrefTable.getModel();
    	if (model.getRowCount() == 1) {
    		RecordsMain.getUI().chartSongNum.setText(model2
    				.getRowCount() + " Song");
    	} else {
    		RecordsMain.getUI().chartSongNum.setText(model2
    				.getRowCount() + " Songs");
    	}
    }
  }

	/** The not checked boxes. */
	private static ArrayList<String> notCheckedBoxes;
}

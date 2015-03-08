/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import java.util.ArrayList;
import java.util.HashMap;

import Records.Main.RecordsMain;

/**
 * The Class DuplicateChecker.
 *
 * @author Alexander Wilson
 */
public class DuplicateChecker {

  /**
   * Gets the values.
   *
   * @param Title          the title
   * @param Artist          the artist
   * @param byPassDuplicate the by pass duplicate
   * @return the values
   */
   static ArrayList<String> getValues(String location, String Title, String Artist) {
    System.out.println("DuplicateEntry.getValues(" + Title + ", " + Artist + ")");
    ArrayList<String> valSet;
    if (isDuplicate(Title, Artist)) {
      valSet = null;
    } else {
      valSet = EchonestSongAttributes.retrieveEchoInfo(Artist, Title);
    }
    return valSet;
  }

  /**
   * Checks if is duplicate.
   *
   * @param Title          the title
   * @param Artist          the artist
   * @param byPass the by pass
   * @return true, if is duplicate
   */
  static boolean isDuplicate(String Title, String Artist) {
    System.out.println("DuplicateEntry.isDuplicate(" + Title + ", " + Artist + ")");
      if (RecordsMain.getFeedSongHash().containsKey(Title + "," + Artist)) {
        System.out.println("----Duplicate found----");
        return true;
      } else {
        System.out.println("----New Entry----");
        return false;
      }
  }

}

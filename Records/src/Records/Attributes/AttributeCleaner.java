package Records.Attributes;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.commons.lang.WordUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class AttributeCleaner.
 *
 * @author Alexander
 */
public class AttributeCleaner {

  /**
   * Capitalize.
   *
   * @param source
   *          the source
   * @return the string
   */
  private static String capitalize(String source) {
    System.out.println("AttributeCleaner.capitalize(" + source + ")");
    String text = WordUtils.capitalize(source);
    return text.trim();
  }

  /**
   * Clean
   *
   * @param word
   *          the word
   * @return the string
   */
  static String clean(String word) {
    System.out.println("AttributeCleaner.clean(" + word + ")");
    return word.replace("'", "").replaceAll(";", "");
  }

  /**
   * Clean artist.
   *
   * @param artist
   *          the artist
   * @return the string
   */
  private static String cleanArtist(String artist) {
    System.out.println("AttributeCleaner.cleanArtist(" + artist + ")");
    artist = artist.toLowerCase().replace("~", "").replaceAll("/*", "").replaceAll("å", "a").replaceAll("'", "")
        .replaceAll("-", "").replace("á", "a").replaceAll("é", "e").replaceAll("í", "i")
        .replace("ó", "o").replaceAll("ú", "u").replaceAll("ü", "u").replaceAll("ñ", "n");

    if (artist.contains(" featuring ")) {
      artist = artist.substring(0, artist.indexOf("featuring")).replace("(", "").replace("[", "");
    } else if (artist.contains(" with ")) {
      artist = artist.substring(0, artist.indexOf(" with ")).replace("(", "").replace("[", "");
    } else if (artist.contains(" feat ")) {
      artist = artist.substring(0, artist.indexOf("feat")).replace("(", "").replace("[", "");
    } else if (artist.contains("feat.")) {
      artist = artist.substring(0, artist.indexOf("feat.")).replace("(", "").replace("[", "");
    } else if (artist.contains("ft.")) {
      artist = artist.substring(0, artist.indexOf("ft.")).replace("(", "").replace("[", "");
    } else if (artist.contains("vs.")) {
      artist = artist.substring(0, artist.indexOf("vs.")).replace("(", "").replace("[", "");
    } else if (artist.contains("vs")) {
      artist = artist.substring(0, artist.indexOf("vs")).replace("(", "").replace("[", "");
    } else if (artist.contains("&")) {
      artist = artist.substring(0, artist.indexOf("&")).replace("(", "").replace("[", "");
    } else if (artist.contains(",")) {
      artist = artist.substring(0, artist.indexOf(",")).replace("(", "").replace("[", "");
    }
    if ((!artist.contains("(")) && (artist.contains(")"))) {
      artist = artist.replace(")", "");
    }
    return capitalize(artist).trim();
  }

  /**
   * Clean attribute.
   *
   * @param type
   *          the type
   * @param attribute
   *          the attribute
   * @return the string
   */
  static String cleanAttribute(String type, String attribute) {
    System.out.println("AttributeCleaner.cleanAttribute(" + type + "," + attribute + ")");

    if (!isPureAscii(attribute) || attribute.toLowerCase().contains("feat")
        || attribute.toLowerCase().contains("featuring") || attribute.toLowerCase().contains("ft")
        || attribute.toLowerCase().contains("&") || attribute.toLowerCase().contains(",")
        || attribute.toLowerCase().contains("vs")) {
      if (type.equals("Artist")) {
        attribute = cleanArtist(attribute);
      } else if (type.equals("Title")) {
        attribute = cleanTitle(attribute);
      }
    }
    return attribute.trim();
  }

  /**
   * Clean title.
   *
   * @param title
   *          the title
   * @return the string
   */
  private static String cleanTitle(String title) {
    System.out.println("AttributeCleaner.cleanTitle(" + title + ")");

    title = title.toLowerCase().replace("~", "").replaceFirst(":", "").replaceFirst(",", "").replace("á", "a")
        .replaceAll("é", "e").replaceAll("í", "i").replace("ó", "o").replaceAll("ú", "u")
        .replaceAll("ü", "u").replaceAll("ñ", "n").replaceAll("'", "");
    try {
      title = title.substring(0, title.indexOf("feat"));
      title = title.replace("(", "").replace("[", "");
    } catch (Exception ex) {
    }
    try {
      title = title.substring(0, title.indexOf("feat."));
      title = title.replace("(", "").replace("[", "");
    } catch (Exception ex) {
    }
    try {
      title = title.substring(0, title.indexOf("ft."));
      title = title.replace("(", "").replace("[", "");
    } catch (Exception ex) {
    }
    title = capitalize(title).trim();
    if ((!title.contains("(")) && (title.contains(")"))) {
      title = title.replace(")", "");
    }
    return title;
  }

  /**
   * Checks if is pure ascii.
   *
   * @param v
   *          the v
   * @return true, if is pure ascii
   */
  private static boolean isPureAscii(String v) {
    System.out.println("AttributeCleaner.isPureAscii(" + v + ")");
    return asciiEncoder.canEncode(v);
  }

  /**
   * Strip perenthesis.
   *
   * @param titleLine
   *          the title line
   * @return the string
   */
  public static String stripParenthesis(String titleLine) {
    System.out.println("EchonestSongAttributes.stripPerenthesis(" + titleLine + ")");
    titleLine = checkPerenthesis(titleLine);
    String newTitle = titleLine;
    if (!(titleLine.contains("remix)") || titleLine.contains("Remix)")
        || titleLine.contains("edit)") || titleLine.contains("mashup)"))) {
      String removeString = titleLine.substring(titleLine.indexOf("("), titleLine.indexOf(")"));
      System.out.println(removeString);
      newTitle = titleLine.replace(removeString, "").replace(")", "").trim();
    }
    System.out.println("stripPerenthesis = " + newTitle);
    return newTitle;
  }

  public static String checkPerenthesis(String attribute) {
    System.out.println("EchonestSongAttributes.checkPerenthesis(" + attribute + ")");
    if (attribute.contains("(") && !(attribute.contains(")"))) {
      if ((attribute.contains("remix"))) {
        attribute = attribute.replace("remix", "remix)");
      } else if (attribute.contains("Remix")) {
        attribute = attribute.replace("Remix", "Remix)");
      } else if (attribute.contains("edit")) {
        attribute = attribute.replace("edit", "edit)");
      } else if (attribute.contains("mashup")) {
        attribute = attribute.replace("mashup", "mashup)");
      } else {
        attribute = attribute.replace("(", "");
      }
    }

    System.out.println("checkPerenthesis = " + attribute);
    return attribute;
  }

  /** The ascii encoder. */
  private static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
}

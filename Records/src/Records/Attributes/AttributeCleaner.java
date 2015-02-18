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
   * Clean.
   *
   * @param word
   *          the word
   * @return the string
   */
  public static String clean(String word) {
    System.out.println("AttributeCleaner.clean(" + word + ")");
    return word.replaceAll("〜", "").replace("'", "").replaceAll(";", "");
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
    artist = artist.toLowerCase().replaceAll("/*", "").replaceAll("å", "a").replaceAll("'", "")
        .replaceAll("-", " ").replace("á", "a").replaceAll("é", "e").replaceAll("í", "i")
        .replace("ó", "o").replaceAll("ú", "u").replaceAll("ü", "u").replaceAll("ñ", "n");
    try {
      artist = artist.substring(0, artist.indexOf("featuring")).replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
    }
    try {
      artist = artist.substring(0, artist.indexOf(" with "));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
    }
    try {
      artist = artist.substring(0, artist.indexOf("feat"));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
    }
    try {
      artist = artist.substring(0, artist.indexOf("feat."));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
    }
    try {
      artist = artist.substring(0, artist.indexOf("ft."));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
    }
    try {
      artist = artist.substring(0, artist.indexOf("vs"));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
    }
    try {
      artist = artist.substring(0, artist.indexOf("vs."));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
    }
    try {
      artist = artist.substring(0, artist.indexOf("&"));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
      }
    try {
      artist = artist.substring(0, artist.indexOf(","));
      artist = artist.replace("(", "").replace("[", "");
    } catch (Exception ex) {
      ex.getMessage();
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
  public static String cleanAttribute(String type, String attribute) {
    System.out.println("AttributeCleaner.cleanAttribute(" + type + "," + attribute + ")");
    if (!isPureAscii(attribute) || attribute.toLowerCase().contains("feat")
        || attribute.toLowerCase().contains("featuring") || attribute.toLowerCase().contains("ft")
        || attribute.toLowerCase().contains("&") || attribute.toLowerCase().contains(",")) {
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

    title = title.toLowerCase().replaceFirst(":", "").replaceFirst(",", "").replace("á", "a")
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

  /** The ascii encoder. */
  private static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder(); // or
                                                                                         // "ISO-8859-1"
                                                                                         // for ISO
                                                                                         // Latin 1
}

package Records.Attributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
// TODO: Auto-generated Javadoc
/**
 * The Class WebsiteDataExtractor.
 *
 * @author Alexander
 */
class WebsiteDataExtractor {

  /**
   * Gets the track nodes.
   *
   * @param key
   *          the key
   * @param UrlPath
   *          the url path
   * @param xpathTitle
   *          the xpath title
   * @param xpathArtist
   *          the xpath artist
   * @param x
   *          the x
   * @return the track nodes
   * @throws MalformedURLException
   *           the malformed url exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException
   *           the parser configuration exception
   * @throws SAXException
   *           the SAX exception
   * @throws XPathExpressionException
   *           the x path expression exception
   */
  private static NodeList getTrackNodes(org.w3c.dom.Document document, String UrlPath, String path) {
    System.out.println("FeedDataSaver.getTrackNodes("+UrlPath + "," + path + ")");
    NodeList trackNameNodes = null;
    try {
      XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
      XPath xPathEvaluator = XPATH_FACTORY.newXPath();
      XPathExpression nameExpr = xPathEvaluator.compile(path);
      trackNameNodes = (NodeList) nameExpr.evaluate(document, XPathConstants.NODESET);
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    return trackNameNodes;
  }

  /**
   * Generate document.
   *
   * @param UrlPath
   *          the url path
   * @param key
   *          the key
   * @return the document
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException
   *           the parser configuration exception
   * @throws SAXException
   *           the SAX exception
   */
  private static org.w3c.dom.Document generateDocument(String UrlPath) throws java.io.IOException,javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException {
    System.out.println("FeedDataSaver.generateDocument("+ UrlPath + ")");
    URL url = new URL(UrlPath);
    DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    URLConnection connection = url.openConnection();
    DocumentBuilder db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
    org.w3c.dom.Document document = null;
    try {
      document = db.parse(connection.getInputStream());
    } catch (java.net.SocketException ex) {
      ex.printStackTrace();
    }
    return document;
  }
  
  /**
   * Extract web data to array.
   *
   * @param site
   *          the site
   * @param jsonDocSelect
   *          the doc select
   * @param jsonDocAttribute
   *          the doc attribute
   * @param numOfSongs
   *          the num of songs
   * @param jsonToken
   *          the token
   * @param location
   *          the location
   * @return the string[][]
   */
  static String[][] extractWebDataToArray(String site, String jsonDocSelect, String jsonDocAttribute,int numOfSongs, String jsonToken, String location, String InType, int numVar, String xPathTitle, String xPathArtist) {
    System.out.println("WebsiteDataExtractor.webExtractToArray(" + site + ", " + jsonDocSelect + ", "+ jsonDocAttribute + ", " + numOfSongs + ", " + jsonToken + ", " + location + ")");
    if (InType.equals("XML")) {
      TopRss = new String[numOfSongs][2];
      org.w3c.dom.Document document = null;
      try {
        document = generateDocument(site);
      } catch (IOException | ParserConfigurationException | SAXException e) {
        e.printStackTrace();
      }
      
      for (int i = 0; i < numOfSongs; i++) {
        System.out.println("SAVE from XML");
        String part1 = "";
        String part2 = "";
        if (numVar != 1) {
          //title
          part1 = getTrackNodes(document, site, xPathTitle).item(i).getTextContent().replace(" - ", "~").replace((i + 1) + ":", "").trim();
          part2 = getTrackNodes(document, site, xPathArtist).item(i).getTextContent().replace(" - ", "~").replace((i + 1) + ":", "").trim();
        } else {
          String trackString = getTrackNodes(document, site, xPathTitle).item(i).getTextContent().replace(" - ", "~").replace((i + 1) + ":", "").trim();
          //Order.equals("Artist_Title")
            part2 = trackString.substring(0, trackString.indexOf("~")).replace("~", "");
            part1 = trackString.replace(part2, "");
        }
        TopRss[i][0] = part1;
        TopRss[i][1] = part2;
      }
    }
    else if(InType.equals("JSON")){
    int counter = 0;
    Document doc = getJSONDoc(site, jsonDocSelect, jsonDocAttribute, numOfSongs, jsonToken, location);
    if (doc != null) {
      Elements metalinks = doc.select(jsonDocSelect);
      TopRss = new String[numOfSongs][2];
      for (Element link : metalinks) {
        String titleArtistString;
        if ("Beatport".equals(location)) {
          titleArtistString = parseBeatportJson(link.attr(jsonDocAttribute));
        } else {
          titleArtistString = link.attr(jsonDocAttribute);
        }
        StringTokenizer stringtokenizer = new StringTokenizer(titleArtistString, jsonToken);
        if (stringtokenizer.hasMoreTokens()) {
          TopRss[counter][0] = stringtokenizer.nextToken();
          TopRss[counter][1] = stringtokenizer.nextToken();
        }
        counter++;
      }
    }
    }
    return TopRss;
  }

  /**
   * @param site
   * @param docSelect
   * @param docAttribute
   * @param numOfSongs
   * @param token
   * @param location
   * @return
   */
  private static Document getJSONDoc(String site, String docSelect, String docAttribute,int numOfSongs, String token, String location) {
    Document doc = null;
    try {
      doc = Jsoup.connect(site).get();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return doc;
  }

  /**
   * Parses the beatport json.
   *
   * @param jsonInput
   *          the json input
   * @return the string
   */
  private static String parseBeatportJson(String jsonInput) {
    System.out.println("WebsiteDataExtractor.parseBeatportJson(" + jsonInput + ")");

    String title = null;
    String artist = null;
    try {
      JSONObject outerObject = new JSONObject(jsonInput);
      title = outerObject.getString("name");
      JSONArray jsonArray = outerObject.getJSONArray("artists");
      JSONObject objectInArray = jsonArray.getJSONObject(0);
      artist = objectInArray.getString("name");
    } catch (JSONException ex) {
      Logger.getLogger(WebsiteDataExtractor.class.getName()).log(Level.SEVERE, null, ex);
    }
    return title + " - " + artist;
  }

  /** The Top rss. */
  private static String[][] TopRss;
}

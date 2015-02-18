package Records.Attributes;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The Class AlbumArtRetriever.
 *
 * @author Alexander
 */
public class AlbumArtRetriever {

	/**
	 * Art opt one.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @return the string
	 */
	private static String artOptOne(String title, String artist) {
		System.out.println("AlbumArtRetriever.artOptOne(" + title + ", "+ artist + ")");
		String albArt = "";
		try {
			String path = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=b379fee6480a2478ba7aa5d3faa2699c&artist="
					+ artist + "&track=" + title;
			URL url = new URL(path);
			URLConnection connection = url.openConnection();
			DocumentBuilder db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
			final Document document = db.parse(connection.getInputStream());
			XPath xPathEvaluator;
			xPathEvaluator = XPATH_FACTORY.newXPath();
			XPathExpression nameExpr;
			nameExpr = xPathEvaluator.compile("lfm/track/album/image");
			NodeList trackNameNodes = (NodeList) nameExpr.evaluate(document,
					XPathConstants.NODESET);
			for (int i = 0; i < trackNameNodes.getLength(); i++) {
				Node trackNameNode = trackNameNodes.item(i);
				String b = trackNameNode.getTextContent().trim();
				albArt = b;
				albArt = albArt.replaceAll("'", "");
				albArt = albArt.replaceAll("\n+", "");
				albArt = albArt.replaceAll("&quot;", "");
				albArt = albArt.replaceAll("&amp; ", "");
				albArt = albArt.replaceAll("\\&lt;.*?\\&gt;", "");
			}
		} catch (IOException ex) {
			System.err.println("AlbumArtRetriever option 1 -  IOException");
			albArt = "";
		} catch (ParserConfigurationException ex) {
			System.err.println("AlbumArtRetriever option 1 - ParserConfigurationException");
			albArt = "";
		} catch (SAXException ex) {
			System.err.println("AlbumArtRetriever option 1 -SAXException");
			albArt = "";
		} catch (XPathExpressionException ex) {
			System.err.println("AlbumArtRetriever option 1 - XPathExpressionException");
			albArt = "";
		}
		return albArt;
	}

	/**
	 * Art opt two.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @return the string
	 */
	private static String artOptTwo(String title, String artist) {
		System.out.println("AlbumArtRetriever.artOptTwo(" + title + ", "
				+ artist + ")");
		String albArt = "";
		BufferedReader bufferedReader = null;
		String ArtID = "";
		try {
			String path = "http://www.musicbrainz.org/ws/2/recording/?query=artist:"
					+ artist + "+recording:" + title;
			URL url = new URL(path);
			URLConnection connection = url.openConnection();
			DocumentBuilder db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
			final Document document = db.parse(connection.getInputStream());
			XPath xPathEvaluator;
			xPathEvaluator = XPATH_FACTORY.newXPath();
			XPathExpression nameExpr;
			nameExpr = xPathEvaluator
					.compile("metadata/recording-list/recording/release-list/release");
			NodeList trackNameNodes = (NodeList) nameExpr.evaluate(document,
					XPathConstants.NODESET);
			Node trackNameNode = trackNameNodes.item(0);
			String b = trackNameNode.getTextContent().trim();
			ArtID = b;
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(
					"http://coverartarchive.org/release/" + ArtID)
					.openConnection().getInputStream()));
			String picLine;
			while ((picLine = bufferedReader.readLine()) != null) {
				art = picLine;
			}
			bufferedReader.close();
			String newStr = art.substring(art.indexOf("large"),
					art.indexOf("small"));
			newStr = newStr.substring(newStr.indexOf(":"), newStr.indexOf(","));
			newStr = newStr.replace("\"", "");
			newStr = newStr.replaceFirst(":", "");
			albArt = newStr;
		} catch (IOException ex) {
			System.err.println("AlbumArtRetriever option 2 -  IOException");
			albArt = "";
		} catch (ParserConfigurationException ex) {
			System.err
					.println("AlbumArtRetriever option 2 - ParserConfigurationException");
			albArt = "";
		} catch (SAXException ex) {
			System.err.println("AlbumArtRetriever option 2 -SAXException");
			albArt = "";
		} catch (XPathExpressionException ex) {
			System.err
					.println("AlbumArtRetriever option 2 - XPathExpressionException");
			albArt = "";
		}
		return albArt;
	}

	/**
	 * Creates the directory.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @param album
	 *            the album
	 * @return the string
	 */
	public static String createDirectory(String title, String artist, String album) {
		String image;
		File dir = new File(
				"C:\\Users\\Alexander\\Documents\\MuRec\\ItunesMediaDatabase\\Images\\"
						+ artist + "\\" + album);
		BufferedImage img;
		if (dir.listFiles() != null) {
			image = "C:\\Users\\Alexander\\Documents\\MuRec\\ItunesMediaDatabase\\Images\\"
					+ artist + "\\" + album + "\\" + album + ".jpg";
		} else {
			image = AlbumArtRetriever.retrieveArt(title, artist);
			if ("Unknown".equals(image) || image == null) {
				image = "C:\\Users\\Alexander\\MuRec\\no_img.gif";
			} else {
				try {
					img = ImageIO.read(new URL(image));
					System.out.println(img);
					if (img != null) {
						saveURLImage(image, album, artist);
						image = "C:\\Users\\Alexander\\Documents\\MuRec\\ItunesMediaDatabase\\Images\\"
								+ artist + "\\" + album + "\\" + album + ".jpg";
					}
				} catch (IOException ex) {
					Logger.getLogger(AlbumArtRetriever.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
		return image;
	}

	/**
	 * Retrieve art.
	 *
	 * @param title
	 *            the title
	 * @param artist
	 *            the artist
	 * @return the string
	 */
	private static String retrieveArt(String title, String artist) {
		System.out.println("AlbumArtRetriever.AlbumArt(" + title + ", "
				+ artist + ")");

		title = title.replaceAll(" ", "%20");
		artist = artist.replaceAll(" ", "%20");
		art = artOptOne(title, artist);
		if ("".equals(art)) {
			art = artOptTwo(title, artist);
			if ("".equals(art)) {
				art = "Unknown";
			}
		}
		return art;
	}

	/**
	 * Save url image.
	 *
	 * @param imageUrl
	 *            the image url
	 * @param album
	 *            the album
	 * @param artist
	 *            the artist
	 */
	private static void saveURLImage(String imageUrl, String album,
			String artist) {
		try {
			FileUtils.forceMkdir(new File(
					"C:\\Users\\Alexander\\Documents\\MuRec\\ItunesMediaDatabase\\Images\\"
							+ artist + "\\" + album));
			String destinationFile = "C:\\Users\\Alexander\\Documents\\MuRec\\ItunesMediaDatabase\\Images\\"
					+ artist + "\\" + album + "\\" + album + ".jpg";
			URL url = new URL(imageUrl);
			OutputStream os;
			try (InputStream is = url.openStream()) {
				os = new FileOutputStream(destinationFile);
				byte[] b = new byte[2048];
				int length;
				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
				os.close();
			} catch (IOException ex) {
				Logger.getLogger(AlbumArtRetriever.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		} catch (MalformedURLException ex) {
			Logger.getLogger(AlbumArtRetriever.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(AlbumArtRetriever.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/** The art. */
	private static String art = "";

	/** The Constant DOCUMENT_BUILDER_FACTORY. */
	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

	/** The Constant XPATH_FACTORY. */
	private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
}

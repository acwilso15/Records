package Records.Attributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryMetadataExtractor.
 */
public class LibraryMetadataExtractor {

	/**
	 * Gets the album.
	 *
	 * @param v2tag
	 *            the v2tag
	 * @param tag
	 *            the tag
	 * @param valSet
	 *            the val set
	 * @return the album
	 */
	public static String getAlbum(AbstractID3v2Tag v2tag, Tag tag,
			ArrayList<String> valSet) {
		System.out.println("LibraryMetadataExtractor.getAlbum()");
		String album;
		if (tag == null) {
			// get artist from v2Tag
			album = v2tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
		} else {
			// get artist from tag
			album = tag.getFirst(FieldKey.ALBUM);
		}
		if (album.equals("")) {
			album = AttributeCleaner.clean(valSet.get(7));
		}
		return album.replace("'", "").replace(",", "");
	}

	/**
	 * Gets the artist.
	 *
	 * @param v2tag
	 *            the v2tag
	 * @param tag
	 *            the tag
	 * @return the artist
	 */
	public static String getArtist(AbstractID3v2Tag v2tag, Tag tag) {
		System.out.println("LibraryMetadataExtractor.getArtist()");
		String artist;
		if (tag == null) {
			// get artist from v2Tag
			artist = v2tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
		} else {
			// get artist from tag
			artist = tag.getFirst(FieldKey.ARTIST);
		}
		return artist.replace("'", "").replace(",", "");
	}

	/**
	 * Gets the bpm.
	 *
	 * @param v2tag
	 *            the v2tag
	 * @param tag
	 *            the tag
	 * @param valSet
	 *            the val set
	 * @return the bpm
	 */
	public static String getBPM(AbstractID3v2Tag v2tag, Tag tag,
			ArrayList<String> valSet) {
		System.out.println("LibraryMetadataExtractor.getBPM()");
		String BPM;
		if (tag == null) {
			BPM = EchonestSongAttributes.addZeros(v2tag
					.getFirst(ID3v24Frames.FRAME_ID_BPM));
		} else {
			BPM = EchonestSongAttributes.addZeros(tag.getFirst(FieldKey.BPM));
		}
		if (BPM.equals("")) {
			BPM = AttributeCleaner.clean(valSet.get(2));
		}
		return BPM;
	}

	/**
	 * Gets the genre.
	 *
	 * @param v2tag
	 *            the v2tag
	 * @param tag
	 *            the tag
	 * @param valSet
	 *            the val set
	 * @return the genre
	 */
	public static String getGenre(AbstractID3v2Tag v2tag, Tag tag,
			ArrayList<String> valSet) {
		System.out.println("LibraryMetadataExtractor.getGenre()");
		String genre;
		if (tag == null) {
			// get genre from v2Tag
			genre = v2tag.getFirst(ID3v24Frames.FRAME_ID_GENRE);
		} else {
			// get genre from tag
			genre = tag.getFirst(FieldKey.GENRE);
		}
		if (genre.equals("")) {
			genre = AttributeCleaner.clean(valSet.get(8));
		}
		return genre;
	}

	/**
	 * Gets the key.
	 *
	 * @param v2tag
	 *            the v2tag
	 * @param tag
	 *            the tag
	 * @param valSet
	 *            the val set
	 * @return the key
	 */
	public static String getKey(AbstractID3v2Tag v2tag, Tag tag,
			ArrayList<String> valSet) {
		System.out.println("LibraryMetadataExtractor.getKey()");
		String key;
		if (tag == null) {
			key = v2tag.getFirst(ID3v24Frames.FRAME_ID_INITIAL_KEY).replace(
					"m", "");
		} else {
			key = tag.getFirst(FieldKey.KEY).replace("m", "");
		}
		if (key.equals("")) {
			key = AttributeCleaner.clean(valSet.get(3));
		}
		return key;
	}

	/**
	 * Gets the title.
	 *
	 * @param v2tag
	 *            the v2tag
	 * @param tag
	 *            the tag
	 * @return the title
	 */
	public static String getTitle(AbstractID3v2Tag v2tag, Tag tag) {
		System.out.println("LibraryMetadataExtractor.getTitle()");
		String title;
		if (tag == null) {
			// get Title from v2Tag
			title = v2tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
		} else {
			// get Title from tag
			title = tag.getFirst(FieldKey.TITLE);
		}
		return title.replace("'", "").replace(",", "");
	}

	// TODO Remove unused code found by UCDetector
	// public static String getMP3Image(String path, String album, String
	// artist,
	// String title) {
	// System.out.println("LibraryMetadataExtractor.getMP3Image()");
	// String image = "";
	// Mp3File song;
	// try {
	// song = new Mp3File(path);
	// ID3v2 id3v2tag = song.getId3v2Tag();
	// byte[] imData = id3v2tag.getAlbumImage();
	// AlbumArtRetriever.saveLibraryImage(imData, album, artist, title);
	// } catch (IOException | UnsupportedTagException | InvalidDataException ex)
	// {
	// image = AlbumArtRetriever.createDirectory(title, artist, album);
	// }
	// return image;
	// }

	/**
	 * Sets the m p3 file.
	 *
	 * @param song
	 *            the song
	 * @return the m p3 file
	 */
	public static MP3File setMP3File(File song) {
		System.out.println("LibraryMetadataExtractor.setMP3File("
				+ song.getAbsolutePath() + ")");
		MP3File g = null;
		try {
			g = (MP3File) AudioFileIO.read(song);

		} catch (CannotReadException | IOException | TagException
				| ReadOnlyFileException | InvalidAudioFrameException ex) {
			Logger.getLogger(LibraryMetadataExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return g;
	}

	/**
	 * Sets the tag.
	 *
	 * @param song
	 *            the song
	 * @return the tag
	 */
	public static Tag setTag(File song) {
		System.out.println("LibraryMetadataExtractor.setTag("
				+ song.getAbsolutePath() + ")");

		Tag tag = null;
		try {
			tag = AudioFileIO.read(song).getTag();

		} catch (CannotReadException | IOException | TagException
				| ReadOnlyFileException | InvalidAudioFrameException ex) {
			Logger.getLogger(LibraryMetadataExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return tag;
	}

	/**
	 * Setv2 tag.
	 *
	 * @param mp3
	 *            the mp3
	 * @return the abstract i d3v2 tag
	 */
	public static AbstractID3v2Tag setv2Tag(MP3File mp3) {
		System.out.println("LibraryMetadataExtractor.setv2Tag()");
		AbstractID3v2Tag v2tag = null;
		if (mp3 != null) {
			v2tag = mp3.getID3v2Tag();
		}
		return v2tag;
	}

	/** The library metadata extractor product. */
	private LibraryMetadataExtractorProduct libraryMetadataExtractorProduct = new LibraryMetadataExtractorProduct();

	/** The Location. */
	private static String Location = "Directory";

	/**
	 * Instantiates a new library metadata extractor.
	 *
	 * @param f
	 *            the f
	 */
	public LibraryMetadataExtractor(File f) {
		libraryMetadataExtractorProduct.setSongFile(f);
	}

	/**
	 * Extract.
	 *
	 * @return the array list
	 */
	public ArrayList<String> extract() {
		return libraryMetadataExtractorProduct.extract();
	}
}

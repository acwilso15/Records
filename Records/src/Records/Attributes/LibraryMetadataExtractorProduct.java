package Records.Attributes;

import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryMetadataExtractorProduct.
 */
class LibraryMetadataExtractorProduct {
	
	/** The song file. */
	private File songFile;

	/**
	 * Extract.
	 * 
	 * @return the array list
	 */
	public ArrayList<String> extract() {
		System.out.println("LibraryMetadataExtractor.extract()");
		ArrayList<String> set = new ArrayList<String>();
		if (FilenameUtils.isExtension(songFile.getAbsolutePath(), "mp3")) {
			MP3File mp3 = LibraryMetadataExtractor.setMP3File(songFile);
			AbstractID3v2Tag ID3v2Tag = LibraryMetadataExtractor.setv2Tag(mp3);
			boolean	byPassDuplicate = true;
			String title = LibraryMetadataExtractor.getTitle(ID3v2Tag, null);
			String artist = LibraryMetadataExtractor.getArtist(ID3v2Tag, null);
			ArrayList<String> valSet = EchonestSongAttributes.retrieveEchoInfo(artist, title, byPassDuplicate);
			String album = LibraryMetadataExtractor.getAlbum(ID3v2Tag, null,
					valSet);
			set.add(title + "_" + artist + "_" + album);
			set.add(title);
			set.add(artist);
			set.add(album);
			set.add(LibraryMetadataExtractor.getGenre(ID3v2Tag, null, valSet));
			set.add(AttributeCleaner.clean(valSet.get(5)));
			set.add(AttributeCleaner.clean(valSet.get(4)));
			set.add(AttributeCleaner.clean(valSet.get(6)));
			set.add(LibraryMetadataExtractor.getBPM(ID3v2Tag, null, valSet));
			set.add(LibraryMetadataExtractor.getKey(ID3v2Tag, null, valSet));
			set.add(AlbumArtRetriever.createDirectory(title, artist, album));
			set.add(songFile.getAbsolutePath().replaceAll("'", "~"));
		} else if (FilenameUtils.isExtension(songFile.getAbsolutePath(), "m4a")) {
			Tag tag = LibraryMetadataExtractor.setTag(songFile);
			String title = LibraryMetadataExtractor.getTitle(null, tag)
					.replaceAll(",", "");
			String artist = LibraryMetadataExtractor.getArtist(null, tag);
			ArrayList<String> valSet = EchonestSongAttributes.retrieveEchoInfo(artist, title,
					true);
			String album = LibraryMetadataExtractor.getAlbum(null, tag, valSet);
			set.add(title + "_" + artist + "_" + album);
			set.add(title);
			set.add(artist);
			set.add(album);
			set.add(LibraryMetadataExtractor.getGenre(null, tag, valSet));
			set.add(AttributeCleaner.clean(valSet.get(5)));
			set.add(AttributeCleaner.clean(valSet.get(4)));
			set.add(AttributeCleaner.clean(valSet.get(6)));
			set.add(LibraryMetadataExtractor.getBPM(null, tag, valSet));
			set.add(LibraryMetadataExtractor.getKey(null, tag, valSet));
			set.add(AlbumArtRetriever.createDirectory(title, artist, album));
			set.add(songFile.getAbsolutePath().replaceAll("'", "~"));
		}
		return set;
	}

	/**
	 * Gets the song file.
	 *
	 * @return the song file
	 */
	public File getSongFile() {
		return songFile;
	}

	/**
	 * Sets the song file.
	 *
	 * @param songFile the new song file
	 */
	public void setSongFile(File songFile) {
		this.songFile = songFile;
	}
}
package cz.martlin.jmop.sourcery.locals.filesystem;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.sourcery.locals.abstracts.AbstractSingleTrackPerFileImporter;
import javafx.util.Duration;

/**
 * The single track per file importer, which works based on the MP3 file
 * metadata.
 * 
 * @author martin
 *
 */
public class MP3FileMetadataBasedImporter extends AbstractSingleTrackPerFileImporter {

	/**
	 * Should the track uri get extracted from the MP3 file, or use the MP3 file as
	 * that?
	 * 
	 */
	private final boolean useUriFromMetadata;

	public MP3FileMetadataBasedImporter(boolean useUriFromMetadata) {
		super();
		this.useUriFromMetadata = useUriFromMetadata;
	}

	@Override
	protected TrackData fileToTrack(File file) throws IOException {
		try {
			LOGGER.info("Loading MP3 file {}", file);
			Mp3File mp3file = new Mp3File(file);

			Duration duration = new Duration(mp3file.getLengthInMilliseconds());

			String title = null;
			String description = null;
			URI uri = null;

			if (mp3file.hasId3v1Tag()) {
				LOGGER.debug("The MP3 file has Id3V1 tag");
				ID3v1 tag = mp3file.getId3v1Tag();
				title = tag.getTitle();
				description = tag.getComment();
			}

			if (mp3file.hasId3v2Tag()) {
				LOGGER.debug("The MP3 file has Id3V2 tag");
				ID3v2 tag = mp3file.getId3v2Tag();
				title = tag.getTitle();
				description = tag.getComment();
				if (useUriFromMetadata) {
					uri = getUri(tag);
				}
			}

			if (title == null || title.isEmpty()) {
				LOGGER.warn("The MP3 file has no title, using file name");
				title = file.getName();
			}
			if (description == null) {
				LOGGER.warn("The MP3 file has no description, ignoring");
				description = null;
			}
			if (uri == null) {
				LOGGER.warn("The MP3 file has no URL, using the file location instead");
				uri = file.toURI();
			}

			return new TrackData(title, description, duration, uri, file);
		} catch (Exception e) {
			throw new IOException("Cannot load MP3 file data", e);
		}
	}

	/**
	 * Picks the uri of the tag. Returns nullable.
	 * 
	 * @param tag
	 * @return
	 */
	private URI getUri(ID3v2 tag) {
		String url = tag.getUrl();
		if (url == null) {
			return null;
		}
		return URI.create(url);
	}
}

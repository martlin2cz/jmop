package cz.martlin.jmop.sourcery.locals.abstracts;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;

/**
 * The abstract tracks-from-file importer, which assumes each file contains
 * exactly one track. Declares method for that.
 * 
 * @author martin
 *
 */
public abstract class AbstractSingleTrackPerFileImporter implements BaseTracksFromFileImporter {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractSingleTrackPerFileImporter.class);

	@Override
	public List<TrackData> importTracks(File file) throws IOException {
		LOGGER.info("Importing track from file {}", file.getAbsolutePath());
		try {
			TrackData track = fileToTrack(file);
			if (track != null) {
				return Collections.singletonList(track);
			} else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			LOGGER.error("The import form file {} failed", file.getAbsolutePath(), e);
			return Collections.emptyList();
		}
	}

	/**
	 * Loads the track data from the given file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	protected abstract TrackData fileToTrack(File file) throws IOException;

}
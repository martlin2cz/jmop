package cz.martlin.jmop.sourcery.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The component which imports tracks from specified file or directory. If
 * directory, can recurse and ignores files not in specified format.
 * 
 * @author martin
 *
 */
public interface BaseTracksFromDirOrFileImporter {

	/**
	 * Runs the import.
	 * 
	 * @param dirOrFile
	 * @param recursive
	 * @param filesFormat
	 * @return
	 * @throws IOException 
	 */
	List<TrackData> importTracks(File dirOrFile, boolean recursive, TrackFileFormat filesFormat) throws IOException;

}
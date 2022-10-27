package cz.martlin.jmop.sourcery.local;

import java.io.File;
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
public interface BaseTracksImpoter {

	/**
	 * Runs the import.
	 * 
	 * @param dirOrFile
	 * @param recursive
	 * @param filesFormat
	 * @return
	 */
	List<TrackData> importTracks(File dirOrFile, boolean recursive, TrackFileFormat filesFormat);

}
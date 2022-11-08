package cz.martlin.jmop.sourcery.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;

/**
 * The component which imports tracks from specified file.
 * 
 * @author martin
 *
 */
public interface BaseTracksFromFileImporter {

	/**
	 * Runs the import.
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	List<TrackData> importTracks(File file) throws IOException;

}
package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormatLocationPreparer;

/**
 * The base converter. Converts track file from one format and location into
 * another. Assuming each converter can convert from each
 * {@link TrackFileFormat} to each (if not, please throw exception).
 * 
 * The converter may generate some progress.
 * 
 * @see TrackFileFormatLocationPreparer
 * @author martin
 * 
 *
 */
@Deprecated
public interface XXX_BaseSourceConverter {

	/**
	 * Converts given track fron given input format at given input location into
	 * given output format to output location. The input and output format might
	 * not be the same.
	 * 
	 * @param track
	 * @param fromLocation
	 * @param fromFormat
	 * @param toLocation
	 * @param toFormat
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	boolean convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat, ProgressListener listener) throws Exception;

	/**
	 * Returns true if this converter is ready to be used (like no libraries are
	 * no needed to be installed).
	 * 
	 * @return
	 */
	public boolean check();


}

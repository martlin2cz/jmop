package cz.martlin.jmop.core.sources.locals;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseSourceConverter;

/**
 * An utility class for "not only converting". This is extension of
 * {@link BaseSourceConverter} for cases where only copy is needed (the input
 * and output format are the same).
 * 
 * @author martin
 *
 */
public class TrackFileFormatLocationPreparer {
	private final BaseLocalSource local;
	private final BaseSourceConverter converter;

	public TrackFileFormatLocationPreparer(BaseLocalSource local, BaseSourceConverter converter) {
		super();
		this.local = local;
		this.converter = converter;
	}

	/**
	 * Set listener.
	 * 
	 * @param progressListener
	 */
	public void specifyListener(ProgressListener progressListener) {
		this.converter.specifyListener(progressListener);
	}

	//////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Prepare track existing at given input location in given input format, to
	 * be existing at given output location and with given output format.
	 * 
	 * @param track
	 * @param fromFormat
	 * @param fromLocation
	 * @param toFormat
	 * @param toLocation
	 * @return
	 * @throws Exception
	 */
	public boolean prepare(Track track, TrackFileFormat fromFormat, TrackFileLocation fromLocation,
			TrackFileFormat toFormat, TrackFileLocation toLocation) throws Exception {

		boolean equalFormat = fromFormat.equals(toFormat);
		boolean equalLocation = fromLocation.equals(toLocation);

		if (equalFormat && equalLocation) {
			// okay, cool
			return true;
		}
		if (equalFormat && !equalLocation) {
			return justCopy(track, fromLocation, toLocation, fromFormat);
		}

		if (!equalFormat && equalLocation) {
			return justConvert(track, fromLocation, fromFormat, toFormat);
		}

		if (!equalFormat && !equalLocation) {
			return convertWithCopy(track, fromLocation, toLocation, fromFormat, toFormat);
		}

		return false;
	}

	/**
	 * Only copies from location to location with the given common format.
	 * 
	 * @param track
	 * @param fromLocation
	 * @param toLocation
	 * @param format
	 * @return
	 * @throws Exception
	 */
	private boolean justCopy(Track track, //
			TrackFileLocation fromLocation, TrackFileLocation toLocation, TrackFileFormat format) throws Exception {

		File fromFile = local.fileOfTrack(track, fromLocation, format);
		File toFile = local.fileOfTrack(track, toLocation, format);

		try {
			Files.copy(fromFile, toFile);
			return true;
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot copy track file", e);
		}
	}

	/**
	 * Only converts from format to format, at given common location.
	 * 
	 * @param track
	 * @param location
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @throws Exception
	 */
	private boolean justConvert(Track track, //
			TrackFileLocation location, TrackFileFormat fromFormat, TrackFileFormat toFormat) throws Exception {

		return converter.convert(track, location, fromFormat, location, toFormat);
	}

	/**
	 * Converts and also copies (in fact converts into different location).
	 * 
	 * @param track
	 * @param fromLocation
	 * @param toLocation
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @throws Exception
	 */
	private boolean convertWithCopy(Track track, //
			TrackFileLocation fromLocation, TrackFileLocation toLocation, //
			TrackFileFormat fromFormat, TrackFileFormat toFormat) throws Exception {

		return converter.convert(track, fromLocation, fromFormat, toLocation, toFormat);

	}

}

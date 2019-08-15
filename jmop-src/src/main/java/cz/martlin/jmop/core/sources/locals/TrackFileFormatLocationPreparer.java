package cz.martlin.jmop.core.sources.locals;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.BaseOperation;
import cz.martlin.jmop.core.misc.ops.EmptyOperation;
import cz.martlin.jmop.core.misc.ops.SimpleShortOperation;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.ConversionReason;

/**
 * An utility class for "not only converting". This is extension of
 * {@link BaseConverter} for cases where only copy is needed (the input and
 * output format are the same).
 * 
 * @author martin
 *
 */
public class TrackFileFormatLocationPreparer {
	private final BaseLocalSource local;
	private final BaseConverter converter;

	public TrackFileFormatLocationPreparer(BaseLocalSource local, BaseConverter converter) {
		super();
		this.local = local;
		this.converter = converter;
	}

	//////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Prepare track existing at given input location in given input format, to be
	 * existing at given output location and with given output format.
	 * 
	 * @param track
	 * @param fromFormat
	 * @param fromLocation
	 * @param toFormat
	 * @param toLocation
	 * @return
	 * @throws Exception
	 */
	public BaseOperation<Track, Track> prepare(Track track, ConversionReason reason, TrackFileFormat fromFormat,
			TrackFileLocation fromLocation, TrackFileFormat toFormat, TrackFileLocation toLocation) throws Exception {

		boolean equalFormat = fromFormat.equals(toFormat);
		boolean equalLocation = fromLocation.equals(toLocation);

		if (equalFormat && equalLocation) {
			// okay, cool
			return new EmptyOperation<Track>(track);
		}
		if (equalFormat && !equalLocation) {
			return justCopy(track, reason, fromLocation, toLocation, fromFormat);
		}

		if (!equalFormat && equalLocation) {
			return justConvert(track, reason, fromLocation, fromFormat, toFormat);
		}

		if (!equalFormat && !equalLocation) {
			return convertWithCopy(track, reason, fromLocation, toLocation, fromFormat, toFormat);
		}

		return null; // never happens
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
	private BaseOperation<Track, Track> justCopy(Track track, ConversionReason reason, //
			TrackFileLocation fromLocation, TrackFileLocation toLocation, TrackFileFormat format) throws Exception {

		String name = reason.getHumanName();
		return new SimpleShortOperation<Track, Track>(name, track, (t) -> t.toHumanString(), (t) -> {
			runCopy(t, fromLocation, toLocation, format);
			return t;
		});
	}

	private void runCopy(Track track, //
			TrackFileLocation fromLocation, TrackFileLocation toLocation, TrackFileFormat format)
			throws JMOPSourceException {

		File fromFile = local.fileOfTrack(track, fromLocation, format);
		File toFile = local.fileOfTrack(track, toLocation, format);

		try {
			Files.copy(fromFile, toFile);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot copy track file", e); //$NON-NLS-1$
		}
	}

	/**
	 * Only converts from format to format, at given common location.
	 * 
	 * @param track
	 * @param location
	 * @param fromFormat
	 * @param toFormat
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	private BaseOperation<Track, Track> justConvert(Track track, ConversionReason reason, //
			TrackFileLocation location, TrackFileFormat fromFormat, TrackFileFormat toFormat) throws Exception {

		return converter.convert(track, location, fromFormat, location, toFormat, reason);
	}

	/**
	 * Converts and also copies (in fact converts into different location).
	 * 
	 * @param track
	 * @param fromLocation
	 * @param toLocation
	 * @param fromFormat
	 * @param toFormat
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	private BaseOperation<Track, Track> convertWithCopy(Track track, ConversionReason reason, //
			TrackFileLocation fromLocation, TrackFileLocation toLocation, //
			TrackFileFormat fromFormat, TrackFileFormat toFormat) throws Exception {

		return converter.convert(track, fromLocation, fromFormat, toLocation, toFormat, reason);

	}

}

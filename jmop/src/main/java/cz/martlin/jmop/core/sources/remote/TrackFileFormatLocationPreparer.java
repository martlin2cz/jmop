package cz.martlin.jmop.core.sources.remote;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormat;

public class TrackFileFormatLocationPreparer {
	private final BaseLocalSource local;
	private final BaseSourceConverter converter;

	public TrackFileFormatLocationPreparer(BaseLocalSource local, BaseSourceConverter converter) {
		super();
		this.local = local;
		this.converter = converter;
	}

	public void specifyListener(ProgressListener progressListener) {
		this.converter.specifyListener(progressListener);
	}

	//////////////////////////////////////////////////////////////////////////////////////

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

	private boolean justConvert(Track track, //
			TrackFileLocation location, TrackFileFormat fromFormat, TrackFileFormat toFormat) throws Exception {

		return converter.convert(track, location, fromFormat, location, toFormat);
	}

	private boolean convertWithCopy(Track track, //
			TrackFileLocation fromLocation, TrackFileLocation toLocation, //
			TrackFileFormat fromFormat, TrackFileFormat toFormat) throws Exception {

		return converter.convert(track, fromLocation, fromFormat, toLocation, toFormat);

	}

}

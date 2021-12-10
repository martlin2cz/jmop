package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public interface BaseConverter {

	BaseLongOperation<Track, Track> convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat, ConversionReason reason);

}

package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public interface BaseConverter {
	public BaseLongOperation convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat);

}

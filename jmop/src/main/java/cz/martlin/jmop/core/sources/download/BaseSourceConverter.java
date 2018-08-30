package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public interface BaseSourceConverter extends ProgressGenerator {
	
	public boolean convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat, TrackFileLocation toLocation, TrackFileFormat toFormat) throws Exception;
	

	
}

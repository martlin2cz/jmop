package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormat;

public interface BaseSourceConverter extends ProgressGenerator {
	
	public boolean convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat, TrackFileLocation toLocation, TrackFileFormat toFormat) throws Exception;

	public boolean check();
	

	
}

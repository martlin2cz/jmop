package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public interface BaseSourceConverter extends ProgressGenerator {
	
	public boolean convert(Track track, TrackFileFormat from, boolean fromTmp, TrackFileFormat to, boolean toTmp) throws Exception;
	

	
}

package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public interface BaseDownloader {
	public TrackFileFormat downloadFormat();
	
	public BaseLongOperation<Track, Track> download(Track track, TrackFileLocation location) throws JMOPSourceException;

}

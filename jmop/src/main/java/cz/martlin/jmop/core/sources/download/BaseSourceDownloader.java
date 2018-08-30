package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public interface BaseSourceDownloader extends ProgressGenerator {

	public boolean download(Track track, TrackFileLocation location) throws Exception;

	public TrackFileFormat formatOfDownload();

}

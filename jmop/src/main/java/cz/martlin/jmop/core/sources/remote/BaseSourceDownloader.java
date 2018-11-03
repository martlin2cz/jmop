package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormat;

public interface BaseSourceDownloader extends ProgressGenerator {

	public boolean download(Track track, TrackFileLocation location) throws Exception;

	public TrackFileFormat formatOfDownload();

	public boolean check();

}

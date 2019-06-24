package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

/**
 * The base track downlader. What to say more? Maybe that each downloader has
 * specified download format.
 * 
 * The downloader may generate some progress.
 * 
 * @author martin
 *
 */
public interface BaseSourceDownloader extends ProgressGenerator {

	/**
	 * Downloads given track to given location.
	 * 
	 * @param track
	 * @param location
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public boolean download(Track track, TrackFileLocation location, ProgressListener listener) throws Exception;

	/**
	 * Returns the format this downloader downloads into.
	 * 
	 * @return
	 */
	public TrackFileFormat formatOfDownload();

	/**
	 * Returns true if this downloader can be used (there is no need to instal
	 * some additional programs or libraries or so).
	 * 
	 * @return
	 */
	public boolean check();

}

package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceDownloader;

/**
 * Specification, where downloaded, saved and prepared-to-play track files may
 * be stored.
 * 
 * @author martin
 *
 */
public interface AbstractTrackFileLocator {
	/**
	 * Returns where to store downloaded files, if downloaded by given
	 * downloader.
	 * 
	 * @param downloader
	 * @return
	 */
	public TrackFileLocation locationOfDownload(XXX_BaseSourceDownloader downloader);

	/**
	 * Returns where to store saved files.
	 * 
	 * @return
	 */
	public TrackFileLocation locationOfSave();

	/**
	 * Returns where to store track files prepared to play, if prepared to be
	 * played by given player.
	 * 
	 * @param player
	 * @return
	 */
	public TrackFileLocation locationOfPlay(BasePlayer player);

}

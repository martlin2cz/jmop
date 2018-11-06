package cz.martlin.jmop.core.config;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The main application configuration base interface.
 * 
 * @author martin
 *
 */
public interface BaseConfiguration {

	/**
	 * Returns format in-which the saved files may be in (preffered is MP3).
	 * 
	 * @return
	 */
	public TrackFileFormat getSaveFormat();

	/**
	 * Returns name (note: not the file name) of the playlist with the all
	 * tracks.
	 * 
	 * @return
	 */
	public String getAllTracksPlaylistName();

	/**
	 * Returns number of seconds, how long may the playlister wait offline until
	 * next retry of online.
	 * 
	 * @return
	 */
	public int getOfflineRetryTimeout();

}
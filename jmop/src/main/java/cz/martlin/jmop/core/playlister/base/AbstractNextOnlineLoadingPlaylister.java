package cz.martlin.jmop.core.playlister.base;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.preparer.TrackPreparer;

/**
 * Playlister loading next track when there is no next to be played, by plain
 * old remote source, downloader and converter (in fact {@link TrackPreparer} ).
 * 
 * @author martin
 *
 */
public abstract class AbstractNextOnlineLoadingPlaylister extends AbstractNextInferringPlaylister {

	protected final TrackPreparer preparer;
	private PlayerEngine engine;

	public AbstractNextOnlineLoadingPlaylister(TrackPreparer preparer) {
		super();
		this.preparer = preparer;
	}

	protected void loadNext(Track track) {
		System.out.println("okay, trying to load next, but only if is true: " + !preparer.isCurrentlyRunningSome());
		checkAndStartLoadingInBg(track);
	}

	@Override
	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist, PlaylistRuntime runtime) {
		super.startPlayingPlaylist(engine, playlist, runtime);
		
		this.engine = engine;
	}
	
	/**
	 * Checks whether there is no running load operation (which indicates that
	 * there is next track currently beeing loaded already) and if so, starts
	 * loading next track. The loaded next track is then appended to this
	 * playlister.
	 * 
	 * @param track
	 */
	private void checkAndStartLoadingInBg(Track track) {
		if (preparer.countOfCurrentlyRunning() <= 1) {
			preparer.startLoadingNextOf(track, engine);
		}
	}

}
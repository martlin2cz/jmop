package cz.martlin.jmop.core.strategy.base;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.preparer.XXX_TrackPreparer;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;

/**
 * Playlister loading next track when there is no next to be played, by plain
 * old remote source, downloader and converter (in fact {@link XXX_TrackPreparer} ).
 * 
 * @author martin
 *
 */
public abstract class AbstractNextOnlineLoadingStrategy extends AbstractNextInferringStrategy {

	protected final XXX_TrackPreparer preparer;
	private PlayerEngine engine;

	public AbstractNextOnlineLoadingStrategy(XXX_TrackPreparer preparer) {
		super();
		this.preparer = preparer;
	}

	/**
	 * Loads next (in background) of given track (but only if not yet loading
	 * it).
	 */
	@Override
	protected void loadNext(Track track) {
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
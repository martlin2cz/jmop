package cz.martlin.jmop.core.strategy.base;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;

/**
 * Playlister loading next track when there is no next to be played, by plain
 * old remote source, downloader and converter (in fact {@link XXX_TrackPreparer} ).
 * 
 * @author martin
 *
 */
public abstract class AbstractNextOnlineLoadingStrategy extends AbstractNextInferringStrategy {

	protected final BaseOperations operations;

	public AbstractNextOnlineLoadingStrategy(BaseOperations operations) {
		super();
		this.operations = operations;
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
	public void startPlayingPlaylist(Playlist playlist, PlaylistRuntime runtime) {
		super.startPlayingPlaylist(playlist, runtime);

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
		//operations.runLoadNext(track, resultHandler );
		throw new UnsupportedOperationException("HERE, load next and play");
	}

}
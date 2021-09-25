package cz.martlin.jmop.core.strategy.base;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;

/**
 * Playlister, which *somehow* loads the next track when there are no more track
 * enqueued. The way how the next track is beeing loaded is up to superclass.
 * 
 * @author martin
 *
 */
public abstract class AbstractNextInferringStrategy extends SimplePlaylisterStrategy {

	public AbstractNextInferringStrategy() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void playlistChanged(Playlist playlist, PlaylistRuntime runtime) {
		super.playlistChanged(playlist, runtime);

		checkAndInferNext();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track getCurrent() {
		Track track = super.getCurrent();
		checkAndInferNext(track);
		return track;
	}

	@Override
	public Track toNext() {
		Track track = super.toNext();
		checkAndInferNext(track);
		return track;
	}

	@Override
	public boolean hasNext() {
		checkAndInferNext();
		return super.hasNext();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Check whether has at least one track (to even be able to load next of
	 * anything), and if so checks and if needed loads next.
	 */
	private void checkAndInferNext() {
		if (hasAtLeastOneTrack()) {
			Track track = super.getCurrent();
			checkAndInferNext(track);
		}
	}

	/**
	 * Checks whether there are no more track to be played in the queue and in
	 * such case invokes the {@link #loadNext(Track)} with current track to load
	 * next.
	 * 
	 */
	private void checkAndInferNext(Track current) {
		if (!super.hasNext()) {
			loadNext(current);
		}
	}

	/**
	 * Loads or starts loading the next track of given track. This method has to
	 * load the next track and add it to this playlister. Keep in mind that this
	 * method could be
	 * 
	 * @param track
	 */
	protected abstract void loadNext(Track track);

}
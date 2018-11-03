package cz.martlin.jmop.core.strategy.base;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;

/**
 * Playlister, which *somehow* loads the next track when there are no more track
 * enqueued. The way how the next track is beeing loaded is up to superclass.
 * 
 * @author martin
 *
 */
public abstract class AbstractNextInferringStrategy extends SimplePlaylisterStrategy {

	// private InvalidationListener runtimeListener;

	public AbstractNextInferringStrategy() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist, PlaylistRuntime runtime) {
		super.startPlayingPlaylist(engine, playlist, runtime);

		// runtimeListener = (val) -> runtimeChanged();
		// runtime.addListener(runtimeListener);

		// checkAndInferNext();
	}

	@Override
	public void stopPlayingPlaylist() {
		// PlaylistRuntime runtime = getRuntime();
		// runtime.removeListener(runtimeListener);

		super.stopPlayingPlaylist();
	}

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

	// private void runtimeChanged() {
	// // this is invoked by all playlister, bot offline and online
	// // and thats terribly wrong
	// checkAndInferNext();
	// }

	/////////////////////////////////////////////////////////////////////////////////////
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
//		System.out.println("Okay, loading next of " + current.getTitle() + " by " + this);
		// PlaylistRuntime runtime = getRuntime();
		// if (runtime.count() == 0) {
		// return;
		// }
		//
		// Track current = getCurrent();

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
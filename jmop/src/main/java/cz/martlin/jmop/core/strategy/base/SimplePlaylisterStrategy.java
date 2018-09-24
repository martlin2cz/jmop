package cz.martlin.jmop.core.strategy.base;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.playlister.PlayerEngine;

/**
 * The base playlister, which simply everything delegates to the runtime. Feel
 * free to override some methods and add custom functionality to them.
 * 
 * @author martin
 *
 */
public abstract class SimplePlaylisterStrategy implements BasePlaylisterStrategy {

	private PlaylistRuntime runtime;

	public SimplePlaylisterStrategy() {
		super();
	}

	@Override
	public PlaylistRuntime getRuntime() {
		return runtime;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist, PlaylistRuntime runtime) {
		this.runtime = runtime;
	}

	@Override
	public void stopPlayingPlaylist() {
		this.runtime = null;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean hasAtLeastOneTrack() {
		return runtime.count() > 0;
	}
	
	@Override
	public boolean hasPrevious() {
		return runtime.hasPlayed();
	}

	@Override
	public boolean hasNext() {
		return runtime.hasNextToPlay();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track getPrevious() {
		return runtime.lastWasPlayed();
	}

	@Override
	public Track getCurrent() {
		return runtime.current();
	}

	@Override
	public Track getNext() {
		return runtime.nextToBePlayed();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track toNext() {
		Track track = runtime.toNext();
		return track;
	}

	@Override
	public Track toPrevious() {
		Track track = runtime.toPrevious();
		return track;
	}

	@Override
	public Track playChoosen(int index) {
		runtime.markPlayedUpTo(index);
		Track track = runtime.get(index);
		return track;
	}

	/////////////////////////////////////////////////////////////////////////////////////////

}
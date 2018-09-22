package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;

public abstract class SimplePlaylister implements BasePlaylister {

	private PlaylistRuntime runtime;

	public SimplePlaylister() {
		super();
	}


	@Override
	public PlaylistRuntime getRuntime() {
		return runtime;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void startPlayingPlaylist(Playlist playlist, PlaylistRuntime runtime) {
		this.runtime = runtime;
	}

	@Override
	public void stopPlayingPlaylist() {
		this.runtime = null;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

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
	public Track obtainPrevious() {
		Track track = runtime.toPrevious();
		return track;
	}

	@Override
	public Track obtainNext() {
		Track track = runtime.toNext();
		return track;
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track nextToPlay() {
		return runtime.current();
	}
	
	@Override
	public Track playChoosen(int index) {
		runtime.markPlayedUpTo(index);
		Track track = runtime.get(index);
		return track;
	}

	/////////////////////////////////////////////////////////////////////////////////////////

}
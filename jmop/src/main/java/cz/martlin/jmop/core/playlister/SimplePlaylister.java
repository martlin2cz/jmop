package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;

public abstract class SimplePlaylister implements BasePlaylister {

	private PlaylistRuntime runtime;

	public SimplePlaylister() {
		super();
	}

	public PlaylistRuntime getRuntime() {
		return runtime;
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void startPlayingPlaylist(Playlist playlist) {
		this.runtime = PlaylistRuntime.of(playlist);
	}

	@Override
	public void stopPlayingPlaylist(Playlist playlist) {
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
		Track track = runtime.lastPlayed();
		return track;
	}

	@Override
	public Track obtainNext() {
		Track track = runtime.nextToPlay();
		return track;
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track nextToPlay() {
		return runtime.nextToBePlayed();
	}
	
	@Override
	public Track playChoosen(int index) {
		runtime.markPlayedUpTo(index);
		Track track = runtime.get(index);
		return track;
	}

	/////////////////////////////////////////////////////////////////////////////////////////

}
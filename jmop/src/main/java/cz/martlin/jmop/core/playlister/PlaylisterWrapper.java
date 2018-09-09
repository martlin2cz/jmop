package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.ObservableValueProperty;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;

public class PlaylisterWrapper implements BaseWrapper<BasePlaylister> {
	private final BasePlaylister playlister;
	private final ObservableValueProperty<PlaylistRuntime> runtimeProperty;

	public PlaylisterWrapper(BasePlaylister playlister) {
		this.playlister = playlister;

		this.runtimeProperty = new ObservableValueProperty<>();

		initBindings();
	}

	public ObservableValueProperty<PlaylistRuntime> runtimeProperty() {
		return runtimeProperty;
	}

	@Override
	public void initBindings() {
		// nothing needed here
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(Playlist playlist) {
		playlister.startPlayingPlaylist(playlist);
		PlaylistRuntime runtime = playlister.getRuntime();
		runtimeProperty.set(runtime);
	}

	public void stopPlayingPlaylist(Playlist playlist) {
		playlister.stopPlayingPlaylist(playlist);
		runtimeProperty.set(null);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Track toNextToPlay() {
		return playlister.nextToPlay();
	}

	public Track toTrackAt(int index) {
		return playlister.playChoosen(index);
	}

	public Track toNext() {
		return playlister.obtainNext();
	}

	public Track toPrevious() {
		return playlister.obtainPrevious();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void addTrack(Track track) {
		PlaylistRuntime runtime = playlister.getRuntime();
		runtime.append(track);
	}

}

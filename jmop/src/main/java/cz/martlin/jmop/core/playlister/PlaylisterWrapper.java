package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ObservableValueProperty;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import javafx.beans.value.ChangeListener;

public class PlaylisterWrapper implements BaseWrapper<Playlister> {
	private final Playlister playlister;
	// private final ObservableValueProperty<PlaylistRuntime> runtimeProperty;
	private final ObservableValueProperty<Playlist> playlistProperty;
	private ChangeListener<? super PlaylistRuntime> runtimeListener;

	public PlaylisterWrapper(Playlister playlister) {
		this.playlister = playlister;

		// this.runtimeProperty = new ObservableValueProperty<>();
		this.playlistProperty = new ObservableValueProperty<>();

		initBindings();
	}

	//
	// public ObservableValueProperty<PlaylistRuntime> runtimeProperty() {
	// return runtimeProperty;
	// }
	public ObservableValueProperty<Playlist> playlistProperty() {
		return playlistProperty;
	}

	@Override
	public void initBindings() {
		// nothing needed here
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(Playlist playlist) {
		playlister.startPlayingPlaylist(playlist);

		runtimeListener = (observable, oldVal, newVal) -> runtimeChanged(newVal);
		PlaylistRuntime runtime = playlister.getRuntime();
		runtime.addListener(runtimeListener);

		playlistProperty.set(playlist);
	}

	public void stopPlayingPlaylist(Playlist playlist) {
		PlaylistRuntime runtime = playlister.getRuntime();
		runtime.removeListener(runtimeListener);

		playlister.stopPlayingPlaylist(playlist);
		playlistProperty.set(null);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Track playNext() throws JMOPSourceException {
		return playlister.playNext();
	}

	public Track play(int index) throws JMOPSourceException {
		return playlister.play(index);
	}

	public Track toNext() throws JMOPSourceException {
		return playlister.toNext();
	}

	public Track toPrevious() throws JMOPSourceException {
		return playlister.toNext();
	}

	public void add(Track track) {
		playlister.add(track);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void addTrack(Track track) {
		PlaylistRuntime runtime = playlister.getRuntime();
		runtime.append(track);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void runtimeChanged(PlaylistRuntime runtime) {
		Tracklist tracks = runtime.toTracklist();
		int currentIndex = runtime.currentTrackIndex();

		Playlist playlist = playlistProperty.get();

		playlist.setTracks(tracks);
		playlist.setCurrentTrackIndex(currentIndex);
	}

}

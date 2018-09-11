package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ObservableValueProperty;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

public class PlaylisterWrapper implements BaseWrapper<Playlister> {
	private final Playlister playlister;
	private final ObservableValueProperty<Playlist> playlistProperty;
	private final BooleanProperty hasPreviousProperty;
	private final BooleanProperty hasNextProperty;
	private final ObjectProperty<Track> previousTrackProperty;
	private final ObjectProperty<Track> nextTrackProperty;

	private ChangeListener<? super PlaylistRuntime> runtimeListener;

	public PlaylisterWrapper(Playlister playlister) {
		this.playlister = playlister;

		this.playlistProperty = new ObservableValueProperty<>();
		this.hasPreviousProperty = new SimpleBooleanProperty();
		this.hasNextProperty = new SimpleBooleanProperty();
		this.previousTrackProperty = new SimpleObjectProperty<>();
		this.nextTrackProperty = new SimpleObjectProperty<>();

		initBindings();
	}

	public ObservableValueProperty<Playlist> playlistProperty() {
		return playlistProperty;
	}

	public ReadOnlyBooleanProperty hasPreviousProperty() {
		return hasPreviousProperty;
	}

	public ReadOnlyBooleanProperty hasNextProperty() {
		return hasNextProperty;
	}

	public ObjectProperty<Track> previousTrackProperty() {
		return previousTrackProperty;
	}

	public ObjectProperty<Track> nextTrackProperty() {
		return nextTrackProperty;
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
		updateNextAndPreviousProperties(runtime);
	}

	public void stopPlayingPlaylist(Playlist playlist) {
		PlaylistRuntime runtime = playlister.getRuntime();
		runtime.removeListener(runtimeListener);

		playlister.stopPlayingPlaylist(playlist);
		playlistProperty.set(null);
		updateNextAndPreviousProperties(null);
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

		updateNextAndPreviousProperties(runtime);
	}

	private void updateNextAndPreviousProperties(PlaylistRuntime runtime) {
		if (runtime == null) {
			hasPreviousProperty.set(false);
			hasNextProperty.set(false);
			previousTrackProperty.set(null);
			nextTrackProperty.set(null);
		} else {
			boolean hasPrev = runtime.hasPlayed();
			hasPreviousProperty.set(hasPrev);

			boolean hasNext = runtime.hasNextToPlay();
			hasNextProperty.set(hasNext);
			
			Track previous = runtime.lastPlayed();
			previousTrackProperty.set(previous);
			
			Track next = runtime.nextToBePlayed();
			nextTrackProperty.set(next);
		}
	}

}

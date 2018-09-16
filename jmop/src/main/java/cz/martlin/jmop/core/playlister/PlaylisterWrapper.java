package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ObservableListenerBinding;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PlaylisterWrapper implements BaseWrapper<Playlister> {
	private final Playlister playlister;

	private final ObjectProperty<Playlist> playlistProperty;
	private final BooleanProperty hasPreviousProperty;
	private final BooleanProperty hasNextProperty;
	private final ObjectProperty<Track> previousTrackProperty;
	private final ObjectProperty<Track> nextTrackProperty;

	private final ObservableListenerBinding<PlaylistRuntime> runtimeListener;

	public PlaylisterWrapper(Playlister playlister) {
		this.playlister = playlister;

		this.playlistProperty = new SimpleObjectProperty<>();
		this.hasPreviousProperty = new SimpleBooleanProperty();
		this.hasNextProperty = new SimpleBooleanProperty();
		this.previousTrackProperty = new SimpleObjectProperty<>();
		this.nextTrackProperty = new SimpleObjectProperty<>();

		this.runtimeListener = new ObservableListenerBinding<>();

		initBindings();
	}

	public ObjectProperty<Playlist> playlistProperty() {
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
		PlaylistRuntime oldRuntime = playlister.getRuntime();

		playlister.startPlayingPlaylist(playlist);

		PlaylistRuntime newRuntime = playlister.getRuntime();
		runtimeListener.rebind(oldRuntime, newRuntime, //
				(r) -> runtimeChanged((PlaylistRuntime) r));

		playlistProperty.set(playlist);
		updateNextAndPreviousProperties(newRuntime);
	}

	public void stopPlayingPlaylist(Playlist playlist) {
		PlaylistRuntime runtime = playlister.getRuntime();
		runtimeListener.rebind(runtime, null, null);

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
		boolean hasPrev;
		boolean hasNext;
		Track previous;
		Track next;

		if (runtime == null) {
			hasPrev = false;
			hasNext = false;
			previous = null;
			next = null;
		} else {
			hasPrev = runtime.hasPlayed();
			hasNext = runtime.hasNextToPlay();

			if (hasPrev) {
				previous = runtime.lastWasPlayed();
			} else {
				previous = null;
			}
			if (hasNext) {
				next = runtime.nextToBePlayed();
			} else {
				next = null;
			}
		}

		hasPreviousProperty.set(hasPrev);
		hasNextProperty.set(hasNext);
		previousTrackProperty.set(previous);
		nextTrackProperty.set(next);
	}

}

package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
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

	public PlaylisterWrapper(Playlister playlister) {
		this.playlister = playlister;

		this.playlistProperty = new SimpleObjectProperty<>();
		this.hasPreviousProperty = new SimpleBooleanProperty();
		this.hasNextProperty = new SimpleBooleanProperty();
		this.previousTrackProperty = new SimpleObjectProperty<>();
		this.nextTrackProperty = new SimpleObjectProperty<>();

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
		InternetConnectionStatus connection = playlister.getConnection();
		connection.addListener((observable) -> offlineChanged()); 
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist) {
		playlister.startPlayingPlaylist(engine, playlist);

		playlistProperty.set(playlist);
		
		updateNextAndPreviousProperties();
	}

	public void stopPlayingPlaylist(Playlist playlist) {
		playlister.stopPlayingPlaylist(playlist);
		
		playlistProperty.set(null);
		
		updateNextAndPreviousProperties();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Track playNext() throws JMOPSourceException {
		return playlister.playNext();
	}

	public Track play(int index) throws JMOPSourceException {
		Track track = playlister.play(index);
		playlisterChanged();
		return track;
	}

	public Track toNext() throws JMOPSourceException {
		Track track = playlister.toNext();
		playlisterChanged();
		return track;
	}

	public Track toPrevious() throws JMOPSourceException {
		Track track = playlister.toPrevious();
		playlisterChanged();
		return track;
	}

	public void add(Track track) {
		playlister.add(track);
		playlisterChanged();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void offlineChanged() {
		System.out.println("PlaylisterWrapper.offlineChanged()");
		updateNextAndPreviousProperties();
	}
	
	private void playlisterChanged() {
		updatePlaylist();
		updateNextAndPreviousProperties();
	}

	private void updatePlaylist() {
		PlaylistRuntime runtime = playlister.getRuntime();

		Tracklist tracks = runtime.toTracklist();
		int currentIndex = runtime.currentTrackIndex();

		Playlist playlist = playlistProperty.get();

		playlist.setTracks(tracks);
		playlist.setCurrentTrackIndex(currentIndex);
	}

	private void updateNextAndPreviousProperties() {
		boolean hasPrev = playlister.hasPrevious();
		boolean hasNext = playlister.hasNext();
		Track previous;
		Track next;

		if (hasPrev) {
			previous = playlister.getPrevious();
		} else {
			previous = null;
		}
		if (hasNext) {
			next = playlister.getNext();
		} else {
			next = null;
		}

		hasPreviousProperty.set(hasPrev);
		hasNextProperty.set(hasNext);
		previousTrackProperty.set(previous);
		nextTrackProperty.set(next);
	}

}

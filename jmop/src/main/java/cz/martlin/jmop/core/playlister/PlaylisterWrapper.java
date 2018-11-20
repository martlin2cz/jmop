package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The wrapper for {@link Playlister}. Holds properties for current playlist,
 * previous and next tracks and booleans "has some track", "has previous track"
 * and "has next track".
 * 
 * @author martin
 *
 */
public class PlaylisterWrapper implements BaseWrapper<Playlister> {
	private final Playlister playlister;

	private final ObjectProperty<Playlist> playlistProperty;
	private final BooleanProperty hasSomeTrackProperty;
	private final BooleanProperty hasPreviousProperty;
	private final BooleanProperty hasNextProperty;
	private final ObjectProperty<Track> previousTrackProperty;
	private final ObjectProperty<Track> nextTrackProperty;

	private InvalidationListener playlistListener;

	public PlaylisterWrapper(Playlister playlister) {
		this.playlister = playlister;

		this.playlistProperty = new SimpleObjectProperty<>();
		this.hasSomeTrackProperty = new SimpleBooleanProperty();
		this.hasPreviousProperty = new SimpleBooleanProperty();
		this.hasNextProperty = new SimpleBooleanProperty();
		this.previousTrackProperty = new SimpleObjectProperty<>();
		this.nextTrackProperty = new SimpleObjectProperty<>();

		initBindings();
	}

	public ObjectProperty<Playlist> playlistProperty() {
		return playlistProperty;
	}

	public BooleanProperty hasSomeTrackProperty() {
		return hasSomeTrackProperty;
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

	/**
	 * Marks as playing given playlist.
	 * 
	 * @param engine
	 * @param playlist
	 */
	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist) {
		playlister.startPlayingPlaylist(engine, playlist);

		playlistProperty.set(playlist);

		playlistListener = (o) -> playlistValueChanged((Playlist) o);
		playlist.addListener(playlistListener);

		updateNextAndPreviousProperties();
	}

	/**
	 * Marks as NOT playing given playlist.
	 * 
	 * @param playlist
	 */
	public void stopPlayingPlaylist(Playlist playlist) {
		playlister.stopPlayingPlaylist(playlist);

		playlist.removeListener(playlistListener);
		playlistProperty.set(null);

		updateNextAndPreviousProperties();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Marks as playing "next" (in fact current) track.
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track playNext() throws JMOPSourceException {
		return playlister.playNext();
	}

	/**
	 * Marks as playing track at specified index.
	 * 
	 * @param index
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track play(int index) throws JMOPSourceException {
		Track track = playlister.play(index);
		playlisterChanged();
		return track;
	}

	/**
	 * Marks as playing next track (after the current one).
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track toNext() throws JMOPSourceException {
		Track track = playlister.toNext();
		playlisterChanged();
		return track;
	}

	/**
	 * Marks as playing previous track (before the current one).
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track toPrevious() throws JMOPSourceException {
		Track track = playlister.toPrevious();
		playlisterChanged();
		return track;
	}

	/**
	 * Adds given track.
	 * 
	 * @param track
	 */
	public void add(Track track) {
		playlister.add(track);
		playlisterChanged();
	}

	/**
	 * Removes all track after the current one.
	 */
	public void clearRemaining() {
		playlister.clearRemaining();
		playlisterChanged();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Handles changed value of offline status. In fact just updates some
	 * properties.
	 */
	private void offlineChanged() {
		updateNextAndPreviousProperties();
	}

	/**
	 * Handles changed playlist. In fact just delegates to playlister.
	 * 
	 * @param playlist
	 */
	private void playlistValueChanged(Playlist playlist) {
		playlister.playlistChanged(playlist);
	}

	/**
	 * Handles changed playister. In fact pushes changes into playlist and
	 * updates properties.
	 */
	private void playlisterChanged() {
		updatePlaylist();
		updateNextAndPreviousProperties();
	}

	/**
	 * Pushes updated runtime into (current) playist.
	 */
	private void updatePlaylist() {
		PlaylistRuntime runtime = playlister.getRuntime();

		Tracklist tracks = runtime.toTracklist();
		int currentIndex = runtime.currentTrackIndex();

		Playlist playlist = playlistProperty.get();

		playlist.setTracks(tracks);
		playlist.setCurrentTrackIndex(currentIndex);
	}

	/**
	 * Yea, does exactly what it sounds like.
	 */
	private void updateNextAndPreviousProperties() {
		boolean hasSome = playlister.hasSomeTrack();
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

		hasSomeTrackProperty.set(hasSome);
		hasPreviousProperty.set(hasPrev);
		hasNextProperty.set(hasNext);
		previousTrackProperty.set(previous);
		nextTrackProperty.set(next);
	}

}

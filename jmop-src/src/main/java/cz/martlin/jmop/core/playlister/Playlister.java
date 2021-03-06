package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;
import cz.martlin.jmop.core.strategy.base.BasePlaylisterStrategy;

/**
 * The playlister, simply said, class responsible for specifying which track
 * shall be played next. Holds reference to the playlist runtime instance and
 * bulk of particullar strategies (specifically: strategy for locked playlist,
 * strategy for offline playing and strategy of online playing). By current
 * situation chooses particullar strategy and delegates method to them.
 * 
 * @author martin
 *
 */
public class Playlister {
	private final InternetConnectionStatus connection;
	private Playlist currentPlaylist;

	private final BasePlaylisterStrategy lockedPlaylister;
	private final BasePlaylisterStrategy offlinePlaylister;
	private final BasePlaylisterStrategy onlinePlaylister;

	private PlaylistRuntime runtime;

	public Playlister(InternetConnectionStatus connection, BasePlaylisterStrategy lockedPlaylister,
			BasePlaylisterStrategy offlinePlaylister, BasePlaylisterStrategy onlinePlaylister) {
		super();
		this.connection = connection;
		this.lockedPlaylister = lockedPlaylister;
		this.offlinePlaylister = offlinePlaylister;
		this.onlinePlaylister = onlinePlaylister;
	}

	public InternetConnectionStatus getConnection() {
		return connection;
	}

	public PlaylistRuntime getRuntime() {
		return runtime;
	}

	////////////////////////////////////////////////////////////////////////////
	/**
	 * Mark as playing given playist and with given engine.
	 * 
	 * @param engine
	 * @param playlist
	 */
	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist) {
		currentPlaylist = playlist;
		runtime = PlaylistRuntime.of(playlist);

		lockedPlaylister.startPlayingPlaylist(engine, playlist, runtime);
		offlinePlaylister.startPlayingPlaylist(engine, playlist, runtime);
		onlinePlaylister.startPlayingPlaylist(engine, playlist, runtime);

	}

	/**
	 * Mark as NOT playing given playlist.
	 * 
	 * @param playlist
	 */
	public void stopPlayingPlaylist(Playlist playlist) {
		lockedPlaylister.stopPlayingPlaylist();
		offlinePlaylister.stopPlayingPlaylist();
		onlinePlaylister.stopPlayingPlaylist();

		currentPlaylist = null;
		runtime = null;
	}

	/**
	 * Handle playlist have been changed. In fact updates the runtime to fit the
	 * playist and delegates the event to the current strategy.
	 * 
	 * @param playlist
	 */
	public void playlistChanged(Playlist playlist) {
		runtime.updateTo(playlist);

		BasePlaylisterStrategy strategy = currentStrategy();
		strategy.playlistChanged(playlist, runtime);
	}

	////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns true if has at least one track (played, current or even queued).
	 * 
	 * @return
	 */
	public boolean hasSomeTrack() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.hasAtLeastOneTrack();
	}

	/**
	 * Has next track to play?
	 * 
	 * @return
	 */
	public boolean hasNext() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.hasNext();
	}

	/**
	 * Has previous (at least one played) track?
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.hasPrevious();
	}

	/**
	 * Returns previous track (last played).
	 * 
	 * @return
	 */
	public Track getPrevious() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.getPrevious();
	}

	/**
	 * Returns the current track (currently beeing played or to be played).
	 * 
	 * @return
	 */
	public Track getCurrent() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.getCurrent();
	}

	/**
	 * Returns next track to be played after the current.
	 * 
	 * @return
	 */
	public Track getNext() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.getNext();
	}

	////////////////////////////////////////////////////////////////////////////

	/**
	 * Mark playing "next" (in fact current) track.
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track playNext() throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.getCurrent();
		return track;
	}

	/**
	 * Mark playing track at given index.
	 * 
	 * @param index
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track play(int index) throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.playChoosen(index);
		return track;
	}

	/**
	 * Mark playing next track after the current one.
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track toNext() throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.toNext();
		return track;
	}

	/**
	 * Mark playing previous track (before the current one).
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track toPrevious() throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.toPrevious();
		return track;
	}

	/**
	 * Add given track (probably append, but not nescessarly).
	 * 
	 * @param track
	 */
	public void add(Track track) {
		BasePlaylisterStrategy strategy = currentStrategy();
		strategy.addTrack(track);
	}

	/**
	 * Remove all tracks before the current one.
	 */
	public void clearRemaining() {
		runtime.replaceRest(null);
	}

	////////////////////////////////////////////////////////////////////////////

	/**
	 * By current configuration (is playlist locked?) and status (is internet
	 * connection offline/online?) chooses the particullar strategy.
	 * 
	 * @return
	 */
	private BasePlaylisterStrategy currentStrategy() {
		if (currentPlaylist != null && currentPlaylist.isLocked()) {
			return lockedPlaylister;
		} else {
			if (connection.isOffline()) {
				return offlinePlaylister;
			} else {
				return onlinePlaylister;
			}
		}
	}

}

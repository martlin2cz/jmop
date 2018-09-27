package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.strategy.base.BasePlaylisterStrategy;

/**
 * The strategy, simply said, class responsible for specifying which track
 * shall be played next. Holds reference to the playlist runtime instance and
 * bulk of particullar strategy (specifically: strategy for locked playlist,
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

	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist) {
		currentPlaylist = playlist;
		runtime = PlaylistRuntime.of(playlist);

		lockedPlaylister.startPlayingPlaylist(engine, playlist, runtime);
		offlinePlaylister.startPlayingPlaylist(engine, playlist, runtime);
		onlinePlaylister.startPlayingPlaylist(engine, playlist, runtime);

	}

	public void stopPlayingPlaylist(Playlist playlist) {
		lockedPlaylister.stopPlayingPlaylist();
		offlinePlaylister.stopPlayingPlaylist();
		onlinePlaylister.stopPlayingPlaylist();

		currentPlaylist = null;
		runtime = null;
	}

	public void playlistChanged(Playlist playlist) {
		runtime.updateTo(playlist);

		BasePlaylisterStrategy strategy = currentStrategy();
		strategy.playlistChanged(playlist, runtime);
	}
	
	////////////////////////////////////////////////////////////////////////////

	public boolean hasSomeTrack() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.hasAtLeastOneTrack();
	}

	
	public boolean hasNext() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.hasNext();
	}

	public boolean hasPrevious() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.hasPrevious();
	}

	public Track getPrevious() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.getPrevious();
	}

	public Track getCurrent() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.getCurrent();
	}

	public Track getNext() {
		BasePlaylisterStrategy strategy = currentStrategy();
		return strategy.getNext();
	}

	////////////////////////////////////////////////////////////////////////////

	public Track playNext() throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.getCurrent();
		return track;
	}

	public Track play(int index) throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.playChoosen(index);
		return track;
	}

	public Track toNext() throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.toNext();
		return track;
	}

	public Track toPrevious() throws JMOPSourceException {
		BasePlaylisterStrategy strategy = currentStrategy();
		Track track = strategy.toPrevious();
		return track;
	}

	public void add(Track track) {
		BasePlaylisterStrategy strategy = currentStrategy();
		strategy.addTrack(track);
	}
	

	public void clearRemaining() {
		runtime.replaceRest(null);
	}

	////////////////////////////////////////////////////////////////////////////

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

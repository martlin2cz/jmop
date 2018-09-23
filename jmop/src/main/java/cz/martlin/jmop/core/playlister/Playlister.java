package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.playlister.base.BasePlaylister;

/**
 * The playlister, simply said, class responsible for specifying which track
 * shall be played next. Holds reference to the playlist runtime instance and
 * bulk of particullar playlister (specifically: playlister for locked playlist,
 * playlister for offline playing and playlister of online playing). By current
 * situation chooses particullar playlister and delegates method to them.
 * 
 * @author martin
 *
 */
public class Playlister {
	private final InternetConnectionStatus connection;
	private Playlist currentPlaylist;

	private final BasePlaylister lockedPlaylister;
	private final BasePlaylister offlinePlaylister;
	private final BasePlaylister onlinePlaylister;
	
	private PlaylistRuntime runtime;

	public Playlister(InternetConnectionStatus connection, BasePlaylister lockedPlaylister,
			BasePlaylister offlinePlaylister, BasePlaylister onlinePlaylister) {
		super();
		this.connection = connection;
		this.lockedPlaylister = lockedPlaylister;
		this.offlinePlaylister = offlinePlaylister;
		this.onlinePlaylister = onlinePlaylister;
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

	////////////////////////////////////////////////////////////////////////////

	public boolean hasNext() {
		BasePlaylister playlister = currentPlaylister();
		return playlister.hasNext();
	}

	public boolean hasPrevious() {
		BasePlaylister playlister = currentPlaylister();
		return playlister.hasPrevious();
	}

	public Track getPrevious() {
		BasePlaylister playlister = currentPlaylister();
		return playlister.getPrevious();
	}

	public Track getCurrent() {
		BasePlaylister playlister = currentPlaylister();
		return playlister.getCurrent();
	}

	public Track getNext() {
		BasePlaylister playlister = currentPlaylister();
		return playlister.getNext();
	}

	////////////////////////////////////////////////////////////////////////////

	public Track playNext() throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.getCurrent();
		return track;
	}

	public Track play(int index) throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.playChoosen(index);
		return track;
	}

	public Track toNext() throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.toNext();
		return track;
	}

	public Track toPrevious() throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.toPrevious();
		return track;
	}

	public void add(Track track) {
		BasePlaylister playlister = currentPlaylister();
		playlister.addTrack(track);
	}

	////////////////////////////////////////////////////////////////////////////

	private BasePlaylister currentPlaylister() {
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

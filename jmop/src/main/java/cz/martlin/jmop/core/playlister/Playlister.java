package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;

public class Playlister {
	private final InternetConnectionStatus connection;
	private final BasePlaylister offlinePlaylister;
	private final BasePlaylister onlinePlaylister;

	public Playlister(InternetConnectionStatus connection, BasePlaylister offlinePlaylister,
			BasePlaylister onlinePlaylister) {
		super();
		this.connection = connection;
		this.offlinePlaylister = offlinePlaylister;
		this.onlinePlaylister = onlinePlaylister;
	}

	public BasePlaylister getOnline() {
		return onlinePlaylister;
	}

	public BasePlaylister getOffline() {
		return offlinePlaylister;
	}

	////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(Playlist playlist) {
		// TODO A!!!
		throw new UnsupportedOperationException();
	}

	public void stopPlayingPlaylist(Playlist currentPlaylist) {
		// TODO !!!
		throw new UnsupportedOperationException();
	}

	////////////////////////////////////////////////////////////////////////////

	public Track playNext() throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.obtainNext();
		return track;
	}

	public Track play(int index) throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.playChoosen(index);
		return track;
	}

	public Track toNext() throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.obtainNext();
		return track;
	}

	public Track toPrevious() throws JMOPSourceException {
		BasePlaylister playlister = currentPlaylister();
		Track track = playlister.obtainPrevious();
		return track;
	}

	public void add(Track track) {
		BasePlaylister playlister = currentPlaylister();
		playlister.trackPrepared(track);
	}

	////////////////////////////////////////////////////////////////////////////

	private BasePlaylister currentPlaylister() {
		if (connection.isOffline()) {
			return offlinePlaylister;
		} else {
			return onlinePlaylister;
		}
	}

	public PlaylistRuntime getRuntime() {
		// TODO Auto-generated method stub
		return null;
	}

}

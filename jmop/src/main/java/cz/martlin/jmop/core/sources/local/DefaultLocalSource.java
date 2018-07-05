package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.Playlist;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public class DefaultLocalSource implements BaseLocalSource {

	private final AbstractFileSystemAccessor fileSystem;

	public DefaultLocalSource(AbstractFileSystemAccessor fileSystem) {
		super();
		this.fileSystem = fileSystem;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listBundlesNames() throws JMOPSourceException {
		try {
			return fileSystem.listBundles();
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot list bundles", e);
		}
	}

	@Override
	public Bundle getBundle(String name) throws JMOPSourceException {
		try {
			return fileSystem.loadBundle(name);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot load bundle", e);
		}
	}

	@Override
	public void addBundle(Bundle bundle) throws JMOPSourceException {
		try {
			fileSystem.createBundle(bundle);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot add bundle", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException {
		try {
			return fileSystem.listPlaylists(bundle);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot list playlists", e);
		}
	}

	@Override
	public Playlist getFullPlaylist(Bundle bundle) throws JMOPSourceException {
		try {
			return fileSystem.getFullPlaylist(bundle);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot load full playlists", e);
		}
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		try {
			return fileSystem.getPlaylist(bundle, name);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot load playlist", e);
		}
	}

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException {
		try {
			fileSystem.savePlaylist(bundle, playlist);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot save playlist", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track getTrack(TrackIdentifier id) throws JMOPSourceException {
		Bundle bundle = null;	//TODO
		return bundle.getTrack(id);
	}

	@Override
	public File fileOfTrack(Track track, TrackFileFormat format) throws JMOPSourceException {
		try {
			Bundle bundle = null;	//TODO
			return fileSystem.getFileOfTrack(bundle, track, format);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot infer file of track", e);
		}
	}

	@Override
	public boolean exists(Track track) throws JMOPSourceException {
		Bundle bundle = null;	//TODO
		return bundle.contains(track);
	}

}

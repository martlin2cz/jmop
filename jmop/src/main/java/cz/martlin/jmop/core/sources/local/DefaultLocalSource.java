package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public class DefaultLocalSource implements BaseLocalSource {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public static final TrackFileFormat MAIN_STORE_FORMAT = TrackFileFormat.MP3;

	private final AbstractFileSystemAccessor fileSystem;

	public DefaultLocalSource(AbstractFileSystemAccessor fileSystem) {
		super();
		this.fileSystem = fileSystem;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listBundlesNames() throws JMOPSourceException {
		LOG.info("Listing bundle names");
		try {
			return fileSystem.listBundles();
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot list bundles", e);
		}
	}

	@Override
	public Bundle getBundle(String name) throws JMOPSourceException {
		LOG.info("Loading bundle " + name);
		try {
			return fileSystem.loadBundle(name);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot load bundle", e);
		}
	}

	@Override
	public void createBundle(Bundle bundle) throws JMOPSourceException {
		try {
			fileSystem.createBundle(bundle);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot create bundle", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException {
		LOG.info("Listing playlists of bundle " + bundle.getName());
		try {
			return fileSystem.listPlaylists(bundle);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot list playlists", e);
		}
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		LOG.info("Loading playlist " + name + " of bundle " + bundle.getName());
		try {
			return fileSystem.getPlaylist(bundle, name);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot load playlist", e);
		}
	}

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException {
		LOG.info("Saving playlist " + playlist.getName() + " of bundle " + bundle.getName());
		try {
			fileSystem.savePlaylist(bundle, playlist);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot save playlist", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track getTrack(Bundle bundle, String id) throws JMOPSourceException {
		LOG.info("Loading track " + id + " of bundle " + bundle.getName());
		return bundle.getTrack(id);
	}

	@Override
	public File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {
		LOG.info("Infering file of track " + track.getTitle() + " in " + location + " as " + format);
		try {
			Bundle bundle = track.getBundle();
			return fileSystem.getFileOfTrack(bundle, track, location, format);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot infer file of track", e);
		}
	}

	@Override
	public boolean exists(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException {
		LOG.info("Checking existence of track " + track.getTitle() + " in " + location + " as " + format);

		// TODO hacky af, killme
		try {
			File file = fileSystem.getFileOfTrack(track.getBundle(), track, location, format);
			return file.exists();
		} catch (IOException e) {
			throw new JMOPSourceException("Cannnot check file existence", e);
		}
	}

}

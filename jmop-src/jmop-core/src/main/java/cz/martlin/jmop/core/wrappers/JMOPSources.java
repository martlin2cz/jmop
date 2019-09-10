package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

/**
 * The JMOP wrapper responsible for sources (list/save bundles, playlists, load
 * track etc.) stuff. Do not invoke directly, use JMOPPlayer instead.
 * 
 * @author martin
 *
 */
public class JMOPSources {
	private final BaseLocalSource local;
	private final BaseOperations operations;

	public JMOPSources(BaseLocalSource local, BaseOperations operations) {
		super();
		this.local = local;
		this.operations = operations;
	}

	public BaseOperations getOperations() {
		return operations;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Create new bundle with the new playlist and start searching the initial
	 * track.
	 * 
	 * @param kind
	 * @param bundleName
	 * @param query
	 * @param engine
	 * @return
	 * @throws JMOPSourceException
	 */
	public Playlist createNewBundleAndPrepare(SourceKind kind, String bundleName, String query, PlayerEngine engine)
			throws JMOPSourceException {
		Bundle bundle = createBundle(kind, bundleName);
		Playlist playlist = new Playlist(bundle, query);

		preparer.startSearchAndLoadInBg(bundle, query, engine);

		return playlist;
	}

	/**
	 * Create new playlist.
	 * 
	 * @param bundle
	 * @param query
	 * @param engine
	 * @return
	 * @throws JMOPSourceException
	 */
	public Playlist createNewPlaylist(Bundle bundle, String query, PlayerEngine engine) throws JMOPSourceException {
		Playlist playlist = new Playlist(bundle, query);

		preparer.startSearchAndLoadInBg(bundle, query, engine);
		return playlist;
	}

	/**
	 * Load playlist.
	 * 
	 * @param bundleName
	 * @param playlistName
	 * @param engine
	 * @return
	 * @throws JMOPSourceException
	 */
	public Playlist loadPlaylist(String bundleName, String playlistName, PlayerEngine engine)
			throws JMOPSourceException {
		Bundle bundle = local.getBundle(bundleName);
		Playlist playlist = local.getPlaylist(bundle, playlistName);

		return playlist;

	}

	/**
	 * Save playlist.
	 * 
	 * @param playlist
	 * @param newPlaylistName
	 * @throws JMOPSourceException
	 */
	public void savePlaylist(Playlist playlist, String newPlaylistName) throws JMOPSourceException {
		playlist.setName(newPlaylistName);
		Bundle bundle = playlist.getBundle();
		local.savePlaylist(bundle, playlist);
	}

	/**
	 * Query given query and load the found track.
	 * 
	 * @param bundle
	 * @param query
	 * @param engine
	 * @throws JMOPSourceException
	 */
	public void queryAndLoad(Bundle bundle, String query, PlayerEngine engine) throws JMOPSourceException {
		preparer.startSearchAndLoadInBg(bundle, query, engine);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * List all bundles.
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public List<String> listAllBundles() throws JMOPSourceException {
		return local.listBundlesNames();
	}

	/**
	 * List all playlists in the given bundle.
	 * 
	 * @param bundleName
	 * @return
	 * @throws JMOPSourceException
	 */
	public List<String> listPlaylists(String bundleName) throws JMOPSourceException {
		Bundle bundle = local.getBundle(bundleName);
		return local.listPlaylistNames(bundle);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Create bundle.
	 * 
	 * @param kind
	 * @param bundleName
	 * @return
	 * @throws JMOPSourceException
	 */
	private Bundle createBundle(SourceKind kind, String bundleName) throws JMOPSourceException {
		Bundle bundle = new Bundle(kind, bundleName);
		local.createBundle(bundle);
		return bundle;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}

package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.core.playlister.SimplePlayerEngine;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;

/**
 * The JMOP wrapper responsible for sources (list/save bundles, playlists, load
 * track etc.) stuff. Do not invoke directly, use JMOPPlayer instead.
 * 
 * @author martin
 */
public class JMOPSources {
	private final XXX_BaseLocalSource local;
	private final BaseOperations operations;

	public JMOPSources(XXX_BaseLocalSource local, BaseOperations operations) {
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
	 * @throws JMOPMusicbaseException
	 */
	public Playlist createNewBundleAndPrepare(SourceKind kind, String bundleName, String query, SimplePlayerEngine engine)
			throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public Playlist createNewPlaylist(Bundle bundle, String query, SimplePlayerEngine engine) throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public Playlist loadPlaylist(String bundleName, String playlistName, SimplePlayerEngine engine)
			throws JMOPMusicbaseException {
		Bundle bundle = local.getBundle(bundleName);
		Playlist playlist = local.getPlaylist(bundle, playlistName);

		return playlist;

	}

	/**
	 * Save playlist.
	 * 
	 * @param playlist
	 * @param newPlaylistName
	 * @throws JMOPMusicbaseException
	 */
	public void savePlaylist(Playlist playlist, String newPlaylistName) throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public void queryAndLoad(Bundle bundle, String query, SimplePlayerEngine engine) throws JMOPMusicbaseException {
		preparer.startSearchAndLoadInBg(bundle, query, engine);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * List all bundles.
	 * 
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public List<String> listAllBundles() throws JMOPMusicbaseException {
		return local.listBundlesNames();
	}

	/**
	 * List all playlists in the given bundle.
	 * 
	 * @param bundleName
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public List<String> listPlaylists(String bundleName) throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	private Bundle createBundle(SourceKind kind, String bundleName) throws JMOPMusicbaseException {
		Bundle bundle = new Bundle(kind, bundleName);
		local.createBundle(bundle);
		return bundle;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
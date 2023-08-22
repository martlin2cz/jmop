package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.musicdataloader.CommonMusicdataLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An playlist loader, which firstly lists playlists indentifiers and just then
 * loads the particular playlists. The lists delegated to
 * {@link BasePlaylistsIdentifiersLister, the loads delegates to
 * {@link BasePlaylistsByIdentifierLoader}.
 * 
 * It's component of {@link CommonMusicdataLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class ByIdentifierPlaylistsLoader<IT> implements BasePlaylistsLoader {

	private final BasePlaylistsIdentifiersLister<IT> lister;
	private final BasePlaylistsByIdentifierLoader<IT> loader;

	public ByIdentifierPlaylistsLoader(BasePlaylistsIdentifiersLister<IT> lister,
			BasePlaylistsByIdentifierLoader<IT> loader) {
		super();
		this.lister = lister;
		this.loader = loader;
	}

	@Override
	public Set<Playlist> loadPlaylists(Bundle bundle, Map<String, Track> tracksMap) throws JMOPPersistenceException {
		Set<IT> indentifiers = lister.loadPlaylistsIdentifiers(bundle);

		return loadPlaylists(bundle, indentifiers, tracksMap);
	}

	private Set<Playlist> loadPlaylists(Bundle bundle, Set<IT> indentifiers, Map<String, Track> tracksMap)
			throws JMOPPersistenceException {
		Set<Playlist> playlists = new HashSet<>(indentifiers.size());

		for (IT indentifier : indentifiers) {
			Playlist playlist = loader.loadPlaylist(bundle, indentifier, tracksMap);

			if (playlist != null) {
				playlists.add(playlist);
			}
		}

		return playlists;
	}

}

package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

@Deprecated
public class AutomaticSavesPerformer {
	private final BaseConfiguration config; 
	private final BaseLocalSource local;
	private Playlist playlist;

	public AutomaticSavesPerformer(BaseConfiguration config, BaseLocalSource local) {
		super();
		this.config = config;
		this.local = local;
		this.playlist = null;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	///////////////////////////////////////////////////////////////////////////

	public void saveCurrentBundle() {
		Bundle bundle = playlist.getBundle();
		saveBundle(bundle);
	}

	public void saveBundle(Bundle bundle) {
		try {
			// XXX hack! LOG.warn("Saving of bundle hacked here");
			final String allTracksPlaylistName =  config.getAllTracksPlaylistName();
			Playlist playlist = new Playlist(bundle, allTracksPlaylistName, bundle.tracks());
			local.savePlaylist(bundle, playlist);
		} catch (JMOPSourceException e) {
			e.printStackTrace(); // TODO error report
		}
	}

	public void saveCurrentPlaylist() {
		savePlaylist(playlist);
	}

	private void savePlaylist(Playlist playlist)  {
		try {
			Bundle bundle = playlist.getBundle();
			local.savePlaylist(bundle, playlist);
		} catch (JMOPSourceException e) {
			e.printStackTrace(); // TODO error report
		}
	}

}

package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.WorksWithPlaylist;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

public class AutomaticSavesPerformer {
	private final BaseLocalSource local;
	private Playlist playlist;

	public AutomaticSavesPerformer(BaseLocalSource local) {
		super();
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

	private void saveBundle(Bundle bundle) {
		try {
			// XXX hack! LOG.warn("Saving of bundle hacked here");
			final String ALL_TRACKS = "all_tracks"; // FIXME !!!!
			Playlist playlist = new Playlist(bundle, ALL_TRACKS, bundle.tracks());
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

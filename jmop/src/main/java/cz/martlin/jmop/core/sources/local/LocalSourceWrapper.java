package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;
import javafx.beans.value.ChangeListener;

public class LocalSourceWrapper implements BaseWrapper<BaseLocalSource> {
	private final BaseLocalSource local;
	private final PlaylisterWrapper playlister;

	private ChangeListener<Bundle> bundleListener;

	public LocalSourceWrapper(BaseLocalSource local, PlaylisterWrapper playlister) {
		super();
		this.local = local;
		this.playlister = playlister;
	}

	@Override
	public void initBindings() {
		playlister.playlistProperty().addListener((observable, oldVal, newVal) -> playlistChaned(oldVal, newVal));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Bundle getBundle(String bundleName) throws JMOPSourceException {
		return local.getBundle(bundleName);
	}

	public Playlist getPlaylist(Bundle bundle, String playlistName) throws JMOPSourceException {
		return local.getPlaylist(bundle, playlistName);
	}

	public List<String> listBundlesNames() throws JMOPSourceException {
		return local.listBundlesNames();
	}

	public List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException {
		return local.listPlaylistNames(bundle);
	}

	public void createBundle(Bundle bundle) throws JMOPSourceException {
		local.createBundle(bundle);
	}

	public void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException {
		local.savePlaylist(bundle, playlist);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	private void playlistChaned(Playlist oldPlaylistValue, Playlist newPlaylistValue) {
		final boolean isNonNull = newPlaylistValue != null;
		final boolean wasNonNull = oldPlaylistValue != null;

		try {
			if (isNonNull && wasNonNull) {
				savePlaylist(newPlaylistValue);
			}

			if (isNonNull && !wasNonNull) {
				bindPlaylist(newPlaylistValue);
			}
			if (!isNonNull && wasNonNull) {
				unbindPlaylist(oldPlaylistValue);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO error hangling
		}
	}

	private void bundleChanged(Bundle bundle) {
		try {
			saveBundle(bundle);
		} catch (JMOPSourceException e) {
			e.printStackTrace();
			// TODO error hangling
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void savePlaylist(Playlist playlist) throws JMOPSourceException {
		Bundle bundle = playlist.getBundle();
		local.savePlaylist(bundle, playlist);
	}

	private void unbindPlaylist(Playlist playlist) {
		Bundle bundle = playlist.getBundle();
		bundle.removeListener(bundleListener);
	}

	private void bindPlaylist(Playlist playlist) {
		Bundle bundle = playlist.getBundle();

		bundleListener = (observable, oldVal, newVal) -> bundleChanged(newVal);
		bundle.addListener(bundleListener);
	}

	private void saveBundle(Bundle bundle) throws JMOPSourceException {
		local.saveBundle(bundle);
	}

}

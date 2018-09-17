package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.BundleBinding;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ObservableListenerBinding;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;

public class LocalSourceWrapper implements BaseWrapper<BaseLocalSource> {
	private final BaseLocalSource local;
	private final PlaylisterWrapper playlister;

	private final ObservableListenerBinding<Playlist> playlistBinding;
	private final ObservableListenerBinding<Playlist> bundleBinding;

	public LocalSourceWrapper(BaseLocalSource local, PlaylisterWrapper playlister) {
		super();
		this.local = local;
		this.playlister = playlister;

		this.playlistBinding = new ObservableListenerBinding<>();
		this.bundleBinding = new BundleBinding();
		
		initBindings();
	}

	@Override
	public void initBindings() {
		playlister.playlistProperty().addListener(//
				(observable, oldVal, newVal) -> playlistPropertyValueChaned(oldVal, newVal));
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

	private void playlistPropertyValueChaned(Playlist oldPlaylistValue, Playlist newPlaylistValue) {
		playlistBinding.rebind(oldPlaylistValue, newPlaylistValue, //
				(p) -> playlistChanged((Playlist) p));

		bundleBinding.rebind(oldPlaylistValue, newPlaylistValue, //
				(b) -> bundleChanged((Bundle) b));

	}

	private void playlistChanged(Playlist playlist) {
		try {
			Bundle bundle = playlist.getBundle();
			local.savePlaylist(bundle, playlist);
			System.out.println("Playlist " + playlist.getName() + " changed, has " + playlist.getTracks().count() + " tracks");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO error hangling
		}
	}
//
//	private void playlistBundleChanged(Playlist playlist) {
//		Bundle bundle = playlist.getBundle();
//		bundleChanged(bundle);
//	}

	private void bundleChanged(Bundle bundle) {
		try {
			local.saveBundle(bundle);
		} catch (JMOPSourceException e) {
			e.printStackTrace();
			// TODO error hangling
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

}

package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.BundleBinding;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ObservableListenerBinding;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;

/**
 * The wrapper of local source. Listens for changes of bundle or current
 * playlist and if change occurs, automatically saves.
 * 
 * @author martin
 *
 */
public class LocalSourceWrapper implements BaseWrapper<BaseLocalSource> {
	private final ErrorReporter reporter;
	private final BaseLocalSource local;
	private final PlaylisterWrapper playlister;

	private final ObservableListenerBinding<Playlist> playlistBinding;
	private final ObservableListenerBinding<Playlist> bundleBinding;

	public LocalSourceWrapper(ErrorReporter reporter, BaseLocalSource local, PlaylisterWrapper playlister) {
		super();
		this.reporter = reporter;
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

	/**
	 * Loads bundle of given name.
	 * 
	 * @param bundleName
	 * @return
	 * @throws JMOPSourceException
	 */
	public Bundle getBundle(String bundleName) throws JMOPSourceException {
		return local.getBundle(bundleName);
	}

	/**
	 * Loads playlist of given name and bundle.
	 * 
	 * @param bundle
	 * @param playlistName
	 * @return
	 * @throws JMOPSourceException
	 */
	public Playlist getPlaylist(Bundle bundle, String playlistName) throws JMOPSourceException {
		return local.getPlaylist(bundle, playlistName);
	}

	/**
	 * Loads names of all bundles.
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public List<String> listBundlesNames() throws JMOPSourceException {
		return local.listBundlesNames();
	}

	/**
	 * Loads names of all playlists within the given bundle.
	 * 
	 * @param bundle
	 * @return
	 * @throws JMOPSourceException
	 */
	public List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException {
		return local.listPlaylistNames(bundle);
	}

	/**
	 * Creates given bundle.
	 * 
	 * @param bundle
	 * @throws JMOPSourceException
	 */
	public void createBundle(Bundle bundle) throws JMOPSourceException {
		local.createBundle(bundle);
	}

	/**
	 * Saves the given playlist.
	 * 
	 * @param bundle
	 * @param playlist
	 * @throws JMOPSourceException
	 */
	public void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException {
		local.savePlaylist(bundle, playlist);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Handles changed value of playlist property. In fact, simply rebinds both
	 * playlist and its bundle.
	 * 
	 * @param oldPlaylistValue
	 * @param newPlaylistValue
	 */
	private void playlistPropertyValueChaned(Playlist oldPlaylistValue, Playlist newPlaylistValue) {
		playlistBinding.rebind(oldPlaylistValue, newPlaylistValue, //
				(p) -> playlistChanged((Playlist) p));

		bundleBinding.rebind(oldPlaylistValue, newPlaylistValue, //
				(b) -> bundleChanged((Bundle) b));

	}

	/**
	 * Handles change of (given) playlist value. In fact, saves the updated
	 * playlist.
	 * 
	 * @param playlist
	 */
	private void playlistChanged(Playlist playlist) {
		try {
			Bundle bundle = playlist.getBundle();
			local.savePlaylist(bundle, playlist);
		} catch (JMOPSourceException e) {
			reporter.report(e);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}

	/**
	 * Handles change of (given) bundle value. In fact, just saves the updated
	 * bundle.
	 * 
	 * @param bundle
	 */
	private void bundleChanged(Bundle bundle) {
		try {
			local.saveBundle(bundle);
		} catch (JMOPSourceException e) {
			reporter.report(e);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

}

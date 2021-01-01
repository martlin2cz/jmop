package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.BundleBinding;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ObservableListenerBinding;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;

/**
 * The wrapper of local source. Listens for changes of bundle or current
 * playlist and if change occurs, automatically saves.
 * 
 * @author martin
 */
public class LocalSourceWrapper implements BaseWrapper<XXX_BaseLocalSource> {
	private final BaseErrorReporter reporter;
	private final XXX_BaseLocalSource local;
	private final PlaylisterWrapper playlister;

	private final ObservableListenerBinding<Playlist> playlistBinding;
	private final ObservableListenerBinding<Playlist> bundleBinding;

	public LocalSourceWrapper(BaseErrorReporter reporter, XXX_BaseLocalSource local, PlaylisterWrapper playlister) {
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
	 * @throws JMOPMusicbaseException
	 */
	public Bundle getBundle(String bundleName) throws JMOPMusicbaseException {
		return local.getBundle(bundleName);
	}

	/**
	 * Loads playlist of given name and bundle.
	 * 
	 * @param bundle
	 * @param playlistName
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public Playlist getPlaylist(Bundle bundle, String playlistName) throws JMOPMusicbaseException {
		return local.getPlaylist(bundle, playlistName);
	}

	/**
	 * Loads names of all bundles.
	 * 
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public List<String> listBundlesNames() throws JMOPMusicbaseException {
		return local.listBundlesNames();
	}

	/**
	 * Loads names of all playlists within the given bundle.
	 * 
	 * @param bundle
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public List<String> listPlaylistNames(Bundle bundle) throws JMOPMusicbaseException {
		return local.listPlaylistNames(bundle);
	}

	/**
	 * Creates given bundle.
	 * 
	 * @param bundle
	 * @throws JMOPMusicbaseException
	 */
	public void createBundle(Bundle bundle) throws JMOPMusicbaseException {
		local.createBundle(bundle);
	}

	/**
	 * Saves the given playlist.
	 * 
	 * @param bundle
	 * @param playlist
	 * @throws JMOPMusicbaseException
	 */
	public void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPMusicbaseException {
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
		} catch (JMOPMusicbaseException e) {
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
		} catch (JMOPMusicbaseException e) {
			reporter.report(e);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

}

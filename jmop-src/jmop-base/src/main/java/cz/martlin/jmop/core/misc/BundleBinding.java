package cz.martlin.jmop.core.misc;

import cz.martlin.jmop.core.data.Playlist;
import javafx.beans.InvalidationListener;

/**
 * Biding extension of playlist's bundle. For each rebind binds/unbinds not only
 * the listener for the playlist, but also for its bundle.
 * 
 * @author martin
 *
 */
public class BundleBinding extends ObservableListenerBinding<Playlist> {

	@Override
	protected void setListener(InvalidationListener listener, Playlist to) {
		to.getBundle().addListener(listener);
	}

	@Override
	protected void unsetListener(InvalidationListener listener, Playlist to) {
		to.getBundle().removeListener(listener);
	}
}

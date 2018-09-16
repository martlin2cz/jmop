package cz.martlin.jmop.core.data;

import cz.martlin.jmop.core.misc.ObservableListenerBinding;
import javafx.beans.InvalidationListener;

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

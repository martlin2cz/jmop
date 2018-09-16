package cz.martlin.jmop.core.misc;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;

public class ObservableListenerBinding<T extends ObservableValue<T>> {
	private InvalidationListener currentListener;

	public ObservableListenerBinding() {
	}

	public void rebind(T oldValue, T newValue, InvalidationListener listener) {
		if (oldValue == null && newValue != null) {
			this.currentListener = listener;

			setListener(currentListener, newValue);
		}

		if (oldValue != null && newValue != null) {
			unsetListener(currentListener, oldValue);

			this.currentListener = listener;

			setListener(currentListener, newValue);
		}

		if (oldValue != null && newValue == null) {
			unsetListener(currentListener, oldValue);

			this.currentListener = null;
		}
	}

	protected void unsetListener(InvalidationListener listener, T to) {
		to.removeListener(listener);
	}

	protected void setListener(InvalidationListener listener, T to) {
		to.addListener(listener);
	}

}

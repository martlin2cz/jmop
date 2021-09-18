package cz.martlin.jmop.core.misc;

import java.beans.PropertyChangeListener;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

/**
 * Utility class performing shift of {@link InvalidationListener} of some
 * {@link Property}, when its value changes. Simply use this class
 * {@link #rebind(ObservableValue, ObservableValue, InvalidationListener)}
 * method as a {@link PropertyChangeListener}.
 * 
 * @author martin
 *
 * @param <T>
 */
public class ObservableListenerBinding<T extends ObservableValue<T>> {
	private InvalidationListener currentListener;

	public ObservableListenerBinding() {
		super();
	}

	/**
	 * Moves (removes from old and sets to the new) given listener from oldValue
	 * to newValue. Works properly with null values. The listener might by
	 * recreated for each of this call.
	 * 
	 * @param oldValue
	 * @param newValue
	 * @param listener
	 */
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

	/**
	 * Removes given listener from given object.
	 * 
	 * @param listener
	 * @param to
	 */
	protected void unsetListener(InvalidationListener listener, T to) {
		to.removeListener(listener);
	}

	/**
	 * Sets given listener to given object.
	 * 
	 * @param listener
	 * @param to
	 */
	protected void setListener(InvalidationListener listener, T to) {
		to.addListener(listener);
	}

}

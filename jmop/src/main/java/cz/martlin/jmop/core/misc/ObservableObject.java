package cz.martlin.jmop.core.misc;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;

/**
 * Simply {@link ObservableValue} which's value is this object itself.
 * 
 * @author martin
 *
 * @param <T>
 */
public class ObservableObject<T> extends ObservableValueBase<T> {

	public ObservableObject() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue() {
		return (T) this;
	}

}

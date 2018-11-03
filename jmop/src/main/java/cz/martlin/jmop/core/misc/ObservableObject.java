package cz.martlin.jmop.core.misc;

import javafx.beans.value.ObservableValueBase;


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

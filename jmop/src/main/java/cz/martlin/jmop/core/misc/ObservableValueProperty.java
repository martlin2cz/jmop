package cz.martlin.jmop.core.misc;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;

@Deprecated
public class ObservableValueProperty<T extends ObservableValue<T>> extends ObservableValueBase<T> {

	public ObservableValueProperty() {
		super();
	}

	@Override
	public T getValue() {
		return (T) this;
	}


}

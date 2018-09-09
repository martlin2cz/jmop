package cz.martlin.jmop.core.misc;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ObservableValueProperty<T extends ObservableValue<T>> extends SimpleObjectProperty<T> {

	private ChangeListener<T> listener;

	public ObservableValueProperty() {
		super();
	}
	
	@Override
	public void set(T newValue) {
		T currentValue = get();
		if (currentValue != null) {
			currentValue.removeListener(listener);
		}

		super.set(newValue);
		if (newValue != null) {
			newValue.addListener((observable, oldVal, newVal) -> changed());
		}
	}

	private void changed() {
		fireValueChangedEvent();
	}

}

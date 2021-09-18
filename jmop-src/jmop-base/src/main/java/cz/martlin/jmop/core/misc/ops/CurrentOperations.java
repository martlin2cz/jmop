package cz.martlin.jmop.core.misc.ops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cz.martlin.jmop.core.misc.ObservableObject;

public class CurrentOperations extends ObservableObject<CurrentOperations> {
	private final HashMap<String, OperationTask<?, ?>> all;

	public CurrentOperations() {
		this.all = new LinkedHashMap<String, OperationTask<?, ?>>(5);
	}

	public synchronized List<OperationTask<?, ?>> all() {
		return new ArrayList<>(all.values());
	}

	public synchronized OperationTask<?, ?> first() {
		if (all.isEmpty()) {
			return null;
		} else {
			return all.values().iterator().next();
		}
	}

	public synchronized void add(OperationTask<?, ?> operation) {
		String input = operation.getInputAsString();
		all.put(input, operation);

		fireValueChangedEvent();
	}

	public synchronized void remove(OperationTask<?, ?> operation) {
		String input = operation.getInputAsString();
		all.remove(input);

		fireValueChangedEvent();
	}
}

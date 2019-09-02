package cz.martlin.jmop.core.misc.ops;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CurrentOperationsWrapper {
	private final CurrentOperations operations;
	private final StringProperty primaryOperationName;
	private final StringProperty primaryOperationData;
	private final DoubleProperty primaryOperationProgress;
	private final ObservableList<OperationTask<?, ?>> allOperations;

	public CurrentOperationsWrapper(OperationsManager manager) {
		this(manager.getOperations());
	}
	
	public CurrentOperationsWrapper(CurrentOperations operations) {
		super();
		this.operations = operations;
		this.operations.addListener((e) -> operationsChanged());

		this.primaryOperationName = new SimpleStringProperty();
		this.primaryOperationData = new SimpleStringProperty();
		this.primaryOperationProgress = new SimpleDoubleProperty();
		this.allOperations = FXCollections.observableArrayList();
	}

	///////////////////////////////////////////////////////////////////////////

	public ReadOnlyStringProperty primaryOperationName() {
		return primaryOperationName;
	}

	public ReadOnlyStringProperty primaryOperationData() {
		return primaryOperationData;
	}

	public DoubleProperty primaryOperationProgress() {
		return primaryOperationProgress;
	}

	public ObservableList<OperationTask<?, ?>> allOperations() {
		return allOperations;
	}

	///////////////////////////////////////////////////////////////////////////

	private void operationsChanged() {
		OperationTask<?, ?> first = operations.first();

		if (first != null) {
			setFirstToOperation(first);
		} else {
			setFirstToNoOperation();
		}

		List<OperationTask<?, ?>> all = operations.all();
		allOperations.setAll(all);

	}

	private void setFirstToNoOperation() {
		primaryOperationName.set("-");
		primaryOperationData.set("-");
		primaryOperationProgress.unbind();
	}

	private void setFirstToOperation(OperationTask<?, ?> first) {
		String name = first.getName();
		primaryOperationName.set(name);

		String data = first.getInputAsString();
		primaryOperationData.set(data);
		
		//TODO FIXME a bit uneffective ...
		primaryOperationProgress.unbind();
		ReadOnlyDoubleProperty progress = first.progressProperty();
		primaryOperationProgress.bind(progress);
	}

}

package cz.martlin.jmop.core.preparer.operations.base;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OperationWrapper<IT, OT> implements OperationChangeListener {
	private final BaseOperation<IT, OT> operation;
	private final StringProperty status;
	private final StringProperty data;
	private final DoubleProperty progress;

	public OperationWrapper(BaseOperation<IT, OT> operation) {
		super();
		this.operation = operation;
		this.status = new SimpleStringProperty();
		this.data = new SimpleStringProperty();
		this.progress = new SimpleDoubleProperty();
	}

	public ReadOnlyStringProperty statusProperty() {
		return status;
	}

	public ReadOnlyStringProperty dataProperty() {
		return data;
	}

	public ReadOnlyDoubleProperty progressProperty() {
		return progress;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public OT run(IT input) {
		return operation.run(input, this);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void progressChanged(double percentage) {
		Platform.runLater(() -> {
			double progress = percentage / THE_100_PERCENT;
			this.progress.set(progress);
		});
	}

	@Override
	public void updateStatus(String status) {
		Platform.runLater(() -> {
			this.status.set(status);
		});
	}

	@Override
	public void updateData(String data) {
		Platform.runLater(() -> {
			this.data.set(data);
		});
	}

}

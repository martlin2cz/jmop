package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.preparer.operations.base.OperationWrapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;

public class OperationsPane extends HBox {

	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblData;
	@FXML
	private Label lblAnothers;

	private ObservableList<OperationWrapper<?, ?>> operationsProperty;

	private OperationWrapper<?, ?> shownOperation;

	public OperationsPane() throws IOException {
		super();

		initialize();

		changeToNoOperation();
	}

	public ObservableList<OperationWrapper<?, ?>> operationsProperty() {
		return operationsProperty;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	private void initialize() throws IOException {
		loadFXML();
		initializeProperties();
	}

	private void loadFXML() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/OperationsPane.fxml"));
		loader.setController(this);

		Parent root = loader.load();
		getChildren().addAll(root);
	}

	private void initializeProperties() {
		// operationProperty = new SimpleObjectProperty<>();
		// operationProperty.addListener((observable, oldVal, newVal) ->
		// operationChanged(newVal));

		this.operationsProperty = FXCollections.observableArrayList();
		this.operationsProperty.addListener((ListChangeListener<OperationWrapper<?, ?>>) (ch) -> operationsChanged(ch));

	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	private void operationsChanged(Change<? extends OperationWrapper<?, ?>> change) {
		Platform.runLater(() -> {
			change.next();

			if (change.wasAdded()) {
				handleOperationsAdded(change);
			}
			if (change.wasRemoved()) {
				handleOperationsRemoved(change);
			}
		});
	}

	private void handleOperationsAdded(Change<? extends OperationWrapper<?, ?>> change) {
		List<? extends OperationWrapper<?, ?>> operations = change.getList();

		if (shownOperation == null) {
			showFirstOperation(change);
		} else {
			changeAnothers(operations.size());
		}
	}

	private void handleOperationsRemoved(Change<? extends OperationWrapper<?, ?>> change) {
		List<? extends OperationWrapper<?, ?>> operations = change.getList();

		List<? extends OperationWrapper<?, ?>> removedOperations = change.getRemoved();
		if (removedOperations.contains(shownOperation)) {
			changeToNoOperation();
			shownOperation = null;

			if (!operations.isEmpty()) {
				changeToFirstOf(operations);
			}
		} else {
			changeAnothers(operations.size());
		}
	}

	private void showFirstOperation(Change<? extends OperationWrapper<?, ?>> change) {
		List<? extends OperationWrapper<?, ?>> addedOperations = change.getAddedSubList();
		changeToFirstOf(addedOperations);
	}

	private void changeToFirstOf(List<? extends OperationWrapper<?, ?>> addedOperations) {
		OperationWrapper<?, ?> addedOperation = addedOperations.get(0);
		changeToOperation(addedOperation);
		shownOperation = addedOperation;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void changeToOperation(OperationWrapper<?, ?> operation) {
		progressIndicator.progressProperty().bind(operation.progressProperty());
		lblStatus.textProperty().bind(operation.statusProperty());
		lblData.textProperty().bind(operation.dataProperty());

		this.setVisible(true);
	}

	private void changeToNoOperation() {
		this.setVisible(false);
		lblAnothers.setVisible(false);

		progressIndicator.progressProperty().unbind();
		lblStatus.textProperty().unbind();
		lblData.textProperty().unbind();

		lblStatus.textProperty().set("-");
		lblData.textProperty().set("-");
	}

	private void changeAnothers(int operationsCount) {
		if (operationsCount > 1) {
			int anothers = operationsCount - 1;
			lblAnothers.setText(" + " + anothers + " more");
			lblAnothers.setVisible(true);
		} else {
			lblAnothers.setVisible(false);
			lblAnothers.setText(" + no more");
		}
		// TODO: tooltip of their list
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
}

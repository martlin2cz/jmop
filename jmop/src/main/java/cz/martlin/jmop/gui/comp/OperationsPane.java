package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.operation.base.OperationWrapper;
import cz.martlin.jmop.gui.local.Msg;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
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
	private final Logger LOG = LoggerFactory.getLogger(getClass());

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/OperationsPane.fxml")); //$NON-NLS-1$

		loader.setResources(Msg.getResourceBundle());
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
			try {
				change.next();

				if (change.wasAdded()) {
					handleOperationsAdded(change);
				}
				if (change.wasRemoved()) {
					handleOperationsRemoved(change);
				}
			} catch (Exception e) {
				LOG.warn("Could not show operations", e); //$NON-NLS-1$
			}
		});
	}

	private void handleOperationsAdded(Change<? extends OperationWrapper<?, ?>> change) {
		List<? extends OperationWrapper<?, ?>> operations = change.getList();

		if (shownOperation == null) {
			showFirstOperation(change, operations);
		} else {
			changeAnothers(operations.size(), operations);
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
			changeAnothers(operations.size(), operations);
		}
	}

	private void showFirstOperation(Change<? extends OperationWrapper<?, ?>> change,
			List<? extends OperationWrapper<?, ?>> operations) {
		if (operations.isEmpty()) {
			// for case of concurency failure
			return;
		}

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
		lblData.getTooltip().textProperty().bind(operation.dataProperty());

		this.setVisible(true);
	}

	private void changeToNoOperation() {
		this.setVisible(false);
		lblAnothers.setVisible(false);
		lblAnothers.getTooltip().textProperty().unbind();

		progressIndicator.progressProperty().unbind();
		lblStatus.textProperty().unbind();
		lblData.textProperty().unbind();
		lblData.getTooltip().textProperty().unbind();

		lblStatus.textProperty().set("-"); //$NON-NLS-1$
		lblData.textProperty().set("-"); //$NON-NLS-1$
		lblData.getTooltip().setText("-"); //$NON-NLS-1$
	}

	private void changeAnothers(int operationsCount, List<? extends OperationWrapper<?, ?>> operations) {
		if (operationsCount > 1) {
			int anothers = operationsCount - 1;
			lblAnothers.setText(Msg.get("OperationsPane.5") + anothers + Msg.get("OperationsPane.6")); //$NON-NLS-1$ //$NON-NLS-2$
			lblAnothers.setVisible(true);
		} else {
			lblAnothers.setVisible(false);
			lblAnothers.setText(Msg.get("OperationsPane.7")); //$NON-NLS-1$
		}

		if (lblAnothers.getTooltip().textProperty().isBound()) {
			lblAnothers.getTooltip().textProperty().unbind();
		}
		lblAnothers.getTooltip().textProperty().bind(new OperationsListTooltipTextBinding(operations));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public static class OperationsListTooltipTextBinding extends StringBinding {

		private final List<? extends OperationWrapper<?, ?>> operations;

		public OperationsListTooltipTextBinding(List<? extends OperationWrapper<?, ?>> operations) {
			super();

			this.operations = operations;
			bindTheOperations();
		}

		private void bindTheOperations() {
			operations.forEach((o) -> {
				bind(o.statusProperty());
				bind(o.dataProperty());
			});
		}

		@Override
		protected String computeValue() {
			return operations.stream() //
					.map((o) -> o.statusProperty().get() + ": " + o.dataProperty().get()) // //$NON-NLS-1$
					.collect(Collectors.joining("\n")); //$NON-NLS-1$
		}

	}
}

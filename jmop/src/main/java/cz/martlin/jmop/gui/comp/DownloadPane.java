package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.preparer.TrackOperationTask;
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

//TODO RENAME !!! (all inc. the labels and fields)
public class DownloadPane extends HBox {

	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblTrack;
	@FXML
	private Label lblAnothers;

	private ObservableList<OperationWrapper<?, ?>> tasksProperty;

	private OperationWrapper<?, ?> shownTask;

	public DownloadPane() throws IOException {
		super();

		initialize();

		changeToNoTask();
	}

	public ObservableList<OperationWrapper<?, ?>> tasksProperty() {
		return tasksProperty;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	private void initialize() throws IOException {
		loadFXML();
		initializeProperties();
	}

	private void loadFXML() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/DownloadPane.fxml"));
		loader.setController(this);

		Parent root = loader.load();
		getChildren().addAll(root);
	}

	private void initializeProperties() {
		// taskProperty = new SimpleObjectProperty<>();
		// taskProperty.addListener((observable, oldVal, newVal) ->
		// taskChanged(newVal));

		this.tasksProperty = FXCollections.observableArrayList();
		this.tasksProperty.addListener((ListChangeListener<OperationWrapper<?, ?>>) (ch) -> tasksChanged(ch));

	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	private void tasksChanged(Change<? extends OperationWrapper<?, ?>> change) {
		Platform.runLater(() -> {
			change.next();

			if (change.wasAdded()) {
				handleTasksAdded(change);
			}
			if (change.wasRemoved()) {
				handleTasksRemoved(change);
			}
		});
	}

	private void handleTasksAdded(Change<? extends OperationWrapper<?, ?>> change) {
		List<? extends OperationWrapper<?, ?>> tasks = change.getList();

		if (shownTask == null) {
			showFirstTask(change);
		} else {
			changeAnothers(tasks.size());
		}
	}

	private void handleTasksRemoved(Change<? extends OperationWrapper<?, ?>> change) {
		List<? extends OperationWrapper<?, ?>> tasks = change.getList();

		List<? extends OperationWrapper<?, ?>> removedTasks = change.getRemoved();
		if (removedTasks.contains(shownTask)) {
			changeToNoTask();
			shownTask = null;

			if (!tasks.isEmpty()) {
				changeToFirstOf(tasks);
			}
		} else {
			changeAnothers(tasks.size());
		}
	}

	private void showFirstTask(Change<? extends OperationWrapper<?, ?>> change) {
		List<? extends OperationWrapper<?, ?>> addedTasks = change.getAddedSubList();
		changeToFirstOf(addedTasks);
	}

	private void changeToFirstOf(List<? extends OperationWrapper<?, ?>> addedTasks) {
		OperationWrapper<?, ?> addedTask = addedTasks.get(0);
		changeToTask(addedTask);
		shownTask = addedTask;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void changeToTask(OperationWrapper<?, ?> task) {
		progressIndicator.progressProperty().bind(task.progressProperty());
		lblStatus.textProperty().bind(task.statusProperty());
		lblTrack.textProperty().bind(task.dataProperty());

		this.setVisible(true);
	}

	private void changeToNoTask() {
		this.setVisible(false);
		lblAnothers.setVisible(false);

		progressIndicator.progressProperty().unbind();
		lblStatus.textProperty().unbind();
		lblTrack.textProperty().unbind();

		lblStatus.textProperty().set("-");
		lblTrack.textProperty().set("-");
	}

	private void changeAnothers(int tasksCount) {
		if (tasksCount > 1) {
			int anothers = tasksCount - 1;
			lblAnothers.setText(" + " + anothers + " more");
			lblAnothers.setVisible(true);
		} else {
			lblAnothers.setVisible(false);
			lblAnothers.setText(" + no more");
		}
		// TODO: tooltip of their list
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private String obtainTrackLabelText(TrackOperationTask<?, ?> task) {
		return null; //XXX
	}

}

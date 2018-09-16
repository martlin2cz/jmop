package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.download.PreparerTask;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
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
import javafx.util.Duration;

public class DownloadPane extends HBox {

	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblTrack;
	@FXML
	private Label lblAnothers;

	@Deprecated
	private ObjectProperty<PreparerTask> taskProperty;

	private ObservableList<PreparerTask> tasksProperty;

	private PreparerTask shownTask;

	public DownloadPane() throws IOException {
		super();

		initialize();

		changeToNoTask();
	}

	@Deprecated
	public ObjectProperty<PreparerTask> taskProperty() {
		return taskProperty;
	}

	public ObservableList<PreparerTask> tasksProperty() {
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
		this.tasksProperty.addListener((ListChangeListener<PreparerTask>) (ch) -> tasksChanged(ch));

	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	private void tasksChanged(Change<? extends PreparerTask> change) {
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

	private void handleTasksAdded(Change<? extends PreparerTask> change) {
		List<? extends PreparerTask> tasks = change.getList();

		if (shownTask == null) {
			showFirstTask(change);
		} else {
			changeAnothers(tasks.size());
		}
	}

	private void handleTasksRemoved(Change<? extends PreparerTask> change) {
		List<? extends PreparerTask> tasks = change.getList();

		List<? extends PreparerTask> removedTasks = change.getRemoved();
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

	private void showFirstTask(Change<? extends PreparerTask> change) {
		List<? extends PreparerTask> addedTasks = change.getAddedSubList();
		changeToFirstOf(addedTasks);
	}

	private void changeToFirstOf(List<? extends PreparerTask> addedTasks) {
		PreparerTask addedTask = addedTasks.get(0);
		changeToTask(addedTask);
		shownTask = addedTask;
	}

	@Deprecated
	private void taskChanged(PreparerTask newTaskOrNull) {
		if (newTaskOrNull != null) {
			changeToTask(newTaskOrNull);
		} else {
			changeToNoTask();

		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void changeToTask(PreparerTask task) {
		progressIndicator.progressProperty().bind(task.progressProperty());
		lblStatus.textProperty().bind(task.messageProperty());

		String lblTrackText = obtainTrackLabelText(task);
		lblTrack.textProperty().set(lblTrackText);

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

	private String obtainTrackLabelText(PreparerTask task) {
		Track track = task.getTrack();

		String title = track.getTitle();
		Duration duration = track.getDuration();
		String durationStr = DurationUtilities.toHumanString(duration);

		return title + " (" + durationStr + ")";
	}

}

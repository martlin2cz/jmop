package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
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
	private ObjectProperty<DownloaderTask> taskProperty;

	private ObservableList<DownloaderTask> tasksProperty;

	private DownloaderTask shownTask;

	public DownloadPane() throws IOException {
		super();

		initialize();

		changeToNoTask();
	}

	@Deprecated
	public ObjectProperty<DownloaderTask> taskProperty() {
		return taskProperty;
	}

	public ObservableList<DownloaderTask> tasksProperty() {
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
		this.tasksProperty.addListener((ListChangeListener<DownloaderTask>) (ch) -> tasksChanged(ch));

	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	private void tasksChanged(Change<? extends DownloaderTask> change) {
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

	private void handleTasksAdded(Change<? extends DownloaderTask> change) {
		List<? extends DownloaderTask> tasks = change.getList();

		if (shownTask == null) {
			showFirstTask(change);
		} else {
			changeAnothers(tasks.size());
		}
	}

	private void handleTasksRemoved(Change<? extends DownloaderTask> change) {
		List<? extends DownloaderTask> tasks = change.getList();

		List<? extends DownloaderTask> removedTasks = change.getRemoved();
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

	private void showFirstTask(Change<? extends DownloaderTask> change) {
		List<? extends DownloaderTask> addedTasks = change.getAddedSubList();
		changeToFirstOf(addedTasks);
	}

	private void changeToFirstOf(List<? extends DownloaderTask> addedTasks) {
		DownloaderTask addedTask = addedTasks.get(0);
		changeToTask(addedTask);
		shownTask = addedTask;
	}

	@Deprecated
	private void taskChanged(DownloaderTask newTaskOrNull) {
		if (newTaskOrNull != null) {
			changeToTask(newTaskOrNull);
		} else {
			changeToNoTask();

		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void changeToTask(DownloaderTask task) {
		System.out.println("Showing task " + task.getTrack().getTitle());
		progressIndicator.progressProperty().bind(task.progressProperty());
		lblStatus.textProperty().bind(task.messageProperty());

		String lblTrackText = obtainTrackLabelText(task);
		lblTrack.textProperty().set(lblTrackText);

		this.setVisible(true);
	}

	private void changeToNoTask() {
		System.out.println("Showing NO task");
		this.setVisible(false);
		lblAnothers.setVisible(false);

		progressIndicator.progressProperty().unbind();
		lblStatus.textProperty().unbind();
		lblTrack.textProperty().unbind();

		lblStatus.textProperty().set("-");
		lblTrack.textProperty().set("-");
	}

	private void changeAnothers(int tasksCount) {
		System.out.println("Updating count, total:" + tasksCount);
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

	private String obtainTrackLabelText(DownloaderTask task) {
		Track track = task.getTrack();

		String title = track.getTitle();
		Duration duration = track.getDuration();
		String durationStr = DurationUtilities.toHumanString(duration);

		return title + " (" + durationStr + ")";
	}

}

package cz.martlin.jmop.gui.comp;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class DownloadPane extends HBox {

	private ProgressBar progressIndicator; //TODO progress indicator here, but needs better layout
	private Label lblStatus;
	private Label lblTrack;
	private ObjectProperty<DownloaderTask> taskProperty;

	public DownloadPane() {	
		super();

		initializeComponents();
		initializeProperties();

		changeToNoTask();
	}

	public ObjectProperty<DownloaderTask> taskProperty() {
		return taskProperty;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void initializeComponents() {
		progressIndicator = new ProgressBar();
		this.getChildren().add(progressIndicator);

		lblStatus = new Label("-");
		this.getChildren().add(lblStatus);

		lblTrack = new Label("-");
		this.getChildren().add(lblTrack);
	}

	private void initializeProperties() {
		taskProperty = new SimpleObjectProperty<>();
		taskProperty.addListener((observable, oldVal, newVal) -> taskChanged(newVal));
	}

	private void taskChanged(DownloaderTask newTaskOrNull) {
		if (newTaskOrNull != null) {
			changeToTask(newTaskOrNull);
		} else {
			changeToNoTask();

		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void changeToTask(DownloaderTask task) {
		progressIndicator.progressProperty().bind(task.progressProperty());
		lblStatus.textProperty().bind(task.messageProperty());

		String lblTrackText = obtainTrackLabelText(task);
		lblTrack.textProperty().set(lblTrackText);

		// TODO this.visible := true
	}

	private void changeToNoTask() {
		// TODO this.visible := false

		progressIndicator.progressProperty().unbind();
		lblStatus.textProperty().unbind();
		lblTrack.textProperty().unbind();

		lblStatus.textProperty().set("-");
		lblTrack.textProperty().set("-");
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

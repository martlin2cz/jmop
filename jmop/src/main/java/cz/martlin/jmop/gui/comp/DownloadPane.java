package cz.martlin.jmop.gui.comp;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class DownloadPane extends GridPane {

	private static final double PRG_MIN_SIZE = 20;
	private ProgressIndicator progressIndicator; 
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
		progressIndicator = new ProgressIndicator();
		progressIndicator.setMinHeight(PRG_MIN_SIZE);
		progressIndicator.setMinHeight(PRG_MIN_SIZE);
		this.add(progressIndicator, 0, 0, 1, 2);

		lblStatus = new Label("-");
		this.add(lblStatus, 1, 0);

		lblTrack = new Label("-");
		this.add(lblTrack, 1, 1);
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

		this.setVisible(true);
	}

	private void changeToNoTask() {
		this.setVisible(false);

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

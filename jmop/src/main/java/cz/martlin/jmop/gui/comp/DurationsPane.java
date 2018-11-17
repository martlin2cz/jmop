package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class DurationsPane extends HBox implements Initializable {
	@FXML
	private Label lblDone;
	@FXML
	private Label lblRemaining;

	private final ObjectProperty<Duration> currentTime;
	private final ObjectProperty<Duration> totalTime;

	public DurationsPane() throws IOException {
		this.currentTime = new SimpleObjectProperty<>();
		this.totalTime = new SimpleObjectProperty<>();

		load();
	}

	public ObjectProperty<Duration> currentTimeProperty() {
		return currentTime;
	}

	public ObjectProperty<Duration> totalTimeProperty() {
		return totalTime;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initBindings();
	}

	private void initBindings() {
		totalTime.addListener((observable, oldVal, newVal) -> totalTimeChanged(newVal));
		currentTime.addListener((observable, oldVal, newVal) -> currentTimeChanged(newVal));
	}

	private void load() throws IOException {
		loadFXML();
	}

	private void loadFXML() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/DurationsPane.fxml")); //$NON-NLS-1$

		loader.setController(this);
		Parent root = loader.load();
		getChildren().addAll(root);
	}

	///////////////////////////////////////////////////////////////////////////
	private void currentTimeChanged(Duration currentTime) {
		displayDurations();
	}

	private void totalTimeChanged(Duration totalTime) {
		displayDurations();
	}

	private void displayDurations() {
		Platform.runLater(() -> {

			Duration currentTime = this.currentTime.get();
			Duration totalTime = this.totalTime.get();

			Duration remainingTime;
			if (currentTime != null && totalTime != null) {
				remainingTime = remaining(currentTime, totalTime);
			} else {
				remainingTime = null;
			}

			String currentStr;
			if (currentTime != null) {
				currentStr = DurationUtilities.toHumanString(currentTime);
			} else {
				currentStr = "-"; //$NON-NLS-1$
			}
			lblDone.setText(currentStr);

			String remainingStr;
			if (remainingTime != null) {
				remainingStr = DurationUtilities.toHumanString(remainingTime);
			} else {
				remainingStr = "-"; //$NON-NLS-1$
			}
			lblRemaining.setText(remainingStr);
		});
	}

	private static Duration remaining(Duration currentTime, Duration totalTime) {
		return totalTime.subtract(currentTime);
	}

}

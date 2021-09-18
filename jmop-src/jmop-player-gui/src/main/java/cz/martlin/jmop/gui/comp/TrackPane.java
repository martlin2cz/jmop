package cz.martlin.jmop.gui.comp;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.gui.local.Msg;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TrackPane extends VBox {

	private static final Tooltip NO_TRACK_TOOLTIP = new Tooltip(Msg.get("No_track")); //$NON-NLS-1$

	private Label lblTitle;
	private Label lblDuration;
	private ObjectProperty<Track> trackProperty;

	public TrackPane() {
		super();

		initializeComponents();
		initializeProperties();

		changeToNoTrack();
	}

	public ObjectProperty<Track> trackProperty() {
		return trackProperty;
	}

	public void setFont(Font font) {
		lblTitle.setFont(font);
		lblDuration.setFont(font);
	}

	public Font getFont() {
		return lblTitle.getFont();
	}

	///////////////////////////////////////////////////////////////////////////

	private void initializeComponents() {
		lblTitle = new Label();
		this.getChildren().add(lblTitle);

		lblDuration = new Label();
		this.getChildren().add(lblDuration);
	}

	private void initializeProperties() {
		trackProperty = new SimpleObjectProperty<>();
		trackProperty.addListener((observable, oldVal, newVal) -> trackChanged(newVal));
	}

	///////////////////////////////////////////////////////////////////////////

	private void trackChanged(Track newTrack) {
		Platform.runLater(() -> {
			if (newTrack != null) {
				changeToTrack(newTrack);
			} else {
				changeToNoTrack();
			}
		});
	}

	private void changeToNoTrack() {
		lblTitle.setText(Msg.get("No_track")); //$NON-NLS-1$
		lblDuration.setText(""); //$NON-NLS-1$

		lblTitle.setDisable(true);
		lblDuration.setDisable(true);

		lblTitle.setTooltip(NO_TRACK_TOOLTIP);
	}

	private void changeToTrack(Track newTrack) {
		String title = newTrack.getTitle();
		lblTitle.setText(title);

		String duration = DurationUtilities.toHumanString(newTrack.getDuration());
		lblDuration.setText(duration);

		lblTitle.setDisable(false);
		lblDuration.setDisable(false);

		String description = newTrack.getDescription();
		Tooltip tooltip = new Tooltip(description);
		lblTitle.setTooltip(tooltip);
	}
}

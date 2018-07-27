package cz.martlin.jmop.gui.comp;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TrackPane extends VBox {

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
		if (newTrack != null) {
			changeToTrack(newTrack);
		} else {
			changeToNoTrack();
		}
	}

	private void changeToNoTrack() {
		lblTitle.setText("No track");
		lblDuration.setText("No duration");
	}

	private void changeToTrack(Track newTrack) {
		String title = newTrack.getTitle();
		lblTitle.setText(title);
		// TODO description as tooltip?

		String duration = DurationUtilities.toHumanString(newTrack.getDuration());
		lblDuration.setText(duration);
	}
}

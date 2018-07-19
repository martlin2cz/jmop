package cz.martlin.jmop.gui.util;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public interface MediaPlayerGuiReporter {
	public StringProperty trackNameProperty();
	public Property<Duration> durationProperty();
	public Property<Status> statusProperty();
}

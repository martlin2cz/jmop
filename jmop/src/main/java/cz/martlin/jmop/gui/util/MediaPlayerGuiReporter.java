package cz.martlin.jmop.gui.util;

import cz.martlin.jmop.core.wrappers.CoreGuiDescriptor;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
/**
 * Replaced by {@link CoreGuiDescriptor}. Hopefully ....
 * @author martin
 *
 */
@Deprecated
public interface MediaPlayerGuiReporter {
	public StringProperty trackNameProperty();
	public Property<Duration> durationProperty();
	public Property<Status> statusProperty();
}

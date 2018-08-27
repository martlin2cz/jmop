package cz.martlin.jmop.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

@Deprecated
public interface DownloadGuiReporter {

	public BooleanProperty runningProperty();

	public DoubleProperty progressProperty();

	public StringProperty statusProperty();

}

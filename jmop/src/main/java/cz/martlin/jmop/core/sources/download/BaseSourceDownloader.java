package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.tracks.Track;
import javafx.beans.property.DoubleProperty;

public interface BaseSourceDownloader {

	public boolean download(Track track) throws Exception;

	public void stop() throws Exception;
	
	public DoubleProperty getProgressPercentProperty();
}

package cz.martlin.jmop.core.sources.local.location;

public interface AbstractTrackFileLocator {
	public TrackFileLocation locationOfDownload();
	public TrackFileLocation locationOfSave();
	public TrackFileLocation locationOfPlay();
	
}

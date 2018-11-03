package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;

public interface AbstractTrackFileLocator {
	public TrackFileLocation locationOfDownload(BaseSourceDownloader downloader);
	public TrackFileLocation locationOfSave();
	public TrackFileLocation locationOfPlay(BasePlayer player);
	
}

package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;

public class PrimitiveLocator implements AbstractTrackFileLocator {

	@Override
	public TrackFileLocation locationOfDownload(BaseSourceDownloader downloader) {
		return TrackFileLocation.TEMP;
	}

	@Override
	public TrackFileLocation locationOfSave() {
		return TrackFileLocation.SAVE;
	}

	@Override
	public TrackFileLocation locationOfPlay(BasePlayer player) {
		return TrackFileLocation.CACHE;
	}

}

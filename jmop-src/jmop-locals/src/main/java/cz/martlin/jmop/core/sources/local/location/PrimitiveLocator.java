package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;

/**
 * The primitive locatior simply indicates to download everything into temp
 * directory, save into save directory, and prepare to play into cache
 * directory.
 * 
 * @author martin
 *
 */
public class PrimitiveLocator implements BaseTrackFileLocator {

	@Override
	public TrackFileLocation locationOfDownload(BaseDownloader downloader) {
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

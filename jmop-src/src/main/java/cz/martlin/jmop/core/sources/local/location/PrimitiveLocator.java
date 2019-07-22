package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceDownloader;

/**
 * The primitive locatior simply indicates to download everything into temp
 * directory, save into save directory, and prepare to play into cache
 * directory.
 * 
 * @author martin
 *
 */
public class PrimitiveLocator implements AbstractTrackFileLocator {

	@Override
	public TrackFileLocation locationOfDownload(XXX_BaseSourceDownloader downloader) {
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

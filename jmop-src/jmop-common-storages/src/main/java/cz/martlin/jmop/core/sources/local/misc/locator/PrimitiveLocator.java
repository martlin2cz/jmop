package cz.martlin.jmop.core.sources.local.misc.locator;

import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

/**
 * The primitive locatior simply indicates to download everything into temp
 * directory, save into save directory, and prepare to play into cache
 * directory.
 * 
 * @author martin
 *
 * @deprecated do not use
 */
@Deprecated
public class PrimitiveLocator implements BaseTrackFileLocator {

	@Override
	public TrackFileLocation locationOfDownload(Object downloader) {
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

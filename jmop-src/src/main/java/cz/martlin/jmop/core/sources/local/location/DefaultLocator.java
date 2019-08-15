package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;

/**
 * The default locator is quite improoved {@link PrimitiveLocator}. If format of
 * downloader (player) is equal to save format given by configuration, downloads
 * (prepares to play) simply directly into the save location.
 * 
 * @author martin
 *
 */
public class DefaultLocator implements BaseTrackFileLocator {

	private final TrackFileFormat saveFormat;

	public DefaultLocator(BaseConfiguration config) {
		super();
		this.saveFormat = config.getSaveFormat();
	}

	@Override
	public TrackFileLocation locationOfDownload(BaseDownloader downloader) {
		TrackFileFormat downloadFormat = downloader.downloadFormat();

		if (downloadFormat.equals(saveFormat)) {
			return TrackFileLocation.SAVE;
		} else {
			return TrackFileLocation.TEMP;
		}
	}

	@Override
	public TrackFileLocation locationOfSave() {
		return TrackFileLocation.SAVE;
	}

	@Override
	public TrackFileLocation locationOfPlay(BasePlayer player) {
		TrackFileFormat playerFormat = player.getPlayableFormat();

		if (playerFormat.equals(saveFormat)) {
			return TrackFileLocation.SAVE;
		} else {
			return TrackFileLocation.CACHE;
		}
	}

}

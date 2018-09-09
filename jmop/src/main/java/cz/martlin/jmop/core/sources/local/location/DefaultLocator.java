package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class DefaultLocator implements AbstractTrackFileLocator {

	private final TrackFileFormat saveFormat;

	public DefaultLocator(BaseConfiguration config) {
		super();
		this.saveFormat = config.getSaveFormat();
	}

	@Override
	public TrackFileLocation locationOfDownload(BaseSourceDownloader downloader) {
		TrackFileFormat downloadFormat = downloader.formatOfDownload();

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

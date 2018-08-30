package cz.martlin.jmop.core.sources.local.location;

import cz.martlin.jmop.core.config.Configuration;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class DefaultLocator implements AbstractTrackFileLocator{

	private final TrackFileFormat downloadFormat;
	private final TrackFileFormat saveFormat;
	private final TrackFileFormat playerFormat;
	
	public DefaultLocator(Configuration config, BaseSourceDownloader downloader, BasePlayer player) {
		super();
		this.downloadFormat = downloader.formatOfDownload();
		this.saveFormat = config.getSaveFormat();
		this.playerFormat = player.getPlayableFormat();
	}
	
	@Override
	public TrackFileLocation locationOfDownload() {
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
	public TrackFileLocation locationOfPlay() {
		if (playerFormat.equals(saveFormat)) {
			return TrackFileLocation.SAVE;
		} else {
			return TrackFileLocation.CACHE;
		}
	}

}

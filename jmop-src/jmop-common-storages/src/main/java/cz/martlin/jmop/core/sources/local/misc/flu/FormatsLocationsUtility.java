package cz.martlin.jmop.core.sources.local.misc.flu;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.local.misc.locator.BaseTrackFileLocator;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;

public class FormatsLocationsUtility {
	private final BaseConfiguration config;
	private final BaseTrackFileLocator locator;
	private final BaseDownloader downloader;
	private final BasePlayer player;

	public FormatsLocationsUtility(BaseConfiguration config, BaseTrackFileLocator locator, BaseDownloader downloader,
			BasePlayer player) {
		super();
		this.config = config;
		this.locator = locator;
		this.downloader = downloader;
		this.player = player;
	}

	public TrackFileFormat downloadFormat() {
		return downloader.downloadFormat();
	}

	public TrackFileFormat saveFormat() {
		return config.getSaveFormat();
	}

	public TrackFileFormat playFormat() {
		return player.getPlayableFormat();
	}

	public TrackFileLocation downloadLocation() {
		return locator.locationOfDownload(downloader);
	}

	public TrackFileLocation saveLocation() {
		return locator.locationOfSave();
	}

	public TrackFileLocation playLocation() {
		return locator.locationOfPlay(player);
	}

}

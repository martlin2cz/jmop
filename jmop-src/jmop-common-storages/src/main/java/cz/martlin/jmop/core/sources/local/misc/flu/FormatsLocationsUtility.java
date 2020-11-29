package cz.martlin.jmop.core.sources.local.misc.flu;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.local.misc.locator.BaseTrackFileLocator;

/**
 * @deprecated no more needed
 * @author martin
 *
 */
@Deprecated
public class FormatsLocationsUtility {
	private final BaseConfiguration config;
	private final BaseTrackFileLocator locator;
	private final Object downloader;
	private final BasePlayer player;

	public FormatsLocationsUtility(BaseConfiguration config, BaseTrackFileLocator locator, Object downloader,
			BasePlayer player) {
		super();
		this.config = config;
		this.locator = locator;
		this.downloader = downloader;
		this.player = player;
	}

	public TrackFileFormat downloadFormat() {
		throw new UnsupportedOperationException("downloader");
//		return downloader.downloadFormat();
	}

	public TrackFileFormat saveFormat() {
		return config.getSaveFormat();
	}

	public TrackFileFormat playFormat() {
		return player.getPlayableFormat();
	}

	public TrackFileLocation downloadLocation() {
		throw new UnsupportedOperationException("downloader");
//		return locator.locationOfDownload(downloader);
	}

	public TrackFileLocation saveLocation() {
		return locator.locationOfSave();
	}

	public TrackFileLocation playLocation() {
		return locator.locationOfPlay(player);
	}

}

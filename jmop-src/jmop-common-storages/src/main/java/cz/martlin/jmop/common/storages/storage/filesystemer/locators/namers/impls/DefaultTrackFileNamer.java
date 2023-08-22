package cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.impls;

import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BaseTrackFileNamer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The default track files namer. The track file name consists of the title and
 * extension.
 * 
 * @author martin
 *
 */
public class DefaultTrackFileNamer implements BaseTrackFileNamer {

	private final TrackFileFormat saveFormat;

	public DefaultTrackFileNamer(TrackFileFormat saveFormat) {
		super();
		this.saveFormat = saveFormat;
	}

	@Override
	public String trackFileName(String bundleName, String trackTitle) {
		return trackTitle + "." + saveFormat.fileExtension();
	}

}

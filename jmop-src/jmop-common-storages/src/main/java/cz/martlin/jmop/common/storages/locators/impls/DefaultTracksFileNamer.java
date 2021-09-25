package cz.martlin.jmop.common.storages.locators.impls;

import cz.martlin.jmop.common.storages.locators.BaseTrackFilesNamer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The default track files namer. The track file name consists of the title and
 * extension.
 * 
 * @author martin
 *
 */
public class DefaultTracksFileNamer implements BaseTrackFilesNamer {

	private final TrackFileFormat saveFormat;

	public DefaultTracksFileNamer(TrackFileFormat saveFormat) {
		super();
		this.saveFormat = saveFormat;
	}

	@Override
	public String trackFileName(String bundleName, String trackTitle) {
		return trackTitle + "." + saveFormat.fileExtension();
	}

}

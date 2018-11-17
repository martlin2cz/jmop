package cz.martlin.jmop.core.sources.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

/**
 * Converter doing simply no conversion, or even nothing.
 * @author martin
 *
 */
public class NoopConverter implements BaseSourceConverter {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public NoopConverter() {
		super();
	}

	@Override
	public boolean convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat) throws Exception {

		LOG.info("NOT Converting track " + track); //$NON-NLS-1$

		return true;
	}

	@Override
	public void specifyListener(ProgressListener listener) {
		// nothing
	}

	@Override
	public boolean check() {
		return true;
	}

}

package cz.martlin.jmop.core.sources.remote.empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.XXX_ProgressListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceConverter;

/**
 * Converter doing simply no conversion, or even nothing.
 * 
 * @author martin
 *
 */
public class NoopConverter implements XXX_BaseSourceConverter {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public NoopConverter() {
		super();
	}

	@Override
	public boolean convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat, XXX_ProgressListener listener) throws Exception {

		LOG.info("NOT Converting track " + track); //$NON-NLS-1$

		return true;
	}
/*
	@Override
	public void specifyListener(ProgressListener listener) {
		// nothing
	}
*/
	@Override
	public boolean check() {
		return true;
	}

}

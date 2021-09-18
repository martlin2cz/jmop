package cz.martlin.jmop.core.sources.remote.empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.ConversionReason;

/**
 * Converter doing simply no conversion, or even nothing.
 * 
 * @author martin
 *
 */
public class NoopConverter implements BaseConverter {

	
	public NoopConverter() {
		super();
	}

	@Override
	public BaseLongOperation<Track, Track> convert(Track track, TrackFileLocation fromLocation,
			TrackFileFormat fromFormat, TrackFileLocation toLocation, TrackFileFormat toFormat, ConversionReason reason)
			throws JMOPSourceException {

		return new NotConvertingLongOperation(track, fromLocation, fromFormat, toLocation, toFormat, reason);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public static class NotConvertingLongOperation implements BaseLongOperation<Track, Track> {

		private final Logger LOG = LoggerFactory.getLogger(getClass());

		
		private final Track track;
		private final ConversionReason reason;

		public NotConvertingLongOperation(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
				TrackFileLocation toLocation, TrackFileFormat toFormat, ConversionReason reason) {
			super();
			this.track = track;
			this.reason = reason;
		}

		@Override
		public String getName() {
			return reason.getHumanName();
		}

		@Override
		public String getInputDataAsString() {
			return track.toHumanString();
		}

		@Override
		public Track run(BaseProgressListener listener) throws Exception {
			LOG.info("NOT Converting track " + track); //$NON-NLS-1$

			return null;
		}

		@Override
		public void reportProgress(BaseProgressListener listener, double progress) {
			listener.reportProgressChanged(progress);
		}

		@Override
		public void terminate() {
			// nothing needed
		}

	}
}

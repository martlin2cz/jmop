package cz.martlin.jmop.core.misc.ops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSource;
import cz.martlin.jmop.core.sources.remote.ConversionReason;

public class PrepareTrackFilesOperationChain extends AbstractTriPhasedOperationsChain<Track> {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final FormatsLocationsUtility flu;
	private final BaseRemoteSource remote;
	private final XXX_BaseLocalSource local;

	public PrepareTrackFilesOperationChain(FormatsLocationsUtility flu, BaseRemoteSource remote,
			XXX_BaseLocalSource local) {
		super();
		this.flu = flu;
		this.remote = remote;
		this.local = local;
	}

	@Override
	protected BaseOperation<Track, Track> createFirstOperation(Track track) throws JMOPSourceException {
		logDebugInfo("first", track);

		if (!isDownloaded(track)) {
			return runDownload(track);
		} else {
			return new EmptyOperation<>(track);
		}
	}

	@Override
	protected BaseOperation<Track, Track> createSecondOperation(Track track) throws JMOPSourceException {
		logDebugInfo("second", track);

		if (isSaved(track)) {
			return new EmptyOperation<>(track);
		} else {
			if (isDownloaded(track)) {
				return runConvertDownloadedToSave(track);
			} else {
				throw new IllegalStateException("Nor downloaded or saved");
			}
		}
	}

	@Override
	protected BaseOperation<Track, Track> createThirdOperation(Track track) throws JMOPSourceException {
		logDebugInfo("third", track);

		if (isToPlay(track)) {
			return new EmptyOperation<>(track);
		} else {
			if (isDownloaded(track)) {
				return runConvertDownloadedToPlay(track);
			} else {
				return runConvertSavedToPlay(track);
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////

	private BaseOperation<Track, Track> runDownload(Track track) throws JMOPSourceException {
		LOG.info("Preparing download of track: " + track);

		BaseDownloader downloader = remote.downloader();
		TrackFileLocation location = flu.downloadLocation();

		BaseOperation<Track, Track> operation = downloader.download(track, location);
		return operation;
	}

	private BaseOperation<Track, Track> runConvertDownloadedToSave(Track track) throws JMOPSourceException {
		LOG.info("Preparing conversion of downloaded to save track: " + track);

		BaseConverter converter = remote.converter();
		TrackFileLocation fromLocation = flu.downloadLocation();
		TrackFileFormat fromFormat = flu.downloadFormat();

		TrackFileLocation toLocation = flu.saveLocation();
		TrackFileFormat toFormat = flu.saveFormat();
		ConversionReason reason = ConversionReason.CONVERT;

		BaseOperation<Track, Track> operation = converter.convert(track, fromLocation, fromFormat, toLocation, toFormat,
				reason);
		return operation;
	}

	private BaseOperation<Track, Track> runConvertDownloadedToPlay(Track track) throws JMOPSourceException {
		LOG.info("Preparing conversion of downloaded to play track: " + track);

		BaseConverter converter = remote.converter();
		TrackFileLocation fromLocation = flu.downloadLocation();
		TrackFileFormat fromFormat = flu.downloadFormat();

		TrackFileLocation toLocation = flu.playLocation();
		TrackFileFormat toFormat = flu.playFormat();
		ConversionReason reason = ConversionReason.PREPARE_TO_PLAY;

		BaseOperation<Track, Track> operation = converter.convert(track, fromLocation, fromFormat, toLocation, toFormat,
				reason);
		return operation;
	}

	private BaseOperation<Track, Track> runConvertSavedToPlay(Track track) throws JMOPSourceException {
		LOG.info("Preparing conversion of saved to play track: " + track);

		BaseConverter converter = remote.converter();
		TrackFileLocation fromLocation = flu.saveLocation();
		TrackFileFormat fromFormat = flu.saveFormat();

		TrackFileLocation toLocation = flu.playLocation();
		TrackFileFormat toFormat = flu.playFormat();
		ConversionReason reason = ConversionReason.PREPARE_TO_PLAY;

		BaseOperation<Track, Track> operation = converter.convert(track, fromLocation, fromFormat, toLocation, toFormat,
				reason);
		return operation;
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	private boolean isDownloaded(Track track) throws JMOPSourceException {
		return local.exists(track, flu.downloadLocation(), flu.downloadFormat());
	}

	private boolean isSaved(Track track) throws JMOPSourceException {
		return local.exists(track, flu.saveLocation(), flu.saveFormat());
	}

	private boolean isToPlay(Track track) throws JMOPSourceException {
		return local.exists(track, flu.playLocation(), flu.playFormat());
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	private void logDebugInfo(String operation, Track track) throws JMOPSourceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Preparing " + operation //
					+ " for track: " + track //
					+ " Downloaded? " + isDownloaded(track) //
					+ " Saved? " + isSaved(track) //
					+ " To play? " + isToPlay(track) //
			);
		}
	}

	@Override
	public String toString() {
		return "PrepareTrackFilesOperationChain []";
	}

}

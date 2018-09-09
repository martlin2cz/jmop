package cz.martlin.jmop.core.preparer.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.misc.TextualStatusUpdateListener;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.TrackFileFormatLocationPreparer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public class DownloadAndConvertOperation extends BaseTrackOperation<Track, Track> {
	private static final String NAME = "Download and convert";
	
	private final BaseLocalSource local;
	private final BaseSourceDownloader downloader;
	private final TrackFileFormatLocationPreparer preparer;

	private final TrackFileLocation downloadLocation;
	private final TrackFileLocation saveLocation;
	private final TrackFileLocation playerLocation;

	private final TrackFileFormat downloadFormat;
	private final TrackFileFormat saveFormat;
	private final TrackFileFormat playerFormat;

	public DownloadAndConvertOperation(BaseConfiguration config, AbstractTrackFileLocator locator, BaseLocalSource local,
			BaseSourceDownloader downloader, BaseSourceConverter converter, BasePlayer player) {
		super(NAME);

		this.local = local;
		this.downloader = downloader;

		this.preparer = new TrackFileFormatLocationPreparer(local, converter);
		this.downloadLocation = locator.locationOfDownload(downloader);
		this.saveLocation = locator.locationOfSave();
		this.playerLocation = locator.locationOfPlay(player);

		this.downloadFormat = downloader.formatOfDownload();
		this.saveFormat = config.getSaveFormat();
		this.playerFormat = player.getPlayableFormat();
		
		
	}
	
	

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected Track runInternal(Track input, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws Exception {

		downloader.specifyListener(progressListener);
		preparer.specifyListener(progressListener);
		
		boolean downloaded = checkAndDownload(input, progressListener, statusListener);
		if (!downloaded) {
			return input;
		}

		boolean converted = checkAndConvert(input, progressListener, statusListener);
		if (!converted) {
			return input;
		}

		boolean prepared = checkAndPrepare(input, progressListener, statusListener);
		if (!prepared) {
			return input;
		}

		//FIXME wont be invoked if return belowe is
		downloader.specifyListener(null);
		preparer.specifyListener(null);
		
		return input;
	}

	private boolean checkAndDownload(Track track, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws Exception {

		startSubTask("Downloading ...", progressListener, statusListener);

		boolean ready = existsSaved(track) && existsToPlay(track);
		if (ready) {
			return true;
		}

		boolean existsDownloaded = existsDownloaded(track);
		if (existsDownloaded) {
			return true;
		}

		boolean downloaded = downloader.download(track, downloadLocation);
		return downloaded;
	}

	private boolean checkAndConvert(Track track, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws Exception {

		startSubTask("Converting ...", progressListener, statusListener);

		boolean exists = existsSaved(track);
		if (exists) {
			return true;
		}

		boolean converted = preparer.prepare(track, downloadFormat, downloadLocation, saveFormat, saveLocation);
		return converted;
	}

	private boolean checkAndPrepare(Track track, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws Exception {

		startSubTask("Preparing to play ...", progressListener, statusListener);

		boolean exists = existsToPlay(track);
		if (exists) {
			return true;
		}

		boolean prepared = preparer.prepare(track, downloadFormat, downloadLocation, playerFormat, playerLocation);
		return prepared;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private boolean existsDownloaded(Track track) throws JMOPSourceException {
		return local.exists(track, downloadLocation, downloadFormat);
	}

	private boolean existsSaved(Track track) throws JMOPSourceException {
		return local.exists(track, saveLocation, saveFormat);
	}

	private boolean existsToPlay(Track track) throws JMOPSourceException {
		return local.exists(track, playerLocation, playerFormat);
	}

}

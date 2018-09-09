package cz.martlin.jmop.core.sources.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.config.DefaultConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import javafx.concurrent.Task;
@Deprecated
public class PreparerTask extends Task<Boolean> implements ProgressListener {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseLocalSource local;
	private final BaseSourceDownloader downloader;
	private final TrackFileFormatLocationPreparer preparer;

	private final TrackFileLocation downloadLocation;
	private final TrackFileLocation saveLocation;
	private final TrackFileLocation playerLocation;

	private final TrackFileFormat downloadFormat;
	private final TrackFileFormat saveFormat;
	private final TrackFileFormat playerFormat;

	private final Track track;

	public PreparerTask(DefaultConfiguration config, AbstractTrackFileLocator locator, BaseLocalSource local,
			BaseSourceDownloader downloader, BaseSourceConverter converter, BasePlayer player, Track track) {
		super();
		this.local = local;
		this.downloader = downloader;

		this.track = track;

		this.preparer = new TrackFileFormatLocationPreparer(local, converter);
		this.downloadLocation = locator.locationOfDownload(downloader);
		this.saveLocation = locator.locationOfSave();
		this.playerLocation = locator.locationOfPlay(player);

		this.downloadFormat = downloader.formatOfDownload();
		this.saveFormat = config.getSaveFormat();
		this.playerFormat = player.getPlayableFormat();

		downloader.specifyListener(this);
		converter.specifyListener(this);
	}

	public Track getTrack() {
		return track;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected Boolean call() throws Exception {
		try {
			boolean downloaded = checkAndDownload();
			if (!downloaded) {
				return false;
			}

			boolean converted = checkAndConvert();
			if (!converted) {
				return false;
			}

			boolean prepared = checkAndPrepare();
			if (!prepared) {
				return false;
			}

			updateProgress(THE_100_PERCENT, THE_100_PERCENT);
			updateMessage("Done.");
			return true;
		} catch (Exception e) {
			throw new JMOPSourceException(e);
		}
	}

	private boolean checkAndDownload() throws Exception {
		startSubTask("Downloading ...");
		boolean ready = existsSaved() && existsToPlay();
		if (ready) {
			return true;
		}
		
		boolean existsDownloaded = existsDownloaded();
		if (existsDownloaded) {
			return true;
		}

		boolean downloaded = downloader.download(track, downloadLocation);
		return downloaded;
	}

	private boolean checkAndConvert() throws Exception {
		startSubTask("Converting ...");
		boolean exists = existsSaved();
		if (exists) {
			return true;
		}

		boolean converted = preparer.prepare(track, downloadFormat, downloadLocation, saveFormat, saveLocation);
		return converted;
	}

	private boolean checkAndPrepare() throws Exception {
		startSubTask("Preparing to play ...");
		boolean exists = existsToPlay();
		if (exists) {
			return true;
		}

		boolean prepared = preparer.prepare(track, downloadFormat, downloadLocation, playerFormat, playerLocation);
		return prepared;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	private boolean existsDownloaded() throws JMOPSourceException {
		return local.exists(track, downloadLocation, downloadFormat);
	}

	private boolean existsSaved() throws JMOPSourceException {
		return local.exists(track, saveLocation, saveFormat);
	}

	private boolean existsToPlay() throws JMOPSourceException {
		return local.exists(track, playerLocation, playerFormat);
	}

	private void startSubTask(String name) {
		LOG.info(name + " track " + track.getTitle());

		updateProgress(0.0, THE_100_PERCENT);
		updateMessage(name);
	}

	@Override
	public void progressChanged(double percentage) {
		updateProgress(percentage, THE_100_PERCENT);
	}

}

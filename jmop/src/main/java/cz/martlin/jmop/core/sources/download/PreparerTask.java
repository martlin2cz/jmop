package cz.martlin.jmop.core.sources.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.config.Configuration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.concurrent.Task;

public class PreparerTask extends Task<Boolean> implements ProgressListener {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private static final double THE_100_PERCENT = 100.0;

	private static final boolean DOWNLOAD_TO_TMP = true;
	private static final boolean SAVE_TO_TMP = false;
	private static final boolean PLAY_FROM_TMP = true;

	private final BaseLocalSource local;
	private final BaseSourceDownloader downloader;
	private final BaseSourceConverter converter;
	private final TrackFileFormat downloadFormat;
	private final TrackFileFormat saveFormat;
	private final TrackFileFormat playerFormat;
	private final Track track;

	public PreparerTask(Configuration config, BaseLocalSource local, BaseSourceDownloader downloader,
			BaseSourceConverter converter, BasePlayer player, Track track) {
		super();
		this.local = local;
		this.downloader = downloader;
		this.converter = converter;
		this.track = track;

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
			// TODO exception
			throw new JMOPSourceException(e);
		}
	}

	private boolean checkAndDownload() throws Exception {
		startSubTask("Downloading ...");
		boolean exists = local.exists(track, downloadFormat, DOWNLOAD_TO_TMP);
		if (exists) {
			return true;
		}

		boolean downloaded = downloader.download(track, DOWNLOAD_TO_TMP);
		return downloaded;
	}

	private boolean checkAndConvert() throws Exception {
		startSubTask("Converting ...");
		boolean exists = local.exists(track, saveFormat, SAVE_TO_TMP);
		if (exists) {
			return true;
		}

		boolean converted = converter.convert(track, downloadFormat, DOWNLOAD_TO_TMP, saveFormat, SAVE_TO_TMP);
		return converted;
	}

	private boolean checkAndPrepare() throws Exception {
		startSubTask("Preparing to play ...");
		boolean exists = local.exists(track, playerFormat, PLAY_FROM_TMP);
		if (exists) {
			return true;
		}

		boolean prepared = converter.convert(track, downloadFormat, DOWNLOAD_TO_TMP, playerFormat, PLAY_FROM_TMP);
		return prepared;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

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

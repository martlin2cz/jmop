package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.tracks.Track;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;

public class DownloaderTask extends Task<Boolean> {

	private static final double THE_100_PERCENT = 100.0;

	private final BaseSourceDownloader downloader;
	private final Track track;
	private final DoubleProperty progressPercentProperty;
	private InvalidationListener progressPercentPropertyListener;

	public DownloaderTask(BaseSourceDownloader downloader, Track track) {
		super();
		this.downloader = downloader;
		this.track = track;
		this.progressPercentProperty = downloader.getProgressPercentProperty();
		this.progressPercentPropertyListener = null;
	}

	@Override
	protected Boolean call() throws Exception {
		initialize();

		return runDownload();
	}

	private void initialize() {
		progressPercentPropertyListener = (e) -> {
			double progress = progressPercentProperty.doubleValue();
			updateProgress(progress, THE_100_PERCENT);
		};

		progressPercentProperty.addListener(progressPercentPropertyListener);
	}

	private void finish() {
		progressPercentProperty.removeListener(progressPercentPropertyListener);
	}

	private Boolean runDownload() throws JMOPSourceException {
		try {
			return downloader.download(track);
		} catch (Exception e) {
			// TODO exception
			throw new JMOPSourceException(e);
		} finally {
			finish();
		}
	}

}

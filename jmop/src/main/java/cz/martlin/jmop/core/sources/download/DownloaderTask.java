package cz.martlin.jmop.core.sources.download;

import java.io.File;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.tracks.Track;
import javafx.concurrent.Task;

public class DownloaderTask extends Task<Boolean> implements ProgressListener {

	private static final double THE_100_PERCENT = 100.0;

	private final BaseSourceDownloader downloader;
	private final Track track;

	public DownloaderTask(BaseSourceDownloader downloader, Track track) {
		super();
		this.downloader = downloader;
		this.track = track;
	}

	@Override
	protected Boolean call() throws Exception {
		return runDownload();
	}

	private Boolean runDownload() throws JMOPSourceException {
		try {
			File file = downloader.download(track);

			return file != null;
		} catch (Exception e) {
			// TODO exception
			throw new JMOPSourceException(e);
		}

	}

	@Override
	public void progressChanged(double percentage) {
		updateProgress(percentage, THE_100_PERCENT);
	}
}

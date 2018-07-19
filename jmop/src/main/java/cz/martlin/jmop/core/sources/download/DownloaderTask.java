package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.gui.DownloadGuiReporter;
import javafx.concurrent.Task;

public class DownloaderTask extends Task<Boolean> implements ProgressListener {

	private static final double THE_100_PERCENT = 100.0;

	private final BaseSourceDownloader downloader;
	private final BaseSourceConverter converter;
	private final Track track;

	public DownloaderTask(BaseSourceDownloader downloader, BaseSourceConverter converter, Track track) {
		super();
		this.downloader = downloader;
		this.converter = converter;
		this.track = track;
	}

	@Override
	protected Boolean call() throws Exception {
		try {
			updateMessage("Downloading ...");
			boolean downloaded = downloader.download(track);
			if (!downloaded) {
				return false;
			}

			updateMessage("Converting ...");
			boolean converted = converter.convert(track);
			if (!converted) {
				return false;
			}

			updateMessage("Done.");
			return true;
		} catch (Exception e) {
			// TODO exception
			throw new JMOPSourceException(e);
		}
	}

	public void bind(DownloadGuiReporter reporter) {
		reporter.runningProperty().bind(this.runningProperty());
		reporter.statusProperty().bind(this.messageProperty());
		reporter.progressProperty().bind(this.progressProperty());
	}
	
	@Override
	public void progressChanged(double percentage) {
		updateProgress(percentage, THE_100_PERCENT);
	}

	
}

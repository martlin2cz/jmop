package cz.martlin.jmop.core.sources.download;

import java.io.File;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;
import javafx.concurrent.Task;

public class DownloaderTask extends Task<Boolean> {

	private final BaseSourceDownloader downloader;
	private final TrackIdentifier identifier;

	public DownloaderTask(BaseSourceDownloader downloader, TrackIdentifier identifier) {
		super();
		this.downloader = downloader;
		this.identifier = identifier;
	}

	@Override
	protected Boolean call() throws Exception {
		try {
			return downloader.download(identifier);
		} catch (Exception e) {
			// TODO exception
			throw new JMOPSourceException(e);
		}
	}

}

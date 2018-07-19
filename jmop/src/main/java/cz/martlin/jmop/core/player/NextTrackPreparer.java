package cz.martlin.jmop.core.player;

import java.util.function.Consumer;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.gui.DownloadGuiReporter;

public class NextTrackPreparer {
	private final AbstractRemoteSource remote;
	private final BaseLocalSource local;
	private final BaseSourceConverter converter;
	private final BaseSourceDownloader downloader;
	private final GuiDescriptor gui;

	public NextTrackPreparer(AbstractRemoteSource remote, BaseLocalSource local, BaseSourceConverter converter,
			BaseSourceDownloader downloader, GuiDescriptor gui) {
		super();
		this.remote = remote;
		this.local = local;
		this.converter = converter;
		this.downloader = downloader;
		this.gui = gui;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public void prepreAndAppend(Track track, BetterPlaylistRuntime playlist) throws JMOPSourceException {
		prepareNextOf(track, (t) -> playlist.append(t));
	}
	
	public void prepareNextOf(Track current, Consumer<Track> onComplete) throws JMOPSourceException {
		try {
			Track next = remote.getNextTrackOf(current);

			boolean contains = local.exists(next);
			if (!contains) {
				checkAndDownload(next, onComplete);
			}
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot prepare next track", e);
		}
	}

	public void checkAndDownload(Track track, Consumer<Track> onComplete) {
		DownloaderTask task = new DownloaderTask(downloader, converter, track);
		DownloadGuiReporter reporter = gui.getDownloadGuiReporter();
		task.bind(reporter);

		if (onComplete != null) {
			task.setOnSucceeded((e) -> onComplete.accept(track));
			Thread thread = new Thread(task, "DownloaderTaskThread");
			thread.start();
		} else {
			task.run();
		}
	}

	
}

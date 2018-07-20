package cz.martlin.jmop.core.player;

import java.util.function.Consumer;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.gui.DownloadGuiReporter;

public class TrackPreparer {
	private final AbstractRemoteSource remote;
	private final BaseLocalSource local;
	private final BaseSourceConverter converter;
	private final BaseSourceDownloader downloader;
	private final GuiDescriptor gui;

	public TrackPreparer(AbstractRemoteSource remote, BaseLocalSource local, BaseSourceConverter converter,
			BaseSourceDownloader downloader, GuiDescriptor gui) {
		super();
		this.remote = remote;
		this.local = local;
		this.converter = converter;
		this.downloader = downloader;
		this.gui = gui;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void load(Track track) throws JMOPSourceException {
		checkAndDownload(track, null);
	}

	public void prepreNextAndAppend(Track track, BetterPlaylistRuntime playlist) throws JMOPSourceException {
		Track next = remote.getNextTrackOf(track);
		checkAndDownload(next, (t) -> playlist.append(t));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void checkAndDownload(Track track, Consumer<Track> onCompleteOrNull) throws JMOPSourceException {
		boolean contains = local.exists(track);

		if (!contains) {
			download(track, onCompleteOrNull);
		} else {
			trackReady(track, onCompleteOrNull);
		}
	}

	private void download(Track track, Consumer<Track> onCompleteOrNull) throws JMOPSourceException {
		DownloaderTask task = new DownloaderTask(downloader, converter, track);
		DownloadGuiReporter reporter = gui.getDownloadGuiReporter();
		task.bind(reporter);

		if (onCompleteOrNull != null) {
			task.setOnSucceeded((e) -> onTrackDownloadedHandler(track, onCompleteOrNull));
			Thread thread = new Thread(task, "DownloaderTaskThread");
			thread.start();
		} else {
			task.run();
			trackDownloaded(track, null);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	private void onTrackDownloadedHandler(Track track, Consumer<Track> onCompleteOrNull)  {
		try {
			trackDownloaded(track, onCompleteOrNull);
		} catch (JMOPSourceException e) {
			//TODO exception
			e.printStackTrace();
		}
	}

	private void trackDownloaded(Track track, Consumer<Track> onCompleteOrNull) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		local.saveBundle(bundle);

		trackReady(track, onCompleteOrNull);
	}

	private void trackReady(Track track, Consumer<Track> onCompleteOrNull) {
		if (onCompleteOrNull != null) {
			onCompleteOrNull.accept(track);
		}
	}

}

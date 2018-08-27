package cz.martlin.jmop.core.player;

import java.util.function.Consumer;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrackPreparer {
	private final AbstractRemoteSource remote;
	private final BaseLocalSource local;
	private final BaseSourceConverter converter;
	private final BaseSourceDownloader downloader;
	private final AutomaticSavesPerformer saver;
	private final GuiDescriptor gui;
	@Deprecated
	private final ObjectProperty<DownloaderTask> currentTaskProperty;
	private final ObservableList<DownloaderTask> currentTasks;


	public TrackPreparer(AbstractRemoteSource remote, BaseLocalSource local, BaseSourceConverter converter,
			BaseSourceDownloader downloader, AutomaticSavesPerformer saver, GuiDescriptor gui) {
		super();
		this.remote = remote;
		this.local = local;
		this.converter = converter;
		this.downloader = downloader;
		this.saver = saver;
		this.gui = gui;
		this.currentTaskProperty = new SimpleObjectProperty<>();
		this.currentTasks = FXCollections.observableArrayList();
	}

	@Deprecated
	public ObjectProperty<DownloaderTask> currentTaskProperty() {
		return currentTaskProperty;
	}
	
	public ObservableList<DownloaderTask> currentTasks() {
		return currentTasks;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public void load(Track track) throws JMOPSourceException {
		checkAndDownload(track, null);
	}

	public void prepreNextAndAppend(Track track, JMOPPlaylister playlister) throws JMOPSourceException {
		Track next = remote.getNextTrackOf(track);
		checkAndDownload(next, (t) -> playlister.appendTrack(t));
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
		
		currentTaskProperty.set(task);
		currentTasks.add(task);

		if (onCompleteOrNull != null) {
			task.setOnSucceeded((e) -> onTrackDownloadedHandler(task, track, onCompleteOrNull));
			Thread thread = new Thread(task, "DownloaderTaskThread");
			thread.start();
		} else {
			task.run();
			trackDownloaded(task, track, null);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	private void onTrackDownloadedHandler(DownloaderTask task , Track track, Consumer<Track> onCompleteOrNull)  {
		try {
			trackDownloaded(task, track, onCompleteOrNull);
		} catch (JMOPSourceException e) {
			//TODO exception
			e.printStackTrace();
		}
	}

	private void trackDownloaded(DownloaderTask task, Track track, Consumer<Track> onCompleteOrNull) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		saver.saveBundle(bundle); //TODO FIXME quite hack

		trackReady(track, onCompleteOrNull);
		
		currentTasks.remove(task);
	}

	private void trackReady(Track track, Consumer<Track> onCompleteOrNull) {
		if (onCompleteOrNull != null) {
			onCompleteOrNull.accept(track);
		}
	}

}

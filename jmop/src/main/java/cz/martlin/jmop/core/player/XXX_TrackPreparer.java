package cz.martlin.jmop.core.player;

import java.util.function.Consumer;

import cz.martlin.jmop.core.config.DefaultConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.PreparerTask;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Deprecated
public class XXX_TrackPreparer {
	private final DefaultConfiguration config;
	private final AbstractRemoteSource remote;
	private final BaseLocalSource local;
	private final AbstractTrackFileLocator locator;
	private final BaseSourceConverter converter;
	private final BaseSourceDownloader downloader;
	private final BasePlayer player;
	private final AutomaticSavesPerformer saver;
	private final GuiDescriptor gui;
	@Deprecated
	private final ObjectProperty<PreparerTask> currentTaskProperty;
	private final ObservableList<PreparerTask> currentTasks;
	
	
	


	public XXX_TrackPreparer(DefaultConfiguration config, AbstractRemoteSource remote, BaseLocalSource local, AbstractTrackFileLocator locator, BaseSourceConverter converter,
			BaseSourceDownloader downloader, BasePlayer player, AutomaticSavesPerformer saver, GuiDescriptor gui) {
		super();
		this.config = config;
		this.remote = remote;
		this.local = local;
		this.locator = locator;
		this.converter = converter;
		this.downloader = downloader;
		this.player = player;
		this.saver = saver;
		this.gui = gui;
		this.currentTaskProperty = new SimpleObjectProperty<>();
		this.currentTasks = FXCollections.observableArrayList();
	}

	@Deprecated
	public ObjectProperty<PreparerTask> currentTaskProperty() {
		return currentTaskProperty;
	}
	
	public ObservableList<PreparerTask> currentTasks() {
		return currentTasks;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public void load(Track track) throws JMOPSourceException {
		prepare(track, null);
	}

	public void prepreNextAndAppend(Track track, JMOPPlaylister playlister) throws JMOPSourceException {
		Track next = remote.getNextTrackOf(track);
		prepare(next, (t) -> playlister.appendTrack(t));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void prepare(Track track, Consumer<Track> onCompleteOrNull) throws JMOPSourceException {
		PreparerTask task = new PreparerTask(config, locator, local, downloader, converter, player, track);
		
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

	private void onTrackDownloadedHandler(PreparerTask task , Track track, Consumer<Track> onCompleteOrNull)  {
		try {
			trackDownloaded(task, track, onCompleteOrNull);
		} catch (JMOPSourceException e) {
			//TODO exception
			e.printStackTrace();
		}
	}

	private void trackDownloaded(PreparerTask task, Track track, Consumer<Track> onCompleteOrNull) throws JMOPSourceException {
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

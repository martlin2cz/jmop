package cz.martlin.jmop.core.preparer;

import java.util.LinkedList;
import java.util.function.Consumer;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.playlister.BasePlaylister;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;
import cz.martlin.jmop.core.preparer.operations.Operations;
import cz.martlin.jmop.core.preparer.operations.TrackSearchOperation.SearchData;
import cz.martlin.jmop.core.preparer.operations.base.BaseOperation;
import cz.martlin.jmop.core.preparer.operations.base.OperationWrapper;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrackPreparer {

	private final Operations operations;

	private final ObservableList<OperationWrapper<?, ?>> currentTasks;

	public TrackPreparer(BaseConfiguration config, AbstractRemoteSource remote, BaseLocalSource local,
			AbstractTrackFileLocator locator, BaseSourceDownloader downloader, BaseSourceConverter converter,
			BasePlayer player) {

		this.operations = new Operations(config, locator, remote, local, downloader, converter, player);

		this.currentTasks = FXCollections.observableList(new LinkedList<>());
	}

	public ObservableList<OperationWrapper<?, ?>> currentOperations() {
		return currentTasks;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void startSearchAndLoadInBg(Bundle bundle, String query, PlayerEngine engine) {
		SearchData data = new SearchData(bundle, query);
		BaseOperation<SearchData, Track> search = operations.searchAndLoadOperation();

		runInBackground(search, data, (t) -> appendAndStartPlaying(t, engine));
	}

	public void startSearchAndLoadInBg(Bundle bundle, String query, Consumer<Track> onLoaded) {
		SearchData data = new SearchData(bundle, query);
		BaseOperation<SearchData, Track> search = operations.searchAndLoadOperation();

		runInBackground(search, data, (t) -> onLoaded.accept(t));
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void startLoadingNextOf(Track track, PlayerEngine engine) {
		BaseOperation<Track, Track> nexts = operations.nextAndLoadOperation();

		runInBackground(nexts, track, (t) -> append(t, engine));
	}

	/**
	 * Quite hack, equal to
	 * {@link #startLoadingNextOf(Track, PlaylisterWrapper)}.
	 * 
	 * @param track
	 * @param addTo
	 */
	public void startLoadingNextOfInBg(Track track, BasePlaylister addTo) {
		BaseOperation<Track, Track> nexts = operations.nextAndLoadOperation();

		runInBackground(nexts, track, (t) -> append(t, addTo));
	}

	public void startLoadingNextOfInBg(Track track, Consumer<Track> onLoaded) {
		BaseOperation<Track, Track> nexts = operations.nextAndLoadOperation();

		runInBackground(nexts, track, (t) -> onLoaded.accept(t));
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void checkAndLoadTrack(Track track) {
		BaseOperation<Track, Track> load = operations.loadOperation();

		runInForeground(load, track);
	}

//	@Deprecated
//	public void checkAndStartLoadingTrack(Track track, PlayerEngine engine) {
//		BaseOperation<Track, Track> load = operations.loadOperation();
//
//		runInForeground(load, track, (t) -> append(t, engine));
//	}
//
//	@Deprecated
//	public void checkAndStartLoadingTrack(Track track, Consumer<Track> onLoaded) {
//		BaseOperation<Track, Track> load = operations.loadOperation();
//
//		runInForeground(load, track, (t) -> onLoaded.accept(t));
//	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void appendAndStartPlaying(Track track, PlayerEngine engine) {
		try {
			engine.add(track);
			engine.playNext();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO error handler
		}
	}

	private void append(Track track, PlayerEngine engine) {
		try {
			engine.add(track);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO error handler
		}
	}

	private void append(Track track, BasePlaylister addTo) {
		try {
			addTo.addTrack(track);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO error handler
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	private <IT, OT> void runInBackground(BaseOperation<IT, OT> operation, IT data, Consumer<OT> onCompleted) {
		OperationWrapper<IT, OT> wrapper = new OperationWrapper<>(operation);
		
		TrackOperationTask<IT, OT> task = new TrackOperationTask<>(wrapper, data);
		
		currentTasks.add(wrapper);

		task.setOnSucceeded((e) -> taskInBgCompleted(task, onCompleted));
		// TODO task.setOnError ....

		Thread thr = new Thread(task, "TrackOperationTaskThread");
		thr.start();
	}

	private <IT, OT> void runInForeground(BaseOperation<IT, OT> operation, IT data) {
OperationWrapper<IT, OT> wrapper = new OperationWrapper<>(operation);
		
		TrackOperationTask<IT, OT> task = new TrackOperationTask<>(wrapper, data);
		
		currentTasks.add(wrapper);

		// TODO task.setOnError ....

		task.run();

		taskInFgCompleted(task);

	}

	/////////////////////////////////////////////////////////////////////////////////////
	private <IT, OT> void taskInBgCompleted(TrackOperationTask<IT, OT> task, Consumer<OT> onCompleted) {
		OT result = task.getValue();
		onCompleted.accept(result);

		currentTasks.remove(task);
	}

	private <IT, OT> void taskInFgCompleted(TrackOperationTask<IT, OT> task) {
		currentTasks.remove(task);
	}
	/////////////////////////////////////////////////////////////////////////////////////

}

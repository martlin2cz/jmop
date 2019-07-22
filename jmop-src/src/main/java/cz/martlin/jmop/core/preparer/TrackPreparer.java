package cz.martlin.jmop.core.preparer;

import java.util.LinkedList;
import java.util.function.Consumer;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.operation.base.BaseOperation;
import cz.martlin.jmop.core.operation.base.OperationWrapper;
import cz.martlin.jmop.core.operation.base.TrackOperationTask;
import cz.martlin.jmop.core.operation.operations.Operations;
import cz.martlin.jmop.core.operation.operations.TrackFilesLoadOperation;
import cz.martlin.jmop.core.operation.operations.TrackSearchOperation.SearchData;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.player.PlayerWrapper;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.remote.XXX_AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceConverter;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceDownloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Main entry point to operations with the tracks. Encapsulates track
 * operations, to be running both in foreground and background, so with
 * reporting the status and progress of all of them.
 * 
 * @author martin
 *
 */
public class TrackPreparer {

	private final ErrorReporter reporter;
	private final Operations operations;
	private final ObservableList<OperationWrapper<?, ?>> currentTasks;

	public TrackPreparer(ErrorReporter reporter, BaseConfiguration config, XXX_AbstractRemoteSource remote,
			BaseLocalSource local, AbstractTrackFileLocator locator, XXX_BaseSourceDownloader downloader,
			XXX_BaseSourceConverter converter, BasePlayer player) {

		this.reporter = reporter;

		this.operations = new Operations(reporter, config, locator, remote, local, downloader, converter, player);

		this.currentTasks = FXCollections.observableList(new LinkedList<>());
	}

	public ObservableList<OperationWrapper<?, ?>> currentOperations() {
		return currentTasks;
	}

	public boolean isCurrentlyRunningSome() {
		return !currentTasks.isEmpty();
	}

	public int countOfCurrentlyRunning() {
		return currentTasks.size();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Start loading of track (in given bundle) by given query, and to
	 * {@link PlayerEngine#add(Track)} to given engine, when completed; in
	 * background.
	 * 
	 * @param bundle
	 * @param query
	 * @param engine
	 */
	public void startSearchAndLoadInBg(Bundle bundle, String query, PlayerEngine engine) {
		SearchData data = new SearchData(bundle, query);
		BaseOperation<SearchData, Track> search = operations.searchAndLoadOperation();

		runInBackground(search, data, (t) -> appendAndStartPlaying(t, engine));
	}

	/**
	 * Start loading of track (in given bundle) with given query, processing by
	 * given consumer, when completed; in background.
	 * 
	 * @param bundle
	 * @param query
	 * @param onLoaded
	 */
	public void startSearchAndLoadInBg(Bundle bundle, String query, Consumer<Track> onLoaded) {
		SearchData data = new SearchData(bundle, query);
		BaseOperation<SearchData, Track> search = operations.searchAndLoadOperation();

		runInBackground(search, data, (t) -> onLoaded.accept(t));
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Start loading next track of given bundle, and to
	 * {@link PlayerEngine#add(Track)} to given engine, when completed; in
	 * background.
	 * 
	 * @param track
	 * @param engine
	 */
	public void startLoadingNextOf(Track track, PlayerEngine engine) {
		BaseOperation<Track, Track> nexts = operations.nextAndLoadOperation();

		runInBackground(nexts, track, (t) -> append(t, engine));
	}

	/**
	 * Start loading next track of given bundle, and to handle with given consumer,
	 * when completed; in background.
	 * 
	 * @param track
	 * @param onLoaded
	 */
	public void startLoadingNextOfInBg(Track track, Consumer<Track> onLoaded) {
		BaseOperation<Track, Track> nexts = operations.nextAndLoadOperation();

		runInBackground(nexts, track, (t) -> onLoaded.accept(t));
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Load in background files of given track. After that start to play them.
	 * 
	 * @param track
	 * @param player
	 */
	public void checkAndLoadTrack(Track track, PlayerWrapper player) {
		final TrackFilesLoadOperation load = operations.loadOperation();
		runInBackground(load, track, (t) -> {
			try {
				if (load.existsToPlay(track)) {
					player.startPlaying(t);
				}
			} catch (JMOPSourceException e) {
				reporter.report(e);
			}
		});
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Append to given engine (by {@link PlayerEngine#add(Track)}) and then start
	 * playing (next track to play, not nescesairly the newly appended).
	 * 
	 * @param track
	 * @param engine
	 */
	private void appendAndStartPlaying(Track track, PlayerEngine engine) {
		try {
			engine.add(track);
			engine.playNext();
		} catch (JMOPSourceException e) {
			reporter.report(e);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}

	/**
	 * Only appends the given track.
	 * 
	 * @param track
	 * @param engine
	 */
	private void append(Track track, PlayerEngine engine) {
		try {
			engine.add(track);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Runs given operation in backgroud, with given input data and when completed,
	 * invokes given consumer. In fact, creates task, adds to current tasks and runs
	 * the task in separate thread.
	 * 
	 * @param operation
	 * @param data
	 * @param onCompleted
	 */
	private <IT, OT> void runInBackground(BaseOperation<IT, OT> operation, IT data, Consumer<OT> onCompleted) {
		OperationWrapper<IT, OT> wrapper = new OperationWrapper<>(operation);
		//TODO if not yet already running ...
		TrackOperationTask<IT, OT> task = new TrackOperationTask<>(wrapper, data);

		currentTasks.add(wrapper);

		task.setOnSucceeded((e) -> taskInBgCompleted(task, wrapper, onCompleted));
		task.setOnFailed((e) -> taskFailed(task, wrapper));

		Thread thr = new Thread(task, "TrackOperationTaskThread"); //$NON-NLS-1$
		thr.start();
	}

	/**
	 * Runs given operation in backgroud, with given input data. In fact, creates
	 * task, adds to current tasks and runs the task.
	 * 
	 * @param operation
	 * @param data
	 * @deprecated do not run anything in foregound!
	 */
	@Deprecated
	private <IT, OT> void runInForeground(BaseOperation<IT, OT> operation, IT data) {
		OperationWrapper<IT, OT> wrapper = new OperationWrapper<>(operation);

		TrackOperationTask<IT, OT> task = new TrackOperationTask<>(wrapper, data);

		currentTasks.add(wrapper);

		task.setOnSucceeded((e) -> taskInFgCompleted(task, wrapper));
		task.setOnFailed((e) -> taskFailed(task, wrapper));

		task.run();

		taskInFgCompleted(task, wrapper);

	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Handler of taks in background completed. In fact, removes the task from
	 * running tasks and invokes given consumer.
	 * 
	 * @param task
	 * @param wrapper
	 * @param onCompleted
	 */
	private <IT, OT> void taskInBgCompleted(TrackOperationTask<IT, OT> task, OperationWrapper<IT, OT> wrapper,
			Consumer<OT> onCompleted) {
		OT result = task.getValue();
		onCompleted.accept(result);

		currentTasks.remove(wrapper);
	}

	/**
	 * Handler of task in foreground completed. In fact, only removes the task from
	 * running tasks.
	 * 
	 * @param task
	 * @param wrapper
	 */
	private <IT, OT> void taskInFgCompleted(TrackOperationTask<IT, OT> task, OperationWrapper<IT, OT> wrapper) {
		currentTasks.remove(wrapper);
	}

	/**
	 * Handler of task in foreground failed. In fact, only removes the task from
	 * running tasks.
	 * 
	 * @param task
	 * @param wrapper
	 */
	private <IT, OT> void taskFailed(TrackOperationTask<IT, OT> task, OperationWrapper<IT, OT> wrapper) {
		currentTasks.remove(wrapper);
	}

	/////////////////////////////////////////////////////////////////////////////////////

}

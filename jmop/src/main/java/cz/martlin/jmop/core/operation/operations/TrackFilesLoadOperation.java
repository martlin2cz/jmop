package cz.martlin.jmop.core.operation.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.operation.base.AbstractAtomicOperation;
import cz.martlin.jmop.core.operation.base.OperationChangeListener;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormat;
import cz.martlin.jmop.core.sources.remote.BaseSourceConverter;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.remote.TrackFileFormatLocationPreparer;
import javafx.util.Duration;

public class TrackFilesLoadOperation extends AbstractAtomicOperation<Track, Track> {

	private final BaseLocalSource local;
	private final BaseSourceDownloader downloader;
	private final TrackFileFormatLocationPreparer preparer;

	private final TrackFileLocation downloadLocation;
	private final TrackFileLocation saveLocation;
	private final TrackFileLocation playerLocation;

	private final TrackFileFormat downloadFormat;
	private final TrackFileFormat saveFormat;
	private final TrackFileFormat playerFormat;

	public TrackFilesLoadOperation(ErrorReporter reporter, BaseConfiguration config, AbstractTrackFileLocator locator,
			BaseLocalSource local, BaseSourceDownloader downloader, BaseSourceConverter converter, BasePlayer player) {
		super(reporter, "Download and convert");

		this.local = local;
		this.downloader = downloader;

		this.preparer = new TrackFileFormatLocationPreparer(local, converter);
		this.downloadLocation = locator.locationOfDownload(downloader);
		this.saveLocation = locator.locationOfSave();
		this.playerLocation = locator.locationOfPlay(player);

		this.downloadFormat = downloader.formatOfDownload();
		this.saveFormat = config.getSaveFormat();
		this.playerFormat = player.getPlayableFormat();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected Track runInternal(Track input, OperationChangeListener handler) throws Exception {

		downloader.specifyListener(handler);
		preparer.specifyListener(handler);

		boolean downloaded = checkAndDownload(input, handler);
		if (!downloaded) {
			return input;
		}

		boolean converted = checkAndConvert(input, handler);
		if (!converted) {
			return input;
		}

		boolean prepared = checkAndPrepare(input, handler);
		if (!prepared) {
			return input;
		}

		// FIXME wont be invoked if return belowe is
		downloader.specifyListener(null);
		preparer.specifyListener(null);

		return input;
	}

	private boolean checkAndDownload(Track track, OperationChangeListener handler) throws Exception {

		startSubOperation("Downloading ...", handler);

		boolean ready = existsSaved(track) && existsToPlay(track);
		if (ready) {
			return true;
		}

		boolean existsDownloaded = existsDownloaded(track);
		if (existsDownloaded) {
			return true;
		}

		boolean downloaded = downloader.download(track, downloadLocation);
		return downloaded;
	}

	private boolean checkAndConvert(Track track, OperationChangeListener handler) throws Exception {

		startSubOperation("Converting ...", handler);

		boolean exists = existsSaved(track);
		if (exists) {
			return true;
		}

		boolean converted = preparer.prepare(track, downloadFormat, downloadLocation, saveFormat, saveLocation);
		return converted;
	}

	private boolean checkAndPrepare(Track track, OperationChangeListener handler) throws Exception {

		startSubOperation("Preparing to play ...", handler);

		boolean exists = existsToPlay(track);
		if (exists) {
			return true;
		}

		boolean prepared = preparer.prepare(track, downloadFormat, downloadLocation, playerFormat, playerLocation);
		return prepared;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private boolean existsDownloaded(Track track) throws JMOPSourceException {
		return local.exists(track, downloadLocation, downloadFormat);
	}

	private boolean existsSaved(Track track) throws JMOPSourceException {
		return local.exists(track, saveLocation, saveFormat);
	}

	private boolean existsToPlay(Track track) throws JMOPSourceException {
		return local.exists(track, playerLocation, playerFormat);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String inputDataAsString(Track input) {
		return trackToString(input);
	}

	public static String trackToString(Track track) {
		String title = track.getTitle();
		Duration duration = track.getDuration();
		String durationStr = DurationUtilities.toHumanString(duration);

		return title + " (" + durationStr + ")";
	}

}

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
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormatLocationPreparer;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceConverter;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceDownloader;
import cz.martlin.jmop.gui.local.Msg;
import javafx.util.Duration;

/**
 * Operation performing "loading" of track files. That means, download, convert
 * to save and prepare to play.
 * 
 * @author martin
 *
 */
public class TrackFilesLoadOperation extends AbstractAtomicOperation<Track, Track> {

	private final BaseLocalSource local;
	private final XXX_BaseSourceDownloader downloader;
	private final TrackFileFormatLocationPreparer preparer;

	private final TrackFileLocation downloadLocation;
	private final TrackFileLocation saveLocation;
	private final TrackFileLocation playerLocation;

	private final TrackFileFormat downloadFormat;
	private final TrackFileFormat saveFormat;
	private final TrackFileFormat playerFormat;

	public TrackFilesLoadOperation(ErrorReporter reporter, BaseConfiguration config, AbstractTrackFileLocator locator,
			BaseLocalSource local, XXX_BaseSourceDownloader downloader, XXX_BaseSourceConverter converter, BasePlayer player) {
		super(reporter, Msg.get("Download_and_convert")); //$NON-NLS-1$

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

		return input;
	}

	/**
	 * Checks if needs to download (saved or prepared to play track file(s) does
	 * not exist), and if is not yet downloaded, and if so, runs download phase.
	 * 
	 * @param track
	 * @param handler
	 * @return false if failed, true if succeed or skipped
	 * @throws Exception
	 */
	private boolean checkAndDownload(Track track, OperationChangeListener handler) throws Exception {

		startSubOperation(Msg.get("Downloading_"), handler); //$NON-NLS-1$

		boolean ready = existsSaved(track) && existsToPlay(track);
		if (ready) {
			return true;
		}

		boolean existsDownloaded = existsDownloaded(track);
		if (existsDownloaded) {
			return true;
		}

		boolean downloaded = downloader.download(track, downloadLocation, handler);
		return downloaded;
	}

	/**
	 * Checks if is not yet converted to save, and if so, converts the
	 * downloaded file to save.
	 * 
	 * @param track
	 * @param handler
	 * @return false if failed, true if succeed or skipped
	 * @throws Exception
	 */
	private boolean checkAndConvert(Track track, OperationChangeListener handler) throws Exception {

		startSubOperation(Msg.get("Converting_"), handler); //$NON-NLS-1$

		boolean exists = existsSaved(track);
		if (exists) {
			return true;
		}

		boolean converted = preparer.prepare(track, downloadFormat, downloadLocation, saveFormat, saveLocation, handler);
		return converted;
	}

	/**
	 * Checks if is not yet prepared to play, and if so, converts the downloaded
	 * file to prepared.
	 * 
	 * @param track
	 * @param handler
	 * @return false if failed, true if succeed or skipped
	 * @throws Exception
	 */
	private boolean checkAndPrepare(Track track, OperationChangeListener handler) throws Exception {

		startSubOperation(Msg.get("Preparing_to_play_"), handler); //$NON-NLS-1$

		boolean exists = existsToPlay(track);
		if (exists) {
			return true;
		}

		boolean prepared = preparer.prepare(track, downloadFormat, downloadLocation, playerFormat, playerLocation, handler);
		return prepared;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Exists downloaded file (file in download format in download location)?
	 * 
	 * @param track
	 * @return
	 * @throws JMOPSourceException
	 */
	public boolean existsDownloaded(Track track) throws JMOPSourceException {
		return local.exists(track, downloadLocation, downloadFormat);
	}

	/**
	 * Exists saved file (file in save format in save location)?
	 * 
	 * @param track
	 * @return
	 * @throws JMOPSourceException
	 */
	public boolean existsSaved(Track track) throws JMOPSourceException {
		return local.exists(track, saveLocation, saveFormat);
	}

	/**
	 * Exists prepared file (file in prepared-to-play format in prepared-to-play
	 * location?
	 * 
	 * @param track
	 * @return
	 * @throws JMOPSourceException
	 */
	public boolean existsToPlay(Track track) throws JMOPSourceException {
		return local.exists(track, playerLocation, playerFormat);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String inputDataAsString(Track input) {
		return trackToString(input);
	}

	/**
	 * Converts given track to some human string in format "TITLE (DURATION)".
	 * 
	 * @param track
	 * @return
	 */
	public static String trackToString(Track track) {
		String title = track.getTitle();
		Duration duration = track.getDuration();
		String durationStr = DurationUtilities.toHumanString(duration);

		return title + " (" + durationStr + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}

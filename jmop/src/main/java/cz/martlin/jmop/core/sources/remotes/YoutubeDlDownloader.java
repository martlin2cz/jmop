package cz.martlin.jmop.core.sources.remotes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.AbstractProcessEncapusulation;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.remotes.YoutubeDlDownloader.DownloadData;

/**
 * The downloader working by youtube-dl.
 * 
 * @author martin
 *
 */
public class YoutubeDlDownloader extends AbstractProcessEncapusulation<DownloadData, Boolean>
		implements BaseSourceDownloader {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public static final TrackFileFormat DOWNLOAD_FILE_FORMAT = TrackFileFormat.OPUS;
	private static final String PROGRESS_LINE_START = "[download]"; //$NON-NLS-1$
	private static final String COLUMNS_SEPARATOR_REGEX = " +"; //$NON-NLS-1$
	private static final String PERCENT_REGEX = "\\d{1,3}\\.\\d{1}\\%"; //$NON-NLS-1$
	private static final int RESULT_CODE_OK = 0;

	private final InternetConnectionStatus connection;
	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;

	public YoutubeDlDownloader(InternetConnectionStatus connection, BaseLocalSource local,
			AbstractRemoteSource remote) {
		super();
		this.connection = connection;
		this.local = local;
		this.remote = remote;
	}

	@Override
	public TrackFileFormat formatOfDownload() {
		return DOWNLOAD_FILE_FORMAT;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean download(Track track, TrackFileLocation location) throws ExternalProgramException {
		LOG.info("Downloading track " + track); //$NON-NLS-1$
		DownloadData data = new DownloadData(track, location);
		return run(data);
	}

	@Override
	protected List<String> createCommandLine(DownloadData data) throws Exception {
		String url = createUrlOfTrack(data);
		String path = createTargetFilePath(data);

		return createCommandLine(url, path);
	}

	@Override
	protected File getWorkingDirectory(DownloadData data) throws Exception {
		return getTemporaryDirectory();
	}

	@Override
	protected InputStream getOutputStream(Process process) throws Exception {
		return process.getInputStream();
	}

	@Override
	protected Double processLineOfOutput(String line) throws Exception {
		return tryToParsePercentFromLine(line);
	}

	@Override
	protected Boolean handleResult(int result, DownloadData data) throws Exception {
		boolean isOk = (result == RESULT_CODE_OK);
		if (!isOk) {
			connection.markOffline();
		}
		return isOk;
	}

	@Override
	public boolean check() {
		return runAndCheckForResult("youtube-dl --version") == RESULT_CODE_OK; //$NON-NLS-1$
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Generates url parameter. In fact calls
	 * {@link AbstractRemoteSource#urlOf(Track)}.
	 * 
	 * @param data
	 * @return
	 * @throws JMOPSourceException
	 */
	private String createUrlOfTrack(DownloadData data) throws JMOPSourceException {
		Track track = data.getTrack();
		URL url = remote.urlOf(track);
		return url.toExternalForm();
	}

	/**
	 * Creates the output file parameter. In fact calls
	 * {@link #createTargetFileFile(Track, TrackFileLocation)}.
	 * 
	 * @param data
	 * @return
	 * @throws JMOPSourceException
	 * @throws IOException
	 */
	private String createTargetFilePath(DownloadData data) throws JMOPSourceException, IOException {
		Track track = data.getTrack();
		TrackFileLocation location = data.getLocation();
		File tmpFile = createTargetFileFile(track, location);

		return tmpFile.getAbsolutePath();
	}

	/**
	 * Creates the output file (as a file).
	 * 
	 * @param track
	 * @param location
	 * @return
	 * @throws JMOPSourceException
	 * @throws IOException
	 */
	private File createTargetFileFile(Track track, TrackFileLocation location) throws JMOPSourceException, IOException {
		return local.fileOfTrack(track, location, DOWNLOAD_FILE_FORMAT);
	}

	/**
	 * Creates the command.
	 * 
	 * @param url
	 * @param path
	 * @return
	 */
	private List<String> createCommandLine(String url, String path) {
		return Arrays.asList( //
				"youtube-dl", "--newline", "--extract-audio", "--audio-format", DOWNLOAD_FILE_FORMAT.getExtension(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"--output", path, url); //$NON-NLS-1$
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Infers the progress from the command. In fact checks the line start and
	 * if so, extracts the progress value.
	 * 
	 * @param line
	 * @return
	 */
	protected static Double tryToParsePercentFromLine(String line) {
		if (!line.startsWith(PROGRESS_LINE_START)) {
			return null;
		}

		String[] parts = line.split(COLUMNS_SEPARATOR_REGEX);
		if (parts.length < 1) {
			return null;
		}

		String progressPart = parts[1];
		if (!progressPart.matches(PERCENT_REGEX)) {
			return null;
		}

		String percentStr = progressPart.substring(0, progressPart.length() - 1);
		double percent = Double.parseDouble(percentStr);
		return percent;
	}

	/**
	 * Removes everything after the dot character.
	 * 
	 * @param path
	 * @return
	 */
	protected static String removeSuffix(String path) {
		int indexOfDot = path.lastIndexOf((int) '.');

		return path.substring(0, indexOfDot);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The data for download process (track and location).
	 * 
	 * @author martin
	 *
	 */
	protected static class DownloadData {
		private final Track track;
		private final TrackFileLocation location;

		public DownloadData(Track track, TrackFileLocation location) {
			super();
			this.track = track;
			this.location = location;
		}

		public Track getTrack() {
			return track;
		}

		public TrackFileLocation getLocation() {
			return location;
		}

	}

}

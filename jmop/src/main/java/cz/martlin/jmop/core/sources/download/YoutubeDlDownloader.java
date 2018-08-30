package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.YoutubeDlDownloader.DownloadData;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public class YoutubeDlDownloader extends AbstractProcessEncapusulation<DownloadData, Boolean>
		implements BaseSourceDownloader {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public static final TrackFileFormat DOWNLOAD_FILE_FORMAT = TrackFileFormat.OPUS;
	private static final String PROGRESS_LINE_START = "[download]";
	private static final String COLUMNS_SEPARATOR_REGEX = " +";
	private static final String PERCENT_REGEX = "\\d{1,3}\\.\\d{1}\\%";
	private static final int RESULT_CODE_OK = 0;

	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;

	public YoutubeDlDownloader(BaseLocalSource local, AbstractRemoteSource remote) {
		super();
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
		LOG.info("Downloading track " + track);
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
		return (result == RESULT_CODE_OK);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private String createUrlOfTrack(DownloadData data) throws JMOPSourceException {
		Track track = data.getTrack();
		URL url = remote.urlOf(track);
		return url.toExternalForm();
	}

	private String createTargetFilePath(DownloadData data) throws JMOPSourceException, IOException {
		Track track = data.getTrack();
		TrackFileLocation location = data.getLocation();
		File tmpFile = createTargetFileFile(track, location);

		return tmpFile.getAbsolutePath();
	}

	private File createTargetFileFile(Track track, TrackFileLocation location) throws JMOPSourceException, IOException {
		return local.fileOfTrack(track, location, DOWNLOAD_FILE_FORMAT);
	}

	private List<String> createCommandLine(String url, String path) {
		return Arrays.asList( //
				"youtube-dl", "--newline", "--extract-audio", "--audio-format", DOWNLOAD_FILE_FORMAT.getExtension(),
				"--output", path, url);
	}

	/////////////////////////////////////////////////////////////////////////////////////

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

	protected static String removeSuffix(String path) {
		int indexOfDot = path.lastIndexOf((int) '.');

		return path.substring(0, indexOfDot);
	}
	/////////////////////////////////////////////////////////////////////////////////////

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

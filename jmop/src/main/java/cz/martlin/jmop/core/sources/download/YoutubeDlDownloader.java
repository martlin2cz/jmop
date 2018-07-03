package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.Sources;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.tracks.Track;

public class YoutubeDlDownloader extends AbstractProcessEncapusulation<Track, File> implements BaseSourceDownloader {

	private static final String TMP_SUFFIX = ".tmp";
	private static final String PROGRESS_LINE_START = "[download]";
	private static final String COLUMNS_SEPARATOR_REGEX = " +";
	private static final String PERCENT_REGEX = "\\d{1,2}\\.\\d{1}\\%";
	private static final int RESULT_CODE_OK = 0;

	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;
	private File outputFile;

	public YoutubeDlDownloader(Sources sources, ProgressListener listener) {
		super(listener);
		this.local = sources.getLocal();
		this.remote = sources.getRemote();
	}

	@Override
	public File download(Track track) throws ExternalProgramException {
		return run(track);
	}

	@Override
	protected List<String> createCommandLine(Track track) throws Exception {
		String url = createUrlOfTrack(track);
		String path = createTargetFilePath(track);

		return createCommandLine(url, path);
	}

	@Override
	protected File getWorkingDirectory(Track track) throws Exception {
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
	protected File handleResult(int result) throws Exception {
		if (result == RESULT_CODE_OK) {
			return outputFile;
		} else {
			return null;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private String createUrlOfTrack(Track track) throws JMOPSourceException {
		URL url = remote.urlOf(track);
		return url.toExternalForm();
	}

	private String createTargetFilePath(Track track) throws JMOPSourceException, IOException {
		File tmpFile = privateCreateTargetTempFile(track);

		outputFile = tmpFile;

		return tmpFile.getAbsolutePath();
	}

	private File privateCreateTargetTempFile(Track track) throws JMOPSourceException, IOException {
		File file = local.fileOfTrack(track);
		String name = file.getName();

		File tmpFile = File.createTempFile(name, TMP_SUFFIX);
		return tmpFile;
	}

	private List<String> createCommandLine(String url, String path) {
		return Arrays.asList( //
				"youtube-dl", "--newline", "--extract-audio", "--output", path, url);
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

}

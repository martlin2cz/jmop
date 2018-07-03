package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.io.Files;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.Sources;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.tracks.Track;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class YoutubeDlDownloader implements BaseSourceDownloader {

	private static final String TMP_SUFFIX = ".%(ext)s";
	private static final String PROGRESS_LINE_START = "[download]";
	private static final String COLUMNS_SEPARATOR_REGEX = " +";
	private static final String PERCENT_REGEX = "\\d{1,2}\\.\\d{1}\\%";
	private static final int RESULT_CODE_OK = 0;

	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;
	private final DoubleProperty progress;

	private Process process;

	public YoutubeDlDownloader(Sources sources) {
		this.local = sources.getLocal();
		this.remote = sources.getRemote();

		this.process = null;
		this.progress = new SimpleDoubleProperty();
	}

	@Override
	public DoubleProperty getProgressPercentProperty() {
		return progress;
	}

	@Override
	public boolean download(Track track) throws IOException, InterruptedException, JMOPSourceException {
		startProcess(track);

		handleProcessOutput();

		return finishProcess();
	}

	@Override
	public void stop() {
		killTheProcess();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void startProcess(Track track) throws IOException, JMOPSourceException {
		List<String> commandline = createCommandLine(track);
		System.err.println("Running: " + commandline);

		ProcessBuilder builder = new ProcessBuilder(commandline);

		File directory = getTemporaryDirectory();
		builder.directory(directory);

		process = builder.start();
	}

	private File getTemporaryDirectory() {
		File directory = Files.createTempDir();
		return directory;
	}

	private boolean finishProcess() throws InterruptedException {
		int result = process.waitFor();

		process = null;

		return result == RESULT_CODE_OK;
	}

	private void killTheProcess() {
		process.destroy();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private List<String> createCommandLine(Track track) throws JMOPSourceException {
		String url = createUrlOfTrack(track);
		String path = createTargetFilePath(track);

		return createCommandLine(url, path);
	}

	private String createUrlOfTrack(Track track) throws JMOPSourceException {
		URL url = remote.urlOf(track);
		return url.toExternalForm();
	}

	private String createTargetFilePath(Track track) throws JMOPSourceException {
		File file = local.fileOfTrack(track);
		String path = file.getAbsolutePath();

		String withoutSuffix = removeSuffix(path);
		String withSuffix = withoutSuffix + TMP_SUFFIX;
		return withSuffix;
	}

	private List<String> createCommandLine(String url, String path) {
		return Arrays.asList( //
				"youtube-dl", "--newline", "--extract-audio", "--audio-format", "mp3", "--output", path,
				"--exec",  "'ffmpeg -stats -i {} /tmp/tracks/out.mp3'",
				/*"--postprocessor-args", "'--stats'",*/ url);
		
		
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void handleProcessOutput() {
		InputStream ins = process.getErrorStream();
		Reader r = new InputStreamReader(ins);
		Scanner s = new Scanner(r);

		while (s.hasNext()) {
			String line = s.next();
			processLineOfOutput(line);
			System.err.println("> " + line);
		}

		s.close();
	}

	private void processLineOfOutput(String line) {
		Double percent = tryToParsePercentFromLine(line);
		if (percent != null) {
			progress.set(percent);
		}
	}

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

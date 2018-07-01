package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import cz.martlin.jmop.core.tracks.TrackIdentifier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class YoutubeDlDownloader implements BaseSourceDownloader {

	private static final String PROGRESS_LINE_START = "[download]";
	private static final String COLUMNS_SEPARATOR_REGEX = " +";
	private static final String PERCENT_REGEX = "\\d{1,2}\\.\\d{1}\\%";
	private static final int RESULT_CODE_OK = 0;

	private Process process;
	private DoubleProperty progress;

	public YoutubeDlDownloader() {
		this.process = null;
		this.progress = new SimpleDoubleProperty();
	}

	@Override
	public boolean download(TrackIdentifier identifier) throws IOException, InterruptedException {
		startProcess(identifier);
		handleProcessOutput();

		return finishProcess();
	}

	private void startProcess(TrackIdentifier identifier) throws IOException {
		List<String> commandline = createCommandLine(identifier);

		ProcessBuilder builder = new ProcessBuilder(commandline);
		File directory = new File("."); // TODO working directory
		builder.directory(directory);
		process = builder.start();
	}

	private boolean finishProcess() throws InterruptedException {
		int result = process.waitFor();
		
		process = null;

		return result == RESULT_CODE_OK;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private List<String> createCommandLine(TrackIdentifier identifier) {
		// TODO generate url by kind
		String url = "https://www.youtube.com/watch?v=" + identifier.getIdentifier();
		return Arrays.asList("youtube-dl", "-x", "--newline", url); // TODO
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void handleProcessOutput() {
		InputStream ins = process.getInputStream();
		Reader r = new InputStreamReader(ins);
		Scanner s = new Scanner(r);

		while (s.hasNext()) {
			String line = s.nextLine();
			processLineOfOutput(line);
			// TODO debug log: System.out.println(">" + line);
		}

		s.close();
	}

	private void processLineOfOutput(String line) {
		Double percent = tryToParsePercentFromLine(line);
		if (percent != null) {
			double progressVal = percent / 100.0;
			progress.set(progressVal);
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

}

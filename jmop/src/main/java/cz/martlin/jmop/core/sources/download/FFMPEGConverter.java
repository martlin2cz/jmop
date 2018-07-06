package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.Sources;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class FFMPEGConverter extends AbstractProcessEncapusulation<Track, Boolean> implements BaseSourceConverter {

	private static final String DURATION_SEPARATOR = ":";
	private static final String DURATION_REGEX = "\\d{2}\\:\\d{2}\\:\\d{2}";

	private static final String DURATION_LINE_REGEX = "^.*Duration: " + DURATION_REGEX + ".*$";
	private static final String PROGRESS_LINE_REGEX = "^size\\=[ \\dkMG]+B time\\=" + DURATION_REGEX + ".*$";
	private static final Pattern DURATION_PATTERN = Pattern.compile(DURATION_REGEX);
	private static final int TIME_UNIT_MULTIPLICATOR = 60;

	private final TrackFileFormat inputFormat;
	private final TrackFileFormat outputFormat;
	private final BaseLocalSource local;

	private Integer inputDuration;

	public FFMPEGConverter(Sources sources, //
			TrackFileFormat inputFormat, TrackFileFormat outputFormat, ProgressListener listener) {
		super(listener);

		this.local = sources.getLocal();
		// this.remote = sources.getRemote();
		this.inputFormat = inputFormat;
		this.outputFormat = outputFormat;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean convert(Track track) throws ExternalProgramException {
		return run(track);
	}

	@Override
	protected List<String> createCommandLine(Track track) throws Exception {
		String inputFile = createInputFile(track);
		String outputFile = createOutputFile(track);

		return creteCommandLine(inputFile, outputFile);
	}

	@Override
	protected File getWorkingDirectory(Track input) throws Exception {
		return getTemporaryDirectory();
	}

	@Override
	protected InputStream getOutputStream(Process process) throws Exception {
		return process.getErrorStream();
	}

	@Override
	protected Double processLineOfOutput(String line) throws Exception {
		if (inputDuration == null) {
			Integer inputDurationOrNot = tryToProcessAsInputDuration(line);
			if (inputDurationOrNot != null) {
				inputDuration = inputDurationOrNot;
			}
		} else {
			Integer durationOrNot = tryToProcessAsProgress(line);
			if (durationOrNot != null) {
				return durationToProgress(durationOrNot);
			}
		}

		return null;
	}

	@Override
	protected Boolean handleResult(int result, Track track) throws Exception {
		removeOriginalInputFile(track);
		
		return result == 0;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private String createInputFile(Track track) throws JMOPSourceException {
		File inputFile = local.fileOfTrack(track, inputFormat);
		return inputFile.getAbsolutePath();
	}

	private String createOutputFile(Track track) throws JMOPSourceException {
		File outputFile = local.fileOfTrack(track, outputFormat);
		return outputFile.getAbsolutePath();
	}

	private List<String> creteCommandLine(String inputFile, String outputFile) {
		return Arrays.asList( //
				"ffmpeg", "-stats", "-y", "-i", inputFile, outputFile);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private double durationToProgress(int duration) {
		return (((double) duration) / inputDuration) * 100.0;
	}

	private void removeOriginalInputFile(Track track) throws JMOPSourceException {
		File inputFile = local.fileOfTrack(track, inputFormat);
		inputFile.delete();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	protected static Integer tryToProcessAsInputDuration(String line) {
		boolean matches = line.matches(DURATION_LINE_REGEX);
		if (!matches) {
			return null;
		}

		String durationStr = inferDuration(line);
		int duration = parseDuration(durationStr);

		return duration;
	}

	protected static Integer tryToProcessAsProgress(String line) {
		boolean matches = line.matches(PROGRESS_LINE_REGEX);
		if (!matches) {
			return null;
		}

		String durationStr = inferDuration(line);
		return parseDuration(durationStr);
	}

	protected static String inferDuration(String line) {
		Matcher matcher = DURATION_PATTERN.matcher(line);
		matcher.find();
		return matcher.group();
	}

	protected static int parseDuration(String durationStr) {
		String[] parts = durationStr.split(DURATION_SEPARATOR);

		int sum = 0;
		for (String part : parts) {
			sum *= TIME_UNIT_MULTIPLICATOR;
			int value = Integer.parseInt(part);
			sum += value;
		}

		return sum;
	}

}

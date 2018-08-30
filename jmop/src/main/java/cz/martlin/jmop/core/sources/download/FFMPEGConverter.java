package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter.TrackConvertData;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
//import  cz.martlin.jmop.core.sources.download.FFMPEGConverterTest.Trac;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public class FFMPEGConverter extends AbstractProcessEncapusulation<TrackConvertData, Boolean>
		implements BaseSourceConverter {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private static final String DURATION_SEPARATOR = ":";
	private static final String DURATION_REGEX = "\\d{2}\\:\\d{2}\\:\\d{2}";

	private static final String DURATION_LINE_REGEX = "^.*Duration: " + DURATION_REGEX + ".*$";
	private static final String PROGRESS_LINE_REGEX = "^size\\=[ \\dkMG]+B time\\=" + DURATION_REGEX + ".*$";
	private static final Pattern DURATION_PATTERN = Pattern.compile(DURATION_REGEX);
	private static final int TIME_UNIT_MULTIPLICATOR = 60;

	private final BaseLocalSource local;
	private Integer inputDuration;

	public FFMPEGConverter(BaseLocalSource local) {
		super();

		this.local = local;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat) throws Exception {

		LOG.info("Converting track " + track + " from " + fromFormat + " to " + toFormat);
		TrackConvertData data = new TrackConvertData(track, fromLocation, fromFormat, toLocation, toFormat);
		return run(data);
	}

	@Override
	protected List<String> createCommandLine(TrackConvertData data) throws Exception {
		String inputFile = createInputFile(data);
		String outputFile = createOutputFile(data);

		return creteCommandLine(inputFile, outputFile);
	}

	@Override
	protected File getWorkingDirectory(TrackConvertData data) throws Exception {
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
	protected Boolean handleResult(int result, TrackConvertData data) throws Exception {
		return result == 0;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private String createInputFile(TrackConvertData data) throws JMOPSourceException {
		Track track = data.getTrack();
		TrackFileLocation inputLocation = data.getFromLocation();
		TrackFileFormat inputFormat = data.getFromFormat();

		File inputFile = local.fileOfTrack(track, inputLocation, inputFormat);
		return inputFile.getAbsolutePath();
	}

	private String createOutputFile(TrackConvertData data) throws JMOPSourceException {
		Track track = data.getTrack();
		TrackFileLocation outputLocation = data.getToLocation();
		TrackFileFormat outputFormat = data.getToFormat();

		File outputFile = local.fileOfTrack(track, outputLocation, outputFormat);
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

	protected static class TrackConvertData {
		private final Track track;
		private final TrackFileLocation fromLocation;
		private final TrackFileFormat fromFormat;
		private final TrackFileLocation toLocation;
		private final TrackFileFormat toFormat;

		public TrackConvertData(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
				TrackFileLocation toLocation, TrackFileFormat toFormat) {
			super();
			this.track = track;
			this.fromLocation = fromLocation;
			this.fromFormat = fromFormat;
			this.toLocation = toLocation;
			this.toFormat = toFormat;
		}

		public Track getTrack() {
			return track;
		}

		public TrackFileLocation getFromLocation() {
			return fromLocation;
		}

		public TrackFileFormat getFromFormat() {
			return fromFormat;
		}

		public TrackFileLocation getToLocation() {
			return toLocation;
		}

		public TrackFileFormat getToFormat() {
			return toFormat;
		}

	}
}

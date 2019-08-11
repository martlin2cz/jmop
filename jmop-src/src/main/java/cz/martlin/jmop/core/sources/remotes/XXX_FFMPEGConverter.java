package cz.martlin.jmop.core.sources.remotes;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.XXX_AbstractProgramEncapusulation;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.XXX_ProgressListener;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
//import  cz.martlin.jmop.core.sources.download.FFMPEGConverterTest.Trac;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceConverter;
import cz.martlin.jmop.core.sources.remotes.XXX_FFMPEGConverter.TrackConvertData;

/**
 * The converted working by ffmpeg program.
 * 
 * @author martin
 *
 */
@Deprecated
public class XXX_FFMPEGConverter extends XXX_AbstractProgramEncapusulation<TrackConvertData, Boolean>
		implements XXX_BaseSourceConverter {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private static final String DURATION_SEPARATOR = ":"; //$NON-NLS-1$
	private static final String DURATION_REGEX = "\\d{2}\\:\\d{2}\\:\\d{2}"; //$NON-NLS-1$

	private static final String DURATION_LINE_REGEX = "^.*Duration: " + DURATION_REGEX + ".*$"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String PROGRESS_LINE_REGEX = "^size\\=[ \\dkMG]+B time\\=" + DURATION_REGEX + ".*$"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final Pattern DURATION_PATTERN = Pattern.compile(DURATION_REGEX);
	private static final int TIME_UNIT_MULTIPLICATOR = 60;
	private static final int RESULT_CODE_OK = 0;

	private final BaseLocalSource local;
	private Integer inputDuration;

	public XXX_FFMPEGConverter(BaseLocalSource local) {
		super();

		this.local = local;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean convert(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat, XXX_ProgressListener listener) throws Exception {

		LOG.info("Converting track " + track + " from " + fromFormat + " to " + toFormat); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		TrackConvertData data = new TrackConvertData(track, fromLocation, fromFormat, toLocation, toFormat);
		return run(data, listener);
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
		return result == RESULT_CODE_OK;
	}

	@Override
	public boolean check() {
		return runAndCheckForResult("ffmpeg -version") == RESULT_CODE_OK; //$NON-NLS-1$
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creates input file parameter.
	 * 
	 * @param data
	 * @return
	 * @throws JMOPSourceException
	 */
	private String createInputFile(TrackConvertData data) throws JMOPSourceException {
		Track track = data.getTrack();
		TrackFileLocation inputLocation = data.getFromLocation();
		TrackFileFormat inputFormat = data.getFromFormat();

		File inputFile = local.fileOfTrack(track, inputLocation, inputFormat);
		return inputFile.getAbsolutePath();
	}

	/**
	 * Creates output file parameter.
	 * 
	 * @param data
	 * @return
	 * @throws JMOPSourceException
	 */
	private String createOutputFile(TrackConvertData data) throws JMOPSourceException {
		Track track = data.getTrack();
		TrackFileLocation outputLocation = data.getToLocation();
		TrackFileFormat outputFormat = data.getToFormat();

		File outputFile = local.fileOfTrack(track, outputLocation, outputFormat);
		return outputFile.getAbsolutePath();
	}

	/**
	 * Creates the command itself.
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	private List<String> creteCommandLine(String inputFile, String outputFile) {
		return Arrays.asList( //
				"ffmpeg", "-stats", "-y", "-i", inputFile, outputFile); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts given (current) duration into progress.
	 * 
	 * @param duration
	 * @return
	 */
	private double durationToProgress(int duration) {
		return (((double) duration) / inputDuration) * 100.0;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Processes given line as line with input file duration. In fact checks for
	 * such line pattern, and if matches, infers and parses duration.
	 * 
	 * @param line
	 * @return
	 */
	protected static Integer tryToProcessAsInputDuration(String line) {
		boolean matches = line.matches(DURATION_LINE_REGEX);
		if (!matches) {
			return null;
		}

		String durationStr = inferDuration(line);
		int duration = parseHumanDuration(durationStr);

		return duration;
	}

	/**
	 * Processes given line as line with current progress duration. In fact
	 * checks for such line pattern, and if matches, infers and parses duration.
	 * 
	 * @param line
	 * @return
	 */
	protected static Integer tryToProcessAsProgress(String line) {
		boolean matches = line.matches(PROGRESS_LINE_REGEX);
		if (!matches) {
			return null;
		}

		String durationStr = inferDuration(line);
		return parseHumanDuration(durationStr);
	}

	/**
	 * Infers the duration from the line. In fact returns first match of the
	 * duration pattern occurence.
	 * 
	 * @param line
	 * @return
	 */
	protected static String inferDuration(String line) {
		Matcher matcher = DURATION_PATTERN.matcher(line);
		matcher.find();
		return matcher.group();
	}

	/**
	 * Parses string duration into integer (number of seconds).
	 * 
	 * @param durationStr
	 * @return
	 */
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

	/**
	 * The input to the process (track, input and output location and format).
	 * 
	 * @author martin
	 *
	 */
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

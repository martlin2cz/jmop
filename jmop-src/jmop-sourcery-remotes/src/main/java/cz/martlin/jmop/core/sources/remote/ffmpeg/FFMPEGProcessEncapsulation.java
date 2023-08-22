package cz.martlin.jmop.core.sources.remote.ffmpeg;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;
import javafx.util.Duration;

/**
 * The FFMPEG process. Does all the output parsing and stuff.
 * 
 * @author martin
 *
 */
public class FFMPEGProcessEncapsulation extends AbstractProcessEncapsulation {

	private static final String DURATION_REGEX = "\\d{2}\\:\\d{2}\\:\\d{2}"; //$NON-NLS-1$

	private static final String PROGRESS_LINE_REGEX = "^size\\=[ \\dkMG]+B time\\=" + DURATION_REGEX + ".*$"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final Pattern DURATION_PATTERN = Pattern.compile(DURATION_REGEX);

	private static final int RESULT_CODE_OK = 0;

	private final int totalSeconds;
	private final File targetFile;
	
	public FFMPEGProcessEncapsulation(String command, List<String> arguments, File workingDirectory, File targetFile, Duration duration) {
		super(command, arguments, workingDirectory);
		
		this.targetFile = targetFile;
		this.totalSeconds = (int) Math.ceil(duration.toSeconds());
	}

	@Override
	protected InputStream getStreamWithOutput(Process process) {
		return process.getErrorStream();
	}

	@Override
	protected Double processLineOfOutput(String line) {
		Integer time = tryToProcessAsProgress(line);
		if (time != null) {
			return durationToProgress(time);
		} else {
			return null;
		}
	}

	@Override
	protected int getExpectedResultCode() {
		return RESULT_CODE_OK;
	}
	
	@Override
	protected void deleteSubResult() throws Exception {
		Path targetPath = targetFile.toPath();
		Files.delete(targetPath);
	}

	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Converts given (current) duration into progress.
	 * 
	 * @param duration
	 * @return
	 */
	private double durationToProgress(int duration) {
		return (((double) duration) / totalSeconds) * 100.0;
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
		Duration durationDur = DurationUtilities.parseHumanDuration(durationStr);
		return (int) Math.ceil(durationDur.toSeconds());
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


}

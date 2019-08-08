package cz.martlin.jmop.core.sources.remote.youtubedl;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;

public class YoutubeDLProcessEncapsulation extends AbstractProcessEncapsulation {
	private static final String COMMAND_NAME = "youtube-dl"; //$NON-NLS-1$
	
	private static final String PROGRESS_LINE_START = "[download]"; //$NON-NLS-1$
	private static final String COLUMNS_SEPARATOR_REGEX = " +"; //$NON-NLS-1$
	private static final String PERCENT_REGEX = "\\d{1,3}\\.\\d{1}\\%"; //$NON-NLS-1$
	private static final int RESULT_CODE_OK = 0;

	public YoutubeDLProcessEncapsulation(List<String> arguments,
			File workingDirectory) {
		super(COMMAND_NAME, arguments, workingDirectory);
	}

	@Override
	protected InputStream getStreamWithOutput(Process process) {
		return process.getInputStream();
	}

	@Override
	protected Double processLineOfOutput(String line) {
		return tryToParsePercentFromLine(line);
	}

	@Override
	protected int getExpectedResultCode() {
		return RESULT_CODE_OK;
	}
	///////////////////////////////////////////////////////////////////////////
	
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
	

}

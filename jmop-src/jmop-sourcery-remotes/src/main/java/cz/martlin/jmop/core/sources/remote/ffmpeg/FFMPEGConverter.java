package cz.martlin.jmop.core.sources.remote.ffmpeg;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;
import cz.martlin.jmop.sourcery.remote.BaseConverter;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;
import javafx.util.Duration;

/**
 * The FFMPEG program based converter.
 * 
 * @author martin
 *
 */
public class FFMPEGConverter implements BaseConverter {
//	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final String FFMPEG_COMMAND_NAME = "ffmpeg";

	private final BaseProgressListener listener;

	public FFMPEGConverter(BaseProgressListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	public void convert(Track track, File from, File to) throws JMOPSourceryException {
		AbstractProcessEncapsulation process = prepareProcess(track, from, to);

		try {
			process.run(listener);
		} catch (ExternalProgramException e) {
			throw new JMOPSourceryException("Download process failed", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Prepares the FFMPEG object.
	 * 
	 * @param track
	 * @param from
	 * @param to
	 * @return
	 */
	private AbstractProcessEncapsulation prepareProcess(Track track, File from, File to) {
		File workingDirectory = AbstractProcessEncapsulation.currentDirectory();
		List<String> arguments = createArguments(track, from, to);
		Duration duration = track.getDuration();

		String command = FFMPEG_COMMAND_NAME;

		AbstractProcessEncapsulation process = new FFMPEGProcessEncapsulation(command, arguments, workingDirectory, to,
				duration);
		return process;
	}

	private List<String> createArguments(Track track, File fromFile, File toFile) {

		String fromPath = fromFile.getAbsolutePath();
		String toPath = toFile.getAbsolutePath();
		
		return createCommandLine(fromPath, toPath);
	}

	private List<String> createCommandLine(String fromPath, String toPath) {
		return Arrays.asList( //
				"-stats", "-y", "-i", fromPath, toPath);
	}
}

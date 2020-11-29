package cz.martlin.jmop.core.player;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.BaseTrackFileLocator;
import javafx.util.Duration;

/**
 * Simple testing player working by invoking aplay unix command.
 * 
 * Warning: does not support pause, resume and seek.
 * 
 * @author martin
 *
 */
public class AplayPlayer extends AbstractPlayer {
	private static final TrackFileFormat APLAY_PLAY_FORMAT = TrackFileFormat.WAV;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseErrorReporter reporter;
	private AplayProcess process;
	private Track currentTrack;

	public AplayPlayer(BaseErrorReporter reporter, XXX_BaseLocalSource local, BaseTrackFileLocator locator) {
		super(local, locator, APLAY_PLAY_FORMAT);

		this.reporter = reporter;
	}

	@Override
	public Duration currentTime() {
		return new Duration(0);
	}

	@Override
	protected void doStartPlaying(Track track, File file) {
		AplayProcess process = new AplayProcess(file);

		runProcessInBackround(process, track, file);

		this.process = process;
	}

	@Override
	protected void doStopPlaying() {
		this.process.terminate();
	}

	@Override
	protected void doPausePlaying() {
		LOG.warn("Pause not supported, will stop plaing"); //$NON-NLS-1$
		stop();
	}

	@Override
	protected void doResumePlaying() {
		LOG.warn("Resume not supported, will play from begin"); //$NON-NLS-1$
		try {
			startPlaying(currentTrack);
		} catch (JMOPSourceException e) {
			reporter.report(e);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}

	@Override
	protected void doSeek(Duration to) {
		LOG.warn("Seek not supported, will ignore"); //$NON-NLS-1$
	}

	/**
	 * Runs the player process.
	 * 
	 * @param process
	 * @param track
	 * @param file
	 */
	private void runProcessInBackround(AplayProcess process, Track track, File file) {
		Runnable run = () -> {
			try {
				process.run(null);
			} catch (ExternalProgramException e) {
				reporter.report(e);
			} catch (Exception e) {
				reporter.internal(e);
			}

			trackFinished();
		};

		Thread thread = new Thread(run, "AplayPlayerThread"); //$NON-NLS-1$
		thread.start();
	}

	/**
	 * Abstract aplay process encapsulation.
	 * 
	 * @author martin
	 *
	 */
	public static class AplayProcess extends AbstractProcessEncapsulation {

		private static final String COMMAND_NAME = "aplay";//$NON-NLS-1$
		private static final int STATUS_CODE_SUCESS = 0;

		public AplayProcess(File file) {
			super(COMMAND_NAME, createCommandLine(file), getWorkingDirectory());
		}

		private static List<String> createCommandLine(File input) {
			String path = input.getAbsolutePath();
			return Arrays.asList(path);
		}

		private static File getWorkingDirectory() {
			return new File("."); // whatever //$NON-NLS-1$
		}

		@Override
		protected int getExpectedResultCode() {
			return STATUS_CODE_SUCESS;
		}

		@Override
		protected InputStream getStreamWithOutput(Process process) {
			return process.getInputStream();
		}

		@Override
		protected Double processLineOfOutput(String line) {
			return null;
		}

		@Override
		protected void deleteSubResult() {
			// we don't have anything like that

		}
	}

}

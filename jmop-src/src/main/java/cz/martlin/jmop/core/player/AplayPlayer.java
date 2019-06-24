package cz.martlin.jmop.core.player;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.AbstractProcessEncapusulation;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
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

	private final ErrorReporter reporter;
	private AplayProcess process;
	private Track currentTrack;

	public AplayPlayer(ErrorReporter reporter, BaseLocalSource local, AbstractTrackFileLocator locator) {
		super(local, locator, APLAY_PLAY_FORMAT);

		this.reporter = reporter;
	}

	@Override
	public Duration currentTime() {
		return new Duration(0);
	}

	@Override
	protected void doStartPlaying(Track track, File file) {
		AplayProcess process = new AplayProcess();

		runProcessInBackround(process, track, file);

		this.process = process;
	}

	@Override
	protected void doStopPlaying() {
		this.process.stop();
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
				process.run(file, null);
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
	public static class AplayProcess extends AbstractProcessEncapusulation<File, Void> {

		public AplayProcess() {
			super();
		}

		@Override
		protected List<String> createCommandLine(File input) throws Exception {
			return Arrays.asList("aplay", input.getAbsolutePath()); //$NON-NLS-1$
		}

		@Override
		protected File getWorkingDirectory(File input) throws Exception {
			return new File("."); // whatever //$NON-NLS-1$
		}

		@Override
		protected InputStream getOutputStream(Process process) throws Exception {
			return process.getInputStream();
		}

		@Override
		protected Double processLineOfOutput(String line) throws Exception {
			return null; // no output
		}

		@Override
		protected Void handleResult(int result, File input) throws Exception {
			return null; // no result
		}

	}

}

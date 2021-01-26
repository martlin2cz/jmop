package cz.martlin.jmop.player.players;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;
import javafx.util.Duration;

/**
 * Simple testing player working by invoking aplay unix command.
 * 
 * Warning: does not support pause, resume and seek.
 * 
 * @author martin
 *
 */
public class AplayPlayer extends AbstractTrackFilePlaingPlayer {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseErrorReporter reporter;
	private AplayProcess process;
	private File currentTrackFile;
	private Track currentTrack;

	public AplayPlayer(BaseErrorReporter reporter, TracksSource local) {
		super(local);

		this.reporter = reporter;
	}

	///////////////////////////////////////////////////////////////////////////s
	
	@Override
	public Duration currentTime() {
		LOG.warn("Current time not supported, returning zero"); //$NON-NLS-1$
		return new Duration(0);
	}

	@Override
	protected void doStartPlaingFile(Track track, File file) {
		this.currentTrack = track;
		this.currentTrackFile = file;
		
		startTheAplay();
	}

	@Override
	protected void doStopPlaying() {
		if (currentStatus().isPlaying()) {
			stopTheAplay();
			
			this.currentTrack = null;
			this.currentTrackFile = null;
		}
	}

	@Override
	protected void doPausePlaying() {
		LOG.warn("Pause not supported, will stop playing"); //$NON-NLS-1$
		stopTheAplay();
	}

	@Override
	protected void doResumePlaying() {
		LOG.warn("Resume not supported, will play from begin"); //$NON-NLS-1$
//		try {
			startTheAplay();
//		} catch (JMOPMusicbaseException e) {
//			reporter.report(e);
//		} catch (Exception e) {
//			reporter.internal(e);
//		}
	}

	@Override
	protected void doSeek(Duration to) {
		LOG.warn("Seek not supported, will ignore"); //$NON-NLS-1$
	}
	
	@Override
	protected void doTrackFinished() {
		// okay
	}

	///////////////////////////////////////////////////////////////////////////

	private void startTheAplay() {
		AplayProcess process = new AplayProcess(currentTrackFile);

		runProcessInBackround(process);

		this.process = process;
	}
	
	private void stopTheAplay() {
		this.process.terminate();
		this.process = null;
	}

	
	/**
	 * Runs the player process.
	 * 
	 * @param process
	 */
	private void runProcessInBackround(AplayProcess process) {
		Runnable run = () -> {
			try {
				process.run(null);
			} catch (ExternalProgramException e) {
				reporter.report("Could not run the aplay", e);
			} catch (Exception e) {
				reporter.internal(e);
			}

			// the process terminated, but we don't know whether by
			// finishing the track or by termination
			if (currentStatus().isPlaying()) {
				trackFinished();
			}
		};

		Thread thread = new Thread(run, "AplayPlayerThread"); //$NON-NLS-1$
		thread.start();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Abstract aplay process encapsulation.
	 * 
	 * @author martin
	 *
	 */
	public static class AplayProcess extends AbstractProcessEncapsulation {

		private static final String COMMAND_NAME = "aplay";//$NON-NLS-1$
		private static final int STATUS_CODE_SUCESS = 0;
		private static final int STATUS_CODE_TERMINATED = 1;

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
			//FIXME if the aplay ends normally by plaing the whole track,
			// returns STATUS_CODE_SUCESS. However, when terminated prematurelly,
			// returns STATUS_CODE_TERMINATED. Both of theese are valid and expected
			// outcomes.
			//TODO Override the finishProces method and rewrite this behaviour. 
			// But that's hack AF.
			return STATUS_CODE_TERMINATED;
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

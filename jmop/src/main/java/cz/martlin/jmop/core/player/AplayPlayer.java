package cz.martlin.jmop.core.player;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.download.AbstractProcessEncapusulation;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class AplayPlayer extends AbstractPlayer {
	private static final TrackFileFormat APLAY_PLAY_FORMAT = TrackFileFormat.WAV;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private AplayProcess process;
	private Track currentTrack;

	public AplayPlayer(BaseLocalSource local) {
		super(local, APLAY_PLAY_FORMAT);
	}

	@Override
	protected void doStartPlaying(Track track, File file) {
		AplayProcess process = new AplayProcess();

		runProcessInBackround(process, track, file, getHandler());
		
		this.process = process;
	}

	@Override
	protected void doStopPlaying() {
		this.process.stop();
	}

	@Override
	protected void doPausePlaying() {
		LOG.warn("Pause not supported, will stop plaing");
		stop();
	}

	@Override
	protected void doResumePlaying() {
		LOG.warn("Resume not supported, will play from begin");
		try {
			startPlayling(currentTrack);
		} catch (JMOPSourceException e) {
			// TODO error report
			e.printStackTrace();
		}
	}

	private void runProcessInBackround(AplayProcess process, Track track, File file, TrackPlayedHandler handler) {
		Runnable run = () -> {
			try {
				process.run(file);
			} catch (ExternalProgramException e) {
				e.printStackTrace();
			}

			if (handler != null) {
				handler.trackPlayed(track);
			}
		};

		Thread thread = new Thread(run, "AplayPlayerThread");
		thread.start();
	}


	public class AplayProcess extends AbstractProcessEncapusulation<File, Void> {

		public AplayProcess() {
			super();
		}

		@Override
		protected List<String> createCommandLine(File input) throws Exception {
			return Arrays.asList("aplay", input.getAbsolutePath());
		}

		@Override
		protected File getWorkingDirectory(File input) throws Exception {
			return new File("."); // whatever
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

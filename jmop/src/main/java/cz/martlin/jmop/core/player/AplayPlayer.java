package cz.martlin.jmop.core.player;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.sources.download.AbstractProcessEncapusulation;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

public class AplayPlayer extends WavPlayer {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private TrackPlayedHandler handler;
	private AplayProcess process;
	private File file;



	public AplayPlayer(BaseLocalSource local) {
		super(local);
	}
	
	public void setHandler(TrackPlayedHandler handler) {
		this.handler = handler;
	}

	@Override
	public void playWAVfile(File file, Track track) {
		this.file = file;
		this.process = new AplayProcess();
		
		try {
			process.run(file);
		} catch (ExternalProgramException e) {
			e.printStackTrace();
		}
		
		if (handler != null) {
			handler.trackPlayed(track);
		}
	}

	@Override
	public void stopPlaying() {
		process.stop();
	}

	@Override
	public void pause() {
		LOG.warn("Pause not supported, will stop plaing");
		process.stop();
	}

	@Override
	public void resume() {
		LOG.warn("Resume not supported, will play from begin");
		playWAVfile(file, null);
	}

	public class AplayProcess extends AbstractProcessEncapusulation<File, Void> {

		public AplayProcess() {
			super(null);
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

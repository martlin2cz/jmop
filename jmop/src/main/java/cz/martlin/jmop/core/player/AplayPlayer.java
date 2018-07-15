package cz.martlin.jmop.core.player;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.sources.download.AbstractProcessEncapusulation;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

public class AplayPlayer extends WavPlayer {

	private AplayProcess process;

	public AplayPlayer(BaseLocalSource local) {
		super(local);
	}

	@Override
	public void playWAVfile(File file) {
		process = new AplayProcess();
		try {
			process.run(file);
		} catch (ExternalProgramException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopPlaying() {
		process.stop();
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

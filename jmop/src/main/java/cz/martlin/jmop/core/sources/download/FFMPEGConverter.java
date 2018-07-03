package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.Sources;

public class FFMPEGConverter extends AbstractProcessEncapusulation<File, Boolean> implements BaseSourceConverter {

	public FFMPEGConverter(Sources sources, ProgressListener listener) {
		super(listener);
		//this.local = sources.getLocal();
		//this.remote = sources.getRemote();
	}

	@Override
	public boolean convert(File infile) throws ExternalProgramException {
		return run(infile);
	}

	@Override
	protected List<String> createCommandLine(File input) throws Exception {
		return Arrays.asList( //
				"ffmpeg", "TODO");
		//TODO args
	}

	@Override
	protected File getWorkingDirectory(File input) throws Exception {
		return getTemporaryDirectory();
	}

	@Override
	protected InputStream getOutputStream(Process process) throws Exception {
		return process.getErrorStream();
	}

	double count = 0;
	@Override
	protected Double processLineOfOutput(String line) throws Exception {
		System.out.println(line);
		//TODO here
		return count ++;
	}

	@Override
	protected Boolean handleResult(int result) throws Exception {
		return result == 0;
	}
	
	

}

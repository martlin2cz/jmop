package cz.martlin.jmop.core.sources.remote.youtubedl;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;

public class YoutubeDLProcessEncapsulation extends AbstractProcessEncapsulation {

	
	private static final String COMMAND_NAME = "youtube-dl";

	public YoutubeDLProcessEncapsulation(BaseProgressListener listener, List<String> arguments,
			File workingDirectory) {
		super(listener, COMMAND_NAME, arguments, workingDirectory);
	}

	@Override
	protected InputStream getStreamWithOutput(Process process) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Double processLineOfOutput(String line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getExpectedResultCode() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package cz.martlin.jmop.core.source.extprogram;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;

public abstract class AbstractProcessEncapsulation {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final List<String> commandLine;
	private final File workingDirectory;

	private Process process;
	
	public AbstractProcessEncapsulation(String command, List<String> arguments,
			File workingDirectory) {
		this(commandLine(command, arguments), workingDirectory);
	}
	
	
	protected AbstractProcessEncapsulation(List<String> commandLine,
			File workingDirectory) {
		super();
		this.commandLine = commandLine;
		this.workingDirectory = workingDirectory;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void run(BaseProgressListener listener) throws ExternalProgramException {
		try {
			prepareProcess();
			handleProcessOutput(listener);
			finishProcess();
		} catch (Exception e) {
			throw new ExternalProgramException("The external process failed", e);
		}
	}

	public void terminate() {
		killTheProcess();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void prepareProcess() throws ExternalProgramException {
		process = createEclipseProcess();
	}

	private Process createEclipseProcess() throws ExternalProgramException {
		try {
			ProcessBuilder builder = new ProcessBuilder(commandLine);
			builder.directory(workingDirectory);
			return builder.start();
		} catch (IOException e) {
			throw new ExternalProgramException("Cannot start external program", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void handleProcessOutput(BaseProgressListener listener) throws Exception {
		InputStream ins = getStreamWithOutput(process);
		Reader r = new InputStreamReader(ins);
		Scanner s = new Scanner(r);

		while (s.hasNext()) {
			String line = s.nextLine();
			LOG.debug(line);

			Double progressOrNot = null;
			try {
				progressOrNot = processLineOfOutput(line);
			} catch (Exception e) {
				LOG.warn("TODO", e);
			}
			if (progressOrNot != null) {
				reportProgress(listener, progressOrNot);
			}

		}

		s.close();
	}

	protected abstract InputStream getStreamWithOutput(Process process);

	protected abstract Double processLineOfOutput(String line);

	/**
	 * Reports given progress to listener (if some).
	 * @param progressOrNot 
	 * @param listener 
	 * 
	 * @param progress
	 * @param listener
	 */
	protected void reportProgress(BaseProgressListener listener, double progress) {
		if (listener != null) {
			listener.reportProgressChanged(progress);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void finishProcess() throws Exception {
		int result = process.waitFor();

		LOG.info("Process finished with code " + result); //$NON-NLS-1$

		int expectedResultCode = getExpectedResultCode();
		if (result != expectedResultCode) {
			throw new ExternalProgramException(
					"The external program ended with code " + result + " but may " + expectedResultCode);
		}
	}

	protected abstract int getExpectedResultCode();

	private void killTheProcess() {
		process.destroy();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	
	private static List<String> commandLine(String command, List<String> arguments) {
		ArrayList<String> commandLine = new ArrayList<String>(arguments.size());
		
		commandLine.add(command);
		commandLine.addAll(arguments);
		
		return commandLine;
	}

}

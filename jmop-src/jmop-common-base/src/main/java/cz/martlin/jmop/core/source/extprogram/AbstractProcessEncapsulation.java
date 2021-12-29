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

	public AbstractProcessEncapsulation(String command, List<String> arguments, File workingDirectory) {
		this(commandLine(command, arguments), workingDirectory);
	}

	protected AbstractProcessEncapsulation(List<String> commandLine, File workingDirectory) {
		super();
		this.commandLine = commandLine;
		this.workingDirectory = workingDirectory;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void run(BaseProgressListener listener) throws ExternalProgramException {
		LOG.info("Running process " + commandLine + " in " + workingDirectory);

		try {
			prepareProcess();
			handleProcessOutput(listener);
			finishProcess();
		} catch (Exception e) {
			try {
				deleteSubResult();
			} catch (Exception e2) {
				LOG.warn("Cannot delete corrupted subresult", e2);
			}

			throw new ExternalProgramException("The external process failed", e);
		}
	}

	public void terminate() throws ExternalProgramException {
		LOG.info("Terminating process " + commandLine);

		killTheProcess();
		
		try {
			deleteSubResult();
		} catch (Exception e) {
			LOG.warn("Deletion of subresult failed", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void prepareProcess() throws ExternalProgramException {
		process = createJavaProcess();
	}

	private Process createJavaProcess() throws ExternalProgramException {
		try {
			ProcessBuilder builder = new ProcessBuilder(commandLine);
			builder.directory(workingDirectory);
			return builder.start();
		} catch (IOException e) {
			throw new ExternalProgramException("Cannot start external program: " + commandLine, e);
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
				LOG.warn("Progress extraction from line " + line + " failed", e);
			}
			if (progressOrNot != null) {
				reportProgress(listener, progressOrNot);
			}

		}

		s.close();
	}

	protected abstract InputStream getStreamWithOutput(Process process);

	protected abstract Double processLineOfOutput(String line) throws Exception;

	/**
	 * Reports given progress to listener (if some).
	 * 
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
		if (process == null) {
			throw new ExternalProgramException("The process is not running"); 
		}
		
		int result = process.waitFor();

		LOG.info("Process finished with code " + result); //$NON-NLS-1$

		int expectedResultCode = getExpectedResultCode();
		if (result != expectedResultCode) {
			throw new ExternalProgramException(
					"The external program ended with code " + result + " but may " + expectedResultCode);
		}
	}

	protected abstract int getExpectedResultCode();

	/////////////////////////////////////////////////////////////////////////////////////

	private void killTheProcess() throws ExternalProgramException {
		if (process == null) {
			throw new ExternalProgramException("The process is not running"); 
		}
		
		process.destroy();
	}

	protected abstract void deleteSubResult() throws Exception;

	/////////////////////////////////////////////////////////////////////////////////////

	private static List<String> commandLine(String command, List<String> arguments) {
		ArrayList<String> commandLine = new ArrayList<String>(arguments.size());

		commandLine.add(command);
		commandLine.addAll(arguments);

		return commandLine;
	}
	
	public static File currentDirectory() {
		String path = System.getProperty("user.dir");
		return new File(path);
	}

}

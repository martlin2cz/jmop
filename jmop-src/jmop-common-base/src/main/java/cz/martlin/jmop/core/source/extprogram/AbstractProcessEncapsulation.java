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

/**
 * Encasuplator of any external process.
 * 
 * @author martin
 *
 */
public abstract class AbstractProcessEncapsulation {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final List<String> commandLine;
	private final File workingDirectory;

	private Process process;

	/**
	 * Creates.
	 * 
	 * @param command
	 * @param arguments
	 * @param workingDirectory
	 */
	public AbstractProcessEncapsulation(String command, List<String> arguments, File workingDirectory) {
		this(commandLine(command, arguments), workingDirectory);
	}

	
	protected AbstractProcessEncapsulation(List<String> commandLine, File workingDirectory) {
		super();
		this.commandLine = commandLine;
		this.workingDirectory = workingDirectory;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Runs the process.
	 * 
	 * @param listener
	 * @throws ExternalProgramException
	 */
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

	/**
	 * Terminates this process. Assuming it's running.
	 *
	 * @throws ExternalProgramException
	 */
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

	/**
	 * Prepares the process.
	 * 
	 * @throws ExternalProgramException
	 */
	private void prepareProcess() throws ExternalProgramException {
		process = createJavaProcess();
	}

	/**
	 * Constructs the process instance.
	 * 
	 * @return
	 * @throws ExternalProgramException
	 */
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

	/**
	 * Runns processing of the output.
	 * 
	 * @param listener
	 * @throws Exception
	 */
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

	/**
	 * Returns stdout or stderr, a stream with output.
	 * 
	 * @param process
	 * @return
	 */
	protected abstract InputStream getStreamWithOutput(Process process);

	/**
	 * Parses progress from the given line of output.
	 * 
	 * @param line
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * Finishes the process. Awaits its end and cheks expected result code.
	 * 
	 * @throws Exception
	 */
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

	/**
	 * Returns the expected exit code (0 usually).
	 * @return
	 */
	protected abstract int getExpectedResultCode();

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Kills the process.
	 * 
	 * @throws ExternalProgramException
	 */
	private void killTheProcess() throws ExternalProgramException {
		if (process == null) {
			throw new ExternalProgramException("The process is not running"); 
		}
		
		process.destroy();
	}

	/**
	 * IF generated some temporary sub-result, this method may delete it.
	 * 
	 * @throws Exception
	 */
	protected abstract void deleteSubResult() throws Exception;

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Constructs the command line.
	 * 
	 * @param command
	 * @param arguments
	 * @return
	 */
	private static List<String> commandLine(String command, List<String> arguments) {
		ArrayList<String> commandLine = new ArrayList<String>(arguments.size());

		commandLine.add(command);
		commandLine.addAll(arguments);

		return commandLine;
	}
	
	/**
	 * Returns the current directy for the execution.
	 * 
	 * @return
	 */
	public static File currentDirectory() {
		String path = System.getProperty("user.dir");
		return new File(path);
	}

}

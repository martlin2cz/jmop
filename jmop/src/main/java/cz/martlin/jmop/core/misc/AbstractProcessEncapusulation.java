package cz.martlin.jmop.core.misc;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

/**
 * Class encapsulating system process, program started by execution of some
 * command. This class specifies abstract way how to convert input data into
 * command, this class then executes the command and passes its output to
 * process it by subclass. When execution finishes, it handles the result (error
 * code).
 * 
 * @author martin
 *
 * @param <INT>
 *            type of input data
 * @param <OUT>
 *            type of output data
 */
public abstract class AbstractProcessEncapusulation<INT, OUT> implements ProgressGenerator {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private ProgressListener listener;
	protected Process process;

	public AbstractProcessEncapusulation() {
		super();
		this.listener = null;
	}

	@Override
	public void specifyListener(ProgressListener listener) {
		this.listener = listener;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Runs the process with the given input.
	 * 
	 * @param input
	 *            the input
	 * @return the output
	 * @throws ExternalProgramException
	 */
	public OUT run(INT input) throws ExternalProgramException {
		try {
			startProcess(input);

			handleProcessOutput();

			return finishProcess(input);
		} catch (Exception e) {
			throw new ExternalProgramException("Process failed", e);
		}
	}

	/**
	 * Kills the process.
	 */
	public void stop() {
		killTheProcess();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Starts execution of the process. In fact creates command by
	 * {@link #createCommandLine(Object)}, java Process and starts it.
	 * 
	 * @param input
	 * @throws Exception
	 */
	private void startProcess(INT input) throws Exception {
		List<String> commandline = createCommandLine(input);
		LOG.info("Starting process " + commandline);

		ProcessBuilder builder = new ProcessBuilder(commandline);

		File directory = getWorkingDirectory(input);
		builder.directory(directory);

		process = builder.start();
	}

	/**
	 * Reads the output of the process and passes it into
	 * {@link #processLineOfOutput(String)}.
	 * 
	 * @throws Exception
	 */
	private void handleProcessOutput() throws Exception {
		InputStream ins = getOutputStream(process);
		Reader r = new InputStreamReader(ins);
		Scanner s = new Scanner(r);

		while (s.hasNext()) {
			String line = s.nextLine();
			LOG.debug(line);

			Double progressOrNot = processLineOfOutput(line);
			if (progressOrNot != null) {
				reportProgress(progressOrNot);
			}

		}

		s.close();
	}

	/**
	 * Reports given progress to {@link #listener} (if some).
	 * 
	 * @param progress
	 */
	private void reportProgress(double progress) {
		if (listener != null) {
			listener.progressChanged(progress);
		}
	}

	/**
	 * Waits until the process ends and then handles the result code (by
	 * {@link #handleResult(int, Object)}).
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private OUT finishProcess(INT input) throws Exception {
		int result = process.waitFor();

		process = null;

		LOG.info("Process finished with code " + result);

		return handleResult(result, input);
	}

	/**
	 * Does kill the process.
	 */
	private void killTheProcess() {
		process.destroy();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * For given input create command to be executed.
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	protected abstract List<String> createCommandLine(INT input) throws Exception;

	/**
	 * For given input return working directory.
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	protected abstract File getWorkingDirectory(INT input) throws Exception;

	/**
	 * For given process choose stderr ( {@link Process#getErrorStream()} ) or
	 * stdout ( {@link Process#getInputStream()} ) and return.
	 * 
	 * @param process
	 * @return
	 * @throws Exception
	 */
	protected abstract InputStream getOutputStream(Process process) throws Exception;

	/**
	 * Process somehow line of output at stderr/stdout. Return non-null value,
	 * if line contains some progress information (if so, it shall be
	 * percentage, i.e. value from 0.0).
	 * 
	 * @param line
	 * @return
	 * @throws Exception
	 */
	protected abstract Double processLineOfOutput(String line) throws Exception;

	/**
	 * For given input and given result code of the process, create the output.
	 * 
	 * @param result
	 * @param input
	 * @return
	 * @throws Exception
	 */
	protected abstract OUT handleResult(int result, INT input) throws Exception;

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Utility method for subclasses, creates and returns temporary directory.
	 * 
	 * @return
	 */
	public static File getTemporaryDirectory() {
		return Files.createTempDir();
	}

	/**
	 * Simply executes given command, awaits the finish and returns the result
	 * code. Usefull for checks, like "foo --version".
	 * 
	 * @param command
	 * @return
	 */
	public static int runAndCheckForResult(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			int result = process.waitFor();
			return result;
		} catch (Exception e) {
			return -1;
		}
	}

}
package cz.martlin.jmop.core.sources.remote;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.ProgressGenerator;
import cz.martlin.jmop.core.misc.ProgressListener;

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

	public OUT run(INT input) throws ExternalProgramException {
		try {
			startProcess(input);

			handleProcessOutput();

			return finishProcess(input);
		} catch (Exception e) {
			throw new ExternalProgramException("Process failed", e);
		}
	}

	public void stop() {
		killTheProcess();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private void startProcess(INT input) throws Exception {
		List<String> commandline = createCommandLine(input);
		LOG.info("Starting process " + commandline);

		ProcessBuilder builder = new ProcessBuilder(commandline);

		File directory = getWorkingDirectory(input);
		builder.directory(directory);

		process = builder.start();
	}

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

	private void reportProgress(double progress) {
		if (listener != null) {
			listener.progressChanged(progress);
		}
	}

	private OUT finishProcess(INT input) throws Exception {
		int result = process.waitFor();

		process = null;

		LOG.info("Process finished with code " + result);

		return handleResult(result, input);
	}

	private void killTheProcess() {
		process.destroy();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	protected abstract List<String> createCommandLine(INT input) throws Exception;

	protected abstract File getWorkingDirectory(INT input) throws Exception;

	protected abstract InputStream getOutputStream(Process process) throws Exception;

	protected abstract Double processLineOfOutput(String line) throws Exception;

	protected abstract OUT handleResult(int result, INT input) throws Exception;

	/////////////////////////////////////////////////////////////////////////////////////

	public static File getTemporaryDirectory() {
		return Files.createTempDir();
	}

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
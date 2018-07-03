package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import com.google.common.io.Files;

import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.ProgressListener;

public abstract class AbstractProcessEncapusulation<INT, OUT> {

	private final ProgressListener listener;
	protected Process process;

	public AbstractProcessEncapusulation(ProgressListener listener) {
		super();
		this.listener = listener;
	}

	public OUT run(INT input) throws ExternalProgramException {
		try {
			startProcess(input);

			handleProcessOutput();

			return finishProcess();
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
		System.err.println("Running: " + commandline);

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
			System.err.println("> " + line);

			Double progressOrNot = processLineOfOutput(line);
			if (progressOrNot != null) {
				listener.progressChanged(progressOrNot);
			}

		}

		s.close();
	}

	private OUT finishProcess() throws Exception {
		int result = process.waitFor();

		process = null;

		return handleResult(result);
	}

	private void killTheProcess() {
		process.destroy();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	protected abstract List<String> createCommandLine(INT input) throws Exception;

	protected abstract File getWorkingDirectory(INT input) throws Exception;

	protected abstract InputStream getOutputStream(Process process) throws Exception;

	protected abstract Double processLineOfOutput(String line) throws Exception;

	protected abstract OUT handleResult(int result) throws Exception;

	/////////////////////////////////////////////////////////////////////////////////////

	public static File getTemporaryDirectory() {
		return Files.createTempDir();
	}
}
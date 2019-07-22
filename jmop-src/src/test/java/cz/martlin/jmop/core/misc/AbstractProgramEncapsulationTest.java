package cz.martlin.jmop.core.misc;

import static org.junit.Assume.assumeNoException;

import java.io.File;

import org.junit.Test;

import cz.martlin.jmop.core.player.AplayPlayer.AplayProcess;
import cz.martlin.jmop.core.sources.remote.empty.TestingDownloader;

/**
 * Some simple test for {@link AbstractProgramEncapusulation}. Currently, checks
 * only the basic usage, without progress reporting.
 * 
 * @author martin
 *
 */
public class AbstractProgramEncapsulationTest {

	/**
	 * Just runs {@link AplayProcess}.
	 * 
	 * @throws ExternalProgramException
	 */
	@Test
	public void testSimplyOneRun() throws ExternalProgramException {
		File input = prepareTestingFile();
		ProgressListener listener = null;

		AplayProcess process = new AplayProcess();

		System.out.println("Starting with " + input);
		process.run(input, listener);
		System.out.println("Done with " + input);
	}

	/**
	 * Just runs {@link AplayProcess}, two "instances" at once.
	 * 
	 * @throws ExternalProgramException
	 * @throws InterruptedException
	 */
	@Test
	public void testTwoAtOnce() throws ExternalProgramException, InterruptedException {
		File input1 = prepareTestingFile();
		File input2 = prepareTestingFile();

		AplayProcess process = new AplayProcess();

		Thread thread1 = runInThread(process, input1);
		Thread thread2 = runInThread(process, input2);

		thread1.join();
		thread2.join();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Runs given process with given file and null listener in new thread. The
	 * thread is then returned to be joined.
	 * 
	 * @param process
	 * @param input
	 * @return
	 * @throws ExternalProgramException
	 */
	private Thread runInThread(AplayProcess process, File input) throws ExternalProgramException {
		final ProgressListener listeners = null;
		Thread thread = new Thread(() -> {
			System.out.println("Starting with " + input);
			try {
				process.run(input, listeners);
			} catch (ExternalProgramException e) {
				assumeNoException(e);
			}
			System.out.println("Done with " + input);
		}, "ProcessInBackgroundThread");

		thread.start();
		return thread;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Prepares testing file. Copies it from resources somewhere to temp.
	 * 
	 * @return
	 */
	private File prepareTestingFile() {
		File input;
		try {
			input = File.createTempFile("sample-", ".opus");
			TestingDownloader.copyTestingFileTo(input);
		} catch (Exception e) {
			assumeNoException(e);
			return null;
		}
		return input;
	}

}

package cz.martlin.jmop.core.misc.ops;

import java.util.concurrent.TimeUnit;

/**
 * Testing long operation. Just counts given number of seconds.
 * 
 * @author martin
 *
 */
public class TestingCountingLongOperation extends AbstractLongOperation<Integer, Integer> {

	private final String name;
	private final int count;
	private boolean interrupted;

	public TestingCountingLongOperation(String name, int count) {
		super("Counting " + name, count, (i) -> i.toString());

		this.name = name;
		this.count = count;
	}

	@Override
	public Integer run(BaseProgressListener listener) throws Exception {

		for (int i = 0; i < count; i++) {
			System.out.println("Iteration " //
					+ i + "/" + count //
					+ " of " + name //
					+ " @ " + (System.currentTimeMillis() % 10000) / 1000.0);

			TimeUnit.SECONDS.sleep(1);

			if (interrupted) {
				return null;
			}

			double progress = ((double) i) / ((double) count);
			reportProgress(listener, progress);
		}

		return count * count;
	}

	@Override
	public void terminate() {
		interrupted = true;
	}

}

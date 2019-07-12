package cz.martlin.jmop.core.misc;

/**
 * The listener of progress. Specifies method for processing the progress. The
 * progress is here meant the percentage of done work.
 * 
 * This class is functional interface, hence it can be written as lambda
 * expression.
 * 
 * @author martin
 *
 */
@FunctionalInterface
public interface ProgressListener {
	public static final double THE_100_PERCENT = 100.0;

	/**
	 * Progress have been changed to given value. The value may not exceed the
	 * {@link #THE_100_PERCENT}.
	 * 
	 * @param percentage
	 */
	public void progressChanged(double percentage);
}

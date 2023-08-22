package cz.martlin.jmop.core.misc.ops;

/**
 * The progress listener. Reports the progress somehow to the user.
 * 
 * @author martin
 *
 */
public interface BaseProgressListener {
	public static final double THE_100_PERCENT = 100.0;

	public void reportProgressChanged(double progress);
}

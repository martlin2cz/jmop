package cz.martlin.jmop.core.misc;

/**
 * Progress generator is class which produces some progress. This progress may
 * be handled by {@link ProgressListener}.
 * 
 * @author martin
 *
 */
public interface ProgressGenerator {

	/**
	 * Specifies progress listener to be setted as listener of progress of this
	 * object.
	 * 
	 * @param listener
	 */
	public void specifyListener(ProgressListener listener);
}

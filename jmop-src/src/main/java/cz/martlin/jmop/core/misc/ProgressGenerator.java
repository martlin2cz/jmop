package cz.martlin.jmop.core.misc;

/**
 * Progress generator is class which produces some progress. This progress may
 * be handled by {@link XXX_ProgressListener}.
 * 
 * @author martin
 * @deprecated Currently, this interface is not beeing used. Thoose who generate
 *             progress does it differently, and the others simply doesn't
 *             generate it. It may be reworked, so don't throw out this class ultimatelly, but for now it is useless.
 *
 */
@Deprecated
public interface ProgressGenerator {

	/**
	 * Specifies progress listener to be setted as listener of progress of this
	 * object.
	 * 
	 * @param listener
	 */
	public void specifyListener(XXX_ProgressListener listener);
}

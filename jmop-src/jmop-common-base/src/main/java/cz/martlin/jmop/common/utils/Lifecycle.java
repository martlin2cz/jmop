package cz.martlin.jmop.common.utils;

/**
 * An utility interface specifiing behaviour on the application start (or
 * reload) and end.
 * 
 * @author martin
 *
 */
public interface Lifecycle {

	/**
	 * Does something when the application gets (re)loaded.
	 */
	void load();

	/**
	 * Does something when the application gets terminated.
	 */
	void terminate();

}
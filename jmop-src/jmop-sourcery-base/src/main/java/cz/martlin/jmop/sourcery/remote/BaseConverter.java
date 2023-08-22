package cz.martlin.jmop.sourcery.remote;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;

/**
 * The converter, converting track from one format to another.
 * @author martin
 *
 */
public interface BaseConverter {

	/**
	 * Converts.
	 * 
	 * @param track
	 * @param from
	 * @param to
	 * @throws JMOPSourceryException
	 */
	void convert(Track track, File from, File to) throws JMOPSourceryException;

}

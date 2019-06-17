package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

/**
 * The most general source. Specifies just one base method - with probably no
 * direct usage.
 * 
 * @author martin
 *
 */
public interface BaseSourceImpl {

	/**
	 * Infers track of given identifier in given bundle.
	 * 
	 * @param bundle
	 * @param identifier
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException;

}
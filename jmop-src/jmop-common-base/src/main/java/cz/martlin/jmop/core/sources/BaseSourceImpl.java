package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

/**
 * The most general source. Specifies just one base method - with probably no
 * direct usage.
 * 
 * @author martin
 *
 */
@Deprecated
public interface BaseSourceImpl {

	/**
	 * Infers track of given identifier in given bundle.
	 * 
	 * @param bundle
	 * @param identifier
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public Track getTrack(Bundle bundle, String identifier) throws JMOPMusicbaseException;

}
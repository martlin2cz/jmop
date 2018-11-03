package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseSourceImpl {

	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException;

}
package cz.martlin.jmop.common.musicbase;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface TracksSource {
	
	public File trackFile(Track track) throws JMOPSourceException;
}

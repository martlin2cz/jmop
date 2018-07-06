package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;

public interface BaseSourceConverter {
	
	public boolean convert(Track track) throws Exception;
	
}

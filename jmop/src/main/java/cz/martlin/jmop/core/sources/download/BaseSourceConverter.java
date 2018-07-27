package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;

public interface BaseSourceConverter extends ProgressGenerator {
	
	public boolean convert(Track track) throws Exception;
	

	
}

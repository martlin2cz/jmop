package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;

public class NoopConverter implements BaseSourceConverter {

	@Override
	public boolean convert(Track track) throws Exception {
		return true;
	}

}

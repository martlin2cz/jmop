package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressGenerator;

public interface BaseSourceDownloader extends ProgressGenerator {

	public boolean download(Track track) throws Exception;

}

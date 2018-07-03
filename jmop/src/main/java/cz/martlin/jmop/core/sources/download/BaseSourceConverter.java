package cz.martlin.jmop.core.sources.download;

import java.io.File;

public interface BaseSourceConverter {
	
	public boolean convert(File file) throws Exception;
	
}

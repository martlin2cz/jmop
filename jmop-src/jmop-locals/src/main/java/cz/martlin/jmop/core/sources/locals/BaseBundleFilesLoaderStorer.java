package cz.martlin.jmop.core.sources.locals;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseBundleFilesLoaderStorer {

	public String extensionOfFile();

	/////////////////////////////////////////////////////////////////

	public Bundle loadBundle(File file) throws JMOPSourceException;

	public void saveBundle(Bundle bundle, File file) throws JMOPSourceException;

}

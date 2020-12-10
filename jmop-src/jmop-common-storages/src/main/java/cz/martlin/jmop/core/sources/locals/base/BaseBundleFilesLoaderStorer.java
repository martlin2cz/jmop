package cz.martlin.jmop.core.sources.locals.base;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.musicbase.commons.BaseExtendedPlaylistSaver;
import cz.martlin.jmop.core.misc.JMOPSourceException;

/**
 * @deprecated replaced by {@link BaseExtendedPlaylistSaver}.
 * @author martin
 *
 */
@Deprecated
public interface BaseBundleFilesLoaderStorer {

	public String extensionOfFile();

	/////////////////////////////////////////////////////////////////

	public Bundle loadBundle(File file) throws JMOPSourceException;

	public void saveBundle(Bundle bundle, File file) throws JMOPSourceException;

}

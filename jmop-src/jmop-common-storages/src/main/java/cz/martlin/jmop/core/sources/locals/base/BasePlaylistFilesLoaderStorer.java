package cz.martlin.jmop.core.sources.locals.base;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.musicbase.commons.BaseExtendedPlaylistSaver;
import cz.martlin.jmop.core.misc.JMOPSourceException;

/**
 * @deprecated replaced by {@link BaseExtendedPlaylistSaver}.
 * @author martin
 *
 */
@Deprecated
public interface BasePlaylistFilesLoaderStorer {

	public String extensionOfFile();

	/////////////////////////////////////////////////////////////////

	public Playlist loadPlaylist(Bundle bundle, File file) throws JMOPSourceException;

	public void savePlaylist(Playlist playlist, File file) throws JMOPSourceException;
}

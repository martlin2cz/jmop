package cz.martlin.jmop.core.sources.locals;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BasePlaylistFilesLoaderStorer {

	public String extensionOfFile();

	/////////////////////////////////////////////////////////////////

	public Playlist loadPlaylist(Bundle bundle, File file) throws JMOPSourceException;

	public void savePlaylist(Bundle bundle, Playlist playlist, File file) throws JMOPSourceException;
}

package cz.martlin.jmop.core.sources.locals;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;

public interface BasePlaylistFilesLoaderStorer {
	public String extensionOfFile();
	public Playlist load(Bundle bundle, File file);
	public void save(Bundle bundle, Playlist playlist, File file);
}

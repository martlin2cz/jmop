package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.PlaylistFileData;


public interface AbstractPlaylistLoader {

	public abstract PlaylistFileData load(Bundle bundle, File file, boolean onlyMetadata) throws IOException;

	public abstract void save(PlaylistFileData playlist, File file) throws IOException;

	public abstract String getFileExtension();
}

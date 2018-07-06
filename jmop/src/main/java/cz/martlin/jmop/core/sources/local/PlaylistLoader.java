package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.data.PlaylistFileData;


public interface PlaylistLoader {

	public abstract PlaylistFileData load(File file) throws IOException;

	public abstract void save(PlaylistFileData playlist, File file) throws IOException;

}

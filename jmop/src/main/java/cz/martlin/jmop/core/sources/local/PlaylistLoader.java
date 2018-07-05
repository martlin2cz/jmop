package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.player.Playlist;


public interface PlaylistLoader {

	public abstract Playlist load(File file) throws IOException;

	public abstract void save(Playlist playlist, File file) throws IOException;

}

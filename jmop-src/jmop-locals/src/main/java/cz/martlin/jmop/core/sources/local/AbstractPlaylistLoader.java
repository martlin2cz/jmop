package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.PlaylistFileData;

/**
 * Base encapsulation for {@link Playlist} (in fact {@link PlaylistFileData})
 * loading operations.
 * 
 * @author martin
 *
 */
@Deprecated
public interface AbstractPlaylistLoader {

	/**
	 * Loads contents of the given playlist file. If onlyMetada is set, only
	 * metadata is loaded (like playlist name, bundle information; not the tracks).
	 * 
	 * @param bundle
	 * @param file
	 * @param onlyMetadata
	 * @return
	 * @throws IOException
	 */
	public abstract PlaylistFileData load(Bundle bundle, File file, boolean onlyMetadata) throws IOException;

	/**
	 * Saves the given playlist data into given playlist file.
	 * 
	 * @param playlist
	 * @param file
	 * @throws IOException
	 */
	public abstract void save(PlaylistFileData playlist, File file) throws IOException;

	/**
	 * Returns file extension used by this kind of playlist files.
	 * 
	 * @return
	 */
	public abstract String getFileExtension();
}

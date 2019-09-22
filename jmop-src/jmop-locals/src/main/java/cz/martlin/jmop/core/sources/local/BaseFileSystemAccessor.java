package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.util.Set;
import java.util.function.Predicate;

public interface BaseFileSystemAccessor {

//	List<String> listPlaylistsFiles(Bundle bundle);
//
//	Playlist loadPlaylist(Bundle bundle, String fileName);
//
//	void savePlaylist(Playlist playlist);
//
//	void deletePlaylist(Playlist playlist);

	///////////////////////////////////////////////////////

	public boolean existsDirectory(File directory);

	public void createDirectory(File directory);

	public void deleteDirectory(File directory);

	public void renameDirectory(File oldDirectory, File newDirectory);

	public Set<File> listDirectoriesMatching(File directory, Predicate<File> matcher);

	///////////////////////////////////////////////////////

	public boolean existsFile(File file);

	public void deleteFile(File file);

	public void moveFile(File oldFile, File newFile);

	public Set<File> listFilesMatching(File directory, Predicate<File> matcher);

	///////////////////////////////////////////////////////

	
//	Set<File> listBundleDirectories();

}

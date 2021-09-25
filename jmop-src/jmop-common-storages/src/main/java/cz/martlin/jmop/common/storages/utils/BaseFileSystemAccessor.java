package cz.martlin.jmop.common.storages.utils;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseFileSystemAccessor {

	///////////////////////////////////////////////////////

	public boolean existsDirectory(File directory) throws JMOPMusicbaseException;

	public void createDirectory(File directory) throws JMOPMusicbaseException;

	public void deleteDirectory(File directory) throws JMOPMusicbaseException;

	public void renameDirectory(File oldDirectory, File newDirectory) throws JMOPMusicbaseException;

	/**
	 * @deprecated use {@link #listDirectories(File)} instead.
	 * @param directory
	 * @param matcher
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	@Deprecated
	public Set<File> listDirectoriesMatching(File directory, Predicate<File> matcher) throws JMOPMusicbaseException;

	public Stream<File> listDirectories(File directory) throws JMOPMusicbaseException;

	///////////////////////////////////////////////////////

	public boolean existsFile(File file) throws JMOPMusicbaseException;

	public void createEmptyFile(File file) throws JMOPMusicbaseException;
	
	public void deleteFile(File file) throws JMOPMusicbaseException;

	public void moveFile(File oldFile, File newFile) throws JMOPMusicbaseException;

	/**
	 * @deprecated use {@link #listFiles(File)} instead
	 * @param directory
	 * @param matcher
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	@Deprecated
	public Set<File> listFilesMatching(File directory, Predicate<File> matcher) throws JMOPMusicbaseException;

	public Stream<File> listFiles(File directory) throws JMOPMusicbaseException;
	
	public List<String> loadLines(File file) throws JMOPMusicbaseException;

	public void saveLines(File file, List<String> lines) throws JMOPMusicbaseException;

	///////////////////////////////////////////////////////

}

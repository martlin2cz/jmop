package cz.martlin.jmop.common.storages.utils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

public interface BaseFileSystemAccessor {

	///////////////////////////////////////////////////////

	public boolean existsDirectory(File directory) throws JMOPPersistenceException;

	public void createDirectory(File directory)  throws JMOPPersistenceException;

	public void deleteDirectory(File directory)  throws JMOPPersistenceException;

	public void renameDirectory(File oldDirectory, File newDirectory)  throws JMOPPersistenceException;

	/**
	 * @deprecated use {@link #listDirectories(File)} instead.
	 * @param directory
	 * @param matcher
	 * @return
	 * @
	 */
	@Deprecated
	public Set<File> listDirectoriesMatching(File directory, Predicate<File> matcher)  throws JMOPPersistenceException;

	public Stream<File> listDirectories(File directory)  throws JMOPPersistenceException;

	///////////////////////////////////////////////////////

	public boolean existsFile(File file)  throws JMOPPersistenceException;

	public void createEmptyFile(File file)  throws JMOPPersistenceException;
	
	public void deleteFile(File file)  throws JMOPPersistenceException;

	public void moveFile(File oldFile, File newFile)  throws JMOPPersistenceException;

	/**
	 * @deprecated use {@link #listFiles(File)} instead
	 * @param directory
	 * @param matcher
	 * @return
	 * @
	 */
	@Deprecated
	public Set<File> listFilesMatching(File directory, Predicate<File> matcher)  throws JMOPPersistenceException;

	public Stream<File> listFiles(File directory)  throws JMOPPersistenceException;
	
	public List<String> loadLines(File file)  throws JMOPPersistenceException;

	public void saveLines(File file, List<String> lines)  throws JMOPPersistenceException;

	public void writeFile(File file, InputStream contents)  throws JMOPPersistenceException;

	///////////////////////////////////////////////////////

}

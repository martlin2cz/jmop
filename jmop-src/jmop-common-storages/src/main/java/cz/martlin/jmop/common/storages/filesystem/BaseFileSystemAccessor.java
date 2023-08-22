package cz.martlin.jmop.common.storages.filesystem;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An tool encapsulating various file system operations, i.e. manipulation with
 * the files and dirs.
 * 
 * @author martin
 *
 */
public interface BaseFileSystemAccessor {

	///////////////////////////////////////////////////////

	/**
	 * Returns true if given directory exists.
	 * 
	 * @param directory
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public boolean existsDirectory(File directory) throws JMOPPersistenceException;

	/**
	 * Creates given directory.
	 * 
	 * @param directory
	 * @throws JMOPPersistenceException
	 */
	public void createDirectory(File directory) throws JMOPPersistenceException;

	/**
	 * Deletes given directory.
	 * 
	 * @param directory
	 * @throws JMOPPersistenceException
	 */
	public void deleteDirectory(File directory) throws JMOPPersistenceException;

	/**
	 * Renames given directory.
	 * 
	 * @param oldDirectory
	 * @param newDirectory
	 * @throws JMOPPersistenceException
	 */
	public void renameDirectory(File oldDirectory, File newDirectory) throws JMOPPersistenceException;

	/**
	 * Lists all the child dirs in the given directory.
	 * 
	 * @param directory
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public Stream<File> listDirectories(File directory) throws JMOPPersistenceException;

	/**
	 * Lists all the child files in the given directory.
	 * 
	 * @param directory
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public Stream<File> listFiles(File directory) throws JMOPPersistenceException;

	///////////////////////////////////////////////////////

	/**
	 * Returns true if the given file exists.
	 * 
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public boolean existsFile(File file) throws JMOPPersistenceException;

	/**
	 * Creates given empty file.
	 * 
	 * @param file
	 * @see {@link #writeFile(File, InputStream)}, {@link #saveLines(File, List)}
	 * @throws JMOPPersistenceException
	 */
	public void createEmptyFile(File file) throws JMOPPersistenceException;

	/**
	 * Deletes the given file.
	 * 
	 * @param file
	 * @throws JMOPPersistenceException
	 */
	public void deleteFile(File file) throws JMOPPersistenceException;

	/**
	 * Moves (or renames) the given file from one location to another.
	 * 
	 * @param oldFile
	 * @param newFile
	 * @throws JMOPPersistenceException
	 */
	public void moveFile(File oldFile, File newFile) throws JMOPPersistenceException;


	/**
	 * Copies the given old file to new one.
	 * 
	 * @param oldFile
	 * @param newFile
	 * @throws JMOPPersistenceException
	 */
	public void copyFile(File oldFile, File newFile) throws JMOPPersistenceException;

	/**
	 * Creates link pointing to the given target file.
	 * 
	 * @param linkFile
	 * @param targetFile
	 * @throws JMOPPersistenceException
	 */
	public void linkFile(File linkFile, File targetFile) throws JMOPPersistenceException;
	
	/**
	 * Loads lines of the given file.
	 * 
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public List<String> loadLines(File file) throws JMOPPersistenceException;

	/**
	 * Saves the given lines into the given file.
	 * 
	 * @param file
	 * @param lines
	 * @throws JMOPPersistenceException
	 */
	public void saveLines(File file, List<String> lines) throws JMOPPersistenceException;

	/**
	 * Writes the given contents to the given file.
	 * 
	 * @param file
	 * @param contents
	 * @throws JMOPPersistenceException
	 */
	public void writeFile(File file, InputStream contents) throws JMOPPersistenceException;

	///////////////////////////////////////////////////////

}

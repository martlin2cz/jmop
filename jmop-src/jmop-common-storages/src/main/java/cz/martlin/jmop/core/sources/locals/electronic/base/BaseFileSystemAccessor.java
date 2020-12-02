package cz.martlin.jmop.core.sources.locals.electronic.base;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import cz.martlin.jmop.common.data.Tracklist;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseFileSystemAccessor {

	///////////////////////////////////////////////////////

	public boolean existsDirectory(File directory) throws JMOPSourceException;

	public void createDirectory(File directory) throws JMOPSourceException;

	public void deleteDirectory(File directory) throws JMOPSourceException;

	public void renameDirectory(File oldDirectory, File newDirectory) throws JMOPSourceException;

	public Set<File> listDirectoriesMatching(File directory, Predicate<File> matcher) throws JMOPSourceException;

	///////////////////////////////////////////////////////

	public boolean existsFile(File file) throws JMOPSourceException;

	public void createEmptyFile(File file) throws JMOPSourceException;
	
	public void deleteFile(File file) throws JMOPSourceException;

	public void moveFile(File oldFile, File newFile) throws JMOPSourceException;

	public Set<File> listFilesMatching(File directory, Predicate<File> matcher) throws JMOPSourceException;

	public List<String> loadLines(File file) throws JMOPSourceException;

	public void saveLines(File file, List<String> lines) throws JMOPSourceException;

	///////////////////////////////////////////////////////

}

package cz.martlin.jmop.core.sources.locals.electronic.impls;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;

public class ElectronicFileSystemAccessor implements BaseFileSystemAccessor {

	public ElectronicFileSystemAccessor() {
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public boolean existsDirectory(File directory) {
		Path path = directory.toPath();
		return Files.isDirectory(path);
	}

	@Override
	public void createDirectory(File directory) throws JMOPSourceException {
		try {
			Path path = directory.toPath();
			Files.createDirectory(path);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot create directory", e);
		}
	}

	@Override
	public void deleteDirectory(File directory) throws JMOPSourceException {
		try {
			Path path = directory.toPath();

			deleteRecursivelly(path);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot delete directory", e);
		}
	}

	@Override
	public void renameDirectory(File oldDirectory, File newDirectory) throws JMOPSourceException {
		try {
			Path oldPath = oldDirectory.toPath();
			Path newPath = newDirectory.toPath();
			Files.move(oldPath, newPath);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot rename directory", e);
		}
	}

	@Override
	public Set<File> listDirectoriesMatching(File directory, Predicate<File> matcher) throws JMOPSourceException {
		try {
			Path path = directory.toPath();
			return Files.list(path) //
					.filter((p) -> (Files.isDirectory(p))) //
					.map((p) -> p.toFile()) //
					.filter((f) -> matcher.test(f)) //
					.collect(Collectors.toSet()); //
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot list directories", e);
		}
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public boolean existsFile(File file) {
		Path path = file.toPath();
		return Files.isRegularFile(path);
	}

	@Override
	public void deleteFile(File file) throws JMOPSourceException {
		try {
			Path path = file.toPath();
			Files.delete(path);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot delete file", e);
		}
	}

	@Override
	public void moveFile(File oldFile, File newFile) throws JMOPSourceException {
		try {
			Path oldPath = oldFile.toPath();
			Path newPath = newFile.toPath();
			Files.move(oldPath, newPath);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot move file", e);
		}
	}

	@Override
	public Set<File> listFilesMatching(File directory, Predicate<File> matcher) throws JMOPSourceException {
		try {
			Path path = directory.toPath();
			return Files.list(path) //
					.filter((p) -> (Files.isRegularFile(p))) //
					.map((p) -> p.toFile()) //
					.filter((f) -> matcher.test(f)) //
					.collect(Collectors.toSet()); //
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot list files", e);
		}
	}
	/////////////////////////////////////////////////////////////////

	private void deleteRecursivelly(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			List<Path> children = Files.list(path).collect(Collectors.toList());

			for (Path child : children) {
				deleteRecursivelly(child);
			}
		}

		Files.delete(path);
	}

}

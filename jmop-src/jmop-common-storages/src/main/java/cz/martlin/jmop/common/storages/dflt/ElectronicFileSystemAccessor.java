package cz.martlin.jmop.common.storages.dflt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

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
	public void createDirectory(File directory) throws JMOPPersistenceException {
		try {
			Path path = directory.toPath();
			Files.createDirectory(path);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot create directory", e);
		}
	}

	@Override
	public void deleteDirectory(File directory) throws JMOPPersistenceException {
		try {
			Path path = directory.toPath();

			deleteRecursivelly(path);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot delete directory", e);
		}
	}

	@Override
	public void renameDirectory(File oldDirectory, File newDirectory) throws JMOPPersistenceException {
		try {
			Path oldPath = oldDirectory.toPath();
			Path newPath = newDirectory.toPath();
			Files.move(oldPath, newPath);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot rename directory", e);
		}
	}

	@Override
	public Set<File> listDirectoriesMatching(File directory, Predicate<File> matcher) throws JMOPPersistenceException {
		try {
			Path path = directory.toPath();
			return Files.list(path) //
					.filter((p) -> (Files.isDirectory(p))) //
					.map((p) -> p.toFile()) //
					.filter((f) -> matcher.test(f)) //
					.collect(Collectors.toSet()); //
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot list directories", e);
		}
	}
	
	@Override
	public Stream<File> listDirectories(File directory) throws JMOPPersistenceException {
		try {
			Path path = directory.toPath();
			return Files.list(path)//
					.filter(p -> Files.isDirectory(p)) //
					.map(p -> p.toFile()); //
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot list directories", e);
		}
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public boolean existsFile(File file) throws JMOPPersistenceException {
		Path path = file.toPath();
		return Files.isRegularFile(path);
	}

	@Override
	public void createEmptyFile(File file) throws JMOPPersistenceException {
		try {
			Path path = file.toPath();
			Files.createFile(path);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot create file", e);
		}
	}
	
	@Override
	public void deleteFile(File file) throws JMOPPersistenceException {
		try {
			Path path = file.toPath();
			Files.delete(path);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot delete file", e);
		}
	}

	@Override
	public void moveFile(File oldFile, File newFile) throws JMOPPersistenceException {
		try {
			Path oldPath = oldFile.toPath();
			Path newPath = newFile.toPath();
			Files.move(oldPath, newPath);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot move file", e);
		}
	}

	@Override
	public Set<File> listFilesMatching(File directory, Predicate<File> matcher) throws JMOPPersistenceException {
		try {
			Path path = directory.toPath();
			return Files.list(path) //
					.filter((p) -> (Files.isRegularFile(p))) //
					.map((p) -> p.toFile()) //
					.filter((f) -> matcher.test(f)) //
					.collect(Collectors.toSet()); //
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot list files", e);
		}
	}
	
	@Override
	public Stream<File> listFiles(File directory) throws JMOPPersistenceException {
		try {
			Path path = directory.toPath();
			return Files.list(path) //
					.filter(p -> Files.isRegularFile(p)) // 
					.map(p -> p.toFile()); //
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot list files", e);
		}
	}

	
	@Override
	public List<String> loadLines(File file) throws JMOPPersistenceException {
		try {
			Path path = file.toPath();
			return Files.readAllLines(path);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot load file", e);
		}
	}
	
	@Override
	public void saveLines(File file, List<String> lines) throws JMOPPersistenceException {
		try {
			Path path = file.toPath();
			Files.write(path, lines);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot write file", e);
		}
	}
	
	@Override
	public void writeFile(File file, InputStream contents) throws JMOPPersistenceException {
		try {
			Path path = file.toPath();
			Files.copy(contents, path);
		} catch (IOException e) {
			throw new JMOPPersistenceException("Cannot write file", e);
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

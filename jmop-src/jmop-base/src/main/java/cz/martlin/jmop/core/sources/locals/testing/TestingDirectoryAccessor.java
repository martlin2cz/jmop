package cz.martlin.jmop.core.sources.locals.testing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.misc.ops.RunnableWithException;

public class TestingDirectoryAccessor {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final String name;
	private Path dir;

	public TestingDirectoryAccessor(String name) {
		this.name = name;
	}

	public File getDir() {
		if (dir == null) {
			throw new IllegalStateException("The directory was not created or is already deleted");
		}

		return dir.toFile();
	}

	public boolean exists() {
		return dir != null;
	}

	///////////////////////////////////////////////////////////////////////////

	public File create() throws IOException {
		LOG.info("Creating testing directory " + name);

		try {
			dir = createDirectory();
		} catch (IOException e) {
			LOG.error("Cannot create testing directory " + name + "!");
			throw e;
		}

		LOG.info("Created testing directory " + dir.toAbsolutePath().toString() + "!");
		return dir.toFile();
	}

	public File delete() throws IOException {
		LOG.info("Deleting testing directory " + dir.toAbsolutePath().toString() + " ... ");

		try {
			deleteRecursivelly(dir.toFile());
		} catch (IOException e) {
			LOG.error("Cannot create testing directory " + name + "!");
			throw e;
		}

		LOG.info("Deleted testing directory " + name + "!");
		return null;
	}
///////////////////////////////////////////////////////////////////////////

	public void checkAndCreate() throws IOException {
		if (!exists()) {
			create();
		}
	}

	public void checkAndDelete() throws IOException {
		if (exists()) {
			delete();
		}
	}
///////////////////////////////////////////////////////////////////////////

	public static void runWith(String name, RunnableWithException run) throws Exception {
		TestingDirectoryAccessor acc = new TestingDirectoryAccessor(name);
		try {
			acc.create();
			run.run();
		} finally {
			acc.delete();
		}
	}

	///////////////////////////////////////////////////////////////////////////
	private Path createDirectory() throws IOException {
		String prefix = "jmop-" + name + "-";
		return Files.createTempDirectory(prefix);
	}

	private void deleteRecursivelly(File resource) throws IOException {
		if (resource.isDirectory()) {
			for (File child : resource.listFiles()) {
				deleteRecursivelly(child);
			}
		}
		Files.delete(resource.toPath());
	}

}

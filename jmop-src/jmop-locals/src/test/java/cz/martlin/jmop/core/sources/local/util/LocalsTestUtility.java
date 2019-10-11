package cz.martlin.jmop.core.sources.local.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LocalsTestUtility {
	private static final String TEST_DIR_NAME = "jmop-test";

	public LocalsTestUtility() {
	}

	/////////////////////////////////////////////////////////////////

	public File createTempDirectory() throws IOException {
		File dir = createTestingTempDirObject();

		Files.createDirectory(dir.toPath());

		return dir;
	}

	public File deleteTempDirectory() throws IOException {
		File dir = createTestingTempDirObject();

		Files.delete(dir.toPath());

		return null; // hoho
	}

	private File createTestingTempDirObject() {
		String tempDirPath = System.getProperty("java.io.tmpdir");
		File temp = new File(tempDirPath);
		File dir = new File(temp, TEST_DIR_NAME);
		return dir;
	}

}

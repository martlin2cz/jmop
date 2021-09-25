package cz.martlin.jmop.common.testing.resources;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingRootDir {
	private static final Logger LOG = LoggerFactory.getLogger(TestingRootDir.class);

	private static final String JMOP_TEST_DIR_NAME = "jmop";

	private final Object testInstanceOrClass;
	private File file;
	
	public TestingRootDir(Object testInstanceOrClass) {
		super();
		this.testInstanceOrClass = testInstanceOrClass;
		
		this.file = prepareFile();
	}

	public File getFile() {
		return file;
	}
	
	private File prepareFile() {
		File jmopDir = getJMOPTempDir();

		String className = testInstanceOrClass instanceof Class<?> //
				? ((Class<?>) testInstanceOrClass).getSimpleName() //
				: (testInstanceOrClass).getClass().getSimpleName();

		String datetime = LocalDateTime.now().toString().replace(':', '.');		
		long timestamp = System.currentTimeMillis();

		String testDirName = className + "-" + datetime + "-" + timestamp;
		File testDir = new File(jmopDir, testDirName);

		assumeTrue(testDir.mkdirs(), "Could not mkdirs " + testDir);
		
		LOG.info("Ready to run test in: " + testDir.getAbsolutePath());
		return testDir;
	}

	private static File getJMOPTempDir() {
		String tempPath = System.getProperty("java.io.tmpdir");

		File tempDir = new File(tempPath);
		File jmopDir = new File(tempDir, JMOP_TEST_DIR_NAME);

		if (!jmopDir.isDirectory()) {
			assumeTrue(jmopDir.mkdirs(), "Could not mkdirs " + jmopDir);
		}
		return jmopDir;
	}
}

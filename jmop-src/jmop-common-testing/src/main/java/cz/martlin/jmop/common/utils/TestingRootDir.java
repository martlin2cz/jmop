package cz.martlin.jmop.common.utils;

import java.io.File;

public class TestingRootDir {
	
	private static final String JMOP_TEST_DIR_NAME = "jmop";

	public static File getFile() {
		String tempPath = System.getProperty("java.io.tmpdir");
				
		File tempDir = new File(tempPath);
		File jmopDir = new File(tempDir, JMOP_TEST_DIR_NAME);
		
		return jmopDir;
	}
}

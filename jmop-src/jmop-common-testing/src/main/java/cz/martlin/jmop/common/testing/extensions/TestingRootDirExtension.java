package cz.martlin.jmop.common.testing.extensions;

import java.io.File;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import cz.martlin.jmop.common.testing.resources.TestingRootDir;

public class TestingRootDirExtension implements Extension, BeforeEachCallback {
	
//	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Object test;

	private File root;
	
	public TestingRootDirExtension(Object test) {
		super();
		
		this.test = test;
	}
	
	public File getFile() {
		return root;
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
//		LOG.debug("Preparing the testing dir ...");
//
		TestingRootDir rootDir = new TestingRootDir(test);
		root = rootDir.getFile();
//
//		if (root.exists()) {
//			try {
//				LOG.trace("The testing dir exists, deleting");
//				FileUtils.deleteDirectory(root);
//			} catch (IOException e) {
//				LOG.error("Could not delete the testing dir", e);
//				assumeTrue(e == null, "Could not delete the testing dir");
//			}
//		}
//
//		try {
//			LOG.trace("Creating the testing dir");
//			FileUtils.forceMkdir(root);
//		} catch (IOException e) {
//			LOG.error("Could not (re)create the testing dir", e);
//			assumeTrue(e == null, "Could not re(create) the testing dir");
//		}
//
//		LOG.debug("Prepared the testing dir!");
	}
	
	public File getRoot() {
		return root;
	}

}

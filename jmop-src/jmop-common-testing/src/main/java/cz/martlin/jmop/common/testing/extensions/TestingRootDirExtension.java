package cz.martlin.jmop.common.testing.extensions;

import java.io.File;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import cz.martlin.jmop.common.testing.resources.TestingRootDir;

/**
 * Junit extension automatically creating the root directory.
 * 
 * @author martin
 *
 */
public class TestingRootDirExtension implements Extension, BeforeEachCallback {
	
//	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Object test;

	private File root;
	
	public TestingRootDirExtension(Object test) {
		super();
		
		this.test = test;
	}
	
	/**
	 * Returns the testing root directory.
	 * 
	 * @return
	 */
	public File getFile() {
		return root;
	}
	
	/**
	 * Returns the testing root directory as well.
	 * 
	 * @return
	 */
	public File getRoot() {
		return root;
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {

		TestingRootDir rootDir = new TestingRootDir(test);
		root = rootDir.getFile();

	}
	

}

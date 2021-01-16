package cz.martlin.jmop.common.utils;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class TestingMusicbaseExtension implements Extension, BeforeEachCallback {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public TestingMusicbase tm;
	
	private final BaseMusicbaseModifing musicbase;
	private final boolean fileExisting;

	public TestingMusicbaseExtension(BaseMusicbaseModifing musicbase, boolean fileExisting) {
		super();
		this.musicbase = musicbase;
		this.fileExisting = fileExisting;
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		LOG.info("Preparing the test environment ...");
		
		ensureTestingDir();

		this.tm = prepareTestingMusicbase();
		
		LOG.info("Prepared the test environment!");
		
	}


	private void ensureTestingDir() {
		LOG.debug("Preparing the testing dir ...");

		File root = TestingRootDir.getFile();
		
		if (root.exists()) {
			try {
				LOG.trace("The testing dir exists, deleting");
				FileUtils.deleteDirectory(root);
			} catch (IOException e) {
				LOG.error("Could not delete the testing dir", e);
				assumeTrue(e == null, "Could not delete the testing dir");
			}
		}
		
		try {
			LOG.trace("Creating the testing dir");
			FileUtils.forceMkdir(root);
		} catch (IOException e) {
			LOG.error("Could not (re)create the testing dir", e);
			assumeTrue(e == null, "Could not re(create) the testing dir");
		}
		
		LOG.debug("Prepared the testing dir!");
	}


	private TestingMusicbase prepareTestingMusicbase() {
		
		try {
			LOG.debug("Preparing the testing musicbase ...");
			return new TestingMusicbase(musicbase, fileExisting);

		} catch (JMOPMusicbaseException e) {
			e.printStackTrace();
			assumeTrue(e == null, "Could not create testing musicbase");
			return null;
			
		} finally {
			LOG.debug("Prepared the testing musicbase!");
		}
	}
	
}

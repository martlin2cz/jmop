package cz.martlin.jmop.core.sources.locals.electronic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.TestingDataCreator;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.locals.testing.TestingDirectoryAccessor;

public class ElectronicLocalSourceTest {

	private final TestingDirectoryAccessor tda = new TestingDirectoryAccessor("jmop-electronic-local-source-test");

	@Before
	public void before() throws IOException {
		tda.create();
		System.out.println("Working with " + tda.getDir().getAbsolutePath());
	}

	@After
	public void after() throws IOException {
		tda.delete();
	}
	
	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testBundles() throws JMOPSourceException {
		ElectronicLocalSource local = createSource();
		BaseBundlesLocalSource bundles = local.bundles();
		
		Bundle bundle = TestingDataCreator.createTestingBundle();
		bundles.createBundle(bundle);
		
		List<Bundle> loaded = bundles.listBundles();
		assertThat(loaded, contains(bundle));
		
		bundles.deleteBundle(bundle);
		assertThat(loaded, empty());
		
		//TODO test move
	}
	
	///////////////////////////////////////////////////////////////////////////
	private ElectronicLocalSource createSource() {
		BaseConfiguration config = new ConstantConfiguration();
		File root = tda.getDir();
		ElectronicLocalSource local = new ElectronicLocalSource(config, root);
		return local;
	}

}

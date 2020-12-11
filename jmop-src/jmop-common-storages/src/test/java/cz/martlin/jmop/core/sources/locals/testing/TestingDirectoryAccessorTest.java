package cz.martlin.jmop.core.sources.locals.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cz.martlin.jmop.common.storages.simples.TestingDirectoryAccessor;

public class TestingDirectoryAccessorTest {

	@Test
	public void test() {
		TestingDirectoryAccessor acc = new TestingDirectoryAccessor("foo");

		File created = null;
		File deleted = null;
		try {
			try {
				created = acc.create();
			} catch (IOException e) {
				assertNull(e);
			}
			assertNotNull(created);
			assertTrue(created.isDirectory());

			System.out.println("Directory " + created.getAbsolutePath() + " ready to use");
		} finally {
			try {
				deleted = acc.delete();
			} catch (IOException e) {
				assertNull(e);
			}
			assertNull(deleted);
			assertFalse(created.isDirectory());
		}
	}

}

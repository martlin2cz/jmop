package cz.martlin.jmop.common.storages.fs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
//import org.junit.Assume;
//import org.junit.Test;
import org.junit.jupiter.api.io.TempDir;

import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * Just the abstract test for the {@link BaseFileSystemAccessor}.
 * @author martin
 *
 */
public abstract class AbstractFileSystemAccessorTest {

	private final BaseFileSystemAccessor accessor;
	@RegisterExtension
	public TestingRootDirExtension root = new TestingRootDirExtension(this);

	public AbstractFileSystemAccessorTest(BaseFileSystemAccessor accessor) {
		super();
		this.accessor = accessor;
	}


	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testFilesSimply() throws JMOPPersistenceException  {
		File foo = new File(root.getRoot(), "foo.txt");
		File bar = new File(root.getRoot(), "bar.txt");

		assertFalse(accessor.existsFile(foo));
		assertFalse(accessor.existsFile(bar));

		assumeTrue(createFile(foo));
		assertTrue(accessor.existsFile(foo));
		assertFalse(accessor.existsFile(bar));

		accessor.moveFile(foo, bar);
		assertFalse(accessor.existsFile(foo));
		assertTrue(accessor.existsFile(bar));

		accessor.deleteFile(bar);
		assertFalse(accessor.existsFile(foo));
		assertFalse(accessor.existsFile(bar));
	}

	@Test
	public void testDirectoriesSimply() throws JMOPPersistenceException  {
		File lorem = new File(root.getRoot(), "lorem");
		File ipsum = new File(root.getRoot(), "ipsum");

		assertFalse(accessor.existsDirectory(lorem));
		assertFalse(accessor.existsDirectory(ipsum));

		accessor.createDirectory(lorem);
		assertTrue(accessor.existsDirectory(lorem));
		assertFalse(accessor.existsDirectory(ipsum));

		accessor.renameDirectory(lorem, ipsum);
		assertFalse(accessor.existsDirectory(lorem));
		assertTrue(accessor.existsDirectory(ipsum));

		accessor.deleteDirectory(ipsum);
		assertFalse(accessor.existsDirectory(lorem));
		assertFalse(accessor.existsDirectory(ipsum));
	}

	@Test
	public void testListing() throws JMOPPersistenceException  {
		File lorem = new File(root.getRoot(), "lorem");
		File ipsum = new File(root.getRoot(), "ipsum");

		File foo = new File(lorem, "foo.txt");
		File bar = new File(lorem, "bar.txt");
		File baz = new File(ipsum, "baz.txt");
		File qux = new File(ipsum, "qux.png");

		File dir42 = new File(ipsum, "42");
		File dir99 = new File(ipsum, "99");
		File dirNumber = new File(ipsum, "number");

		try {
			// prepare
			create(lorem, ipsum, foo, bar, baz, qux, dir42, dir99, dirNumber);

			// check
			Set<File> txtsOfLorem = accessor.listFiles(lorem) //
					.filter((f) -> f.getName().endsWith("txt")) //
					.collect(Collectors.toSet());
			assertThat(txtsOfLorem, containsInAnyOrder(foo, bar));

			Set<File> pngsOfIpsum = accessor.listFiles(ipsum) //
					.filter((f) -> f.getName().endsWith("png"))//
					.collect(Collectors.toSet());
			assertThat(pngsOfIpsum, containsInAnyOrder(qux));

			Set<File> numbersInIpsum = accessor.listDirectories(ipsum) //
					.filter((f) -> f.getName().matches("[0-9]+"))//
					.collect(Collectors.toSet());
			assertThat(numbersInIpsum, containsInAnyOrder(dir42, dir99));

		} finally {
			// cleanup
			delete(lorem, ipsum, foo, bar, baz, qux, dir42, dir99, dirNumber);
		}
	}

	private void create(File lorem, File ipsum, File foo, File bar, File baz, File qux, File dir42, File dir99,
			File dirNumber) throws JMOPPersistenceException {

			accessor.createDirectory(lorem);
			accessor.createDirectory(ipsum);

			assumeTrue(createFile(foo));
			assumeTrue(createFile(bar));
			assumeTrue(createFile(baz));
			assumeTrue(createFile(qux));

			accessor.createDirectory(dir42);
			accessor.createDirectory(dir99);
			accessor.createDirectory(dirNumber);
	}

	private void delete(File lorem, File ipsum, File foo, File bar, File baz, File qux, File dir42, File dir99,
			File dirNumber) throws JMOPPersistenceException  {

			accessor.deleteDirectory(dirNumber);
			accessor.deleteDirectory(dir99);
			accessor.deleteDirectory(dir42);

			accessor.deleteFile(qux);
			accessor.deleteFile(baz);
			accessor.deleteFile(bar);
			accessor.deleteFile(foo);

			accessor.deleteDirectory(ipsum);
			accessor.deleteDirectory(lorem);
	}

///////////////////////////////////////////////////////////////////////////

	private boolean createFile(File foo) {
		try {
			return foo.createNewFile();
		} catch (IOException e) {
			System.err.println(e);
			return false;
		}
	}

}

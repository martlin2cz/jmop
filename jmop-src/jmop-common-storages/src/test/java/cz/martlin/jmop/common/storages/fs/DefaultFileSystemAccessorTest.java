package cz.martlin.jmop.common.storages.fs;

import cz.martlin.jmop.common.storages.filesystem.DefaultFileSystemAccessor;

/**
 * An test of the {@link DefaultFileSystemAccessor}.
 * 
 * @author martin
 *
 */
public class DefaultFileSystemAccessorTest extends AbstractFileSystemAccessorTest {

	public DefaultFileSystemAccessorTest() {
		super(new DefaultFileSystemAccessor());
	}

}

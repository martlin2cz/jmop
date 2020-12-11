package cz.martlin.jmop.common.storages.dflt;

import cz.martlin.jmop.common.storages.dflt.ElectronicFileSystemAccessor;
import cz.martlin.jmop.common.storages.testing.BaseFileSystemAccessorTest;

public class ElectronicFileSystemAccessorTest extends BaseFileSystemAccessorTest {

	public ElectronicFileSystemAccessorTest() {
		super(new ElectronicFileSystemAccessor());
	}

}

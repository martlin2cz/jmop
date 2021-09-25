package cz.martlin.jmop.common.storages.fileobjects;

import java.io.File;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * Just an empty implementation of the {@link BaseFileObjectIO}. The file
 * objects is here represented by the {@link File} class.
 * 
 * An load procedure just returns the provided file, save does nothing.
 * 
 * @author martin
 *
 */
public class NoopFileObjectIO implements BaseFileObjectIO<File> {

	@Override
	public File tryLoadOrCreate(File file) throws JMOPPersistenceException {
		return file;
	}

	@Override
	public File load(File file) throws JMOPPersistenceException {
		return file;
	}

	@Override
	public void save(File xfile, File file) throws JMOPPersistenceException {
		// nothing to do here.
	}

}

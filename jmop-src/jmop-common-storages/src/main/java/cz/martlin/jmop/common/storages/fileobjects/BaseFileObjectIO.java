package cz.martlin.jmop.common.storages.fileobjects;

import java.io.File;

import cz.martlin.jmop.common.storages.musicdatafile.CommonMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The I/O component of the {@link CommonMusicdataFileManipulator}. Provides the
 * create/load and save of the file objects.
 * 
 * Theese are to be then used by the {@link BaseFileObjectManipulator}.
 * 
 * @author martin
 *
 * @param <FT>
 */
public interface BaseFileObjectIO<FT> {

//	FT create() throws JMOPPersistenceException;

	/**
	 * Tries to load the given file. If cannot load (the file does not exist yet, is
	 * is somehow empty), it may create new file object.
	 * 
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException
	 */
	FT tryLoadOrCreate(File file) throws JMOPPersistenceException;

	/**
	 * Loads the given file. If anything gets wrong, may fail immediatelly, no
	 * questions asked.
	 * 
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException
	 */
	FT load(File file) throws JMOPPersistenceException;

	/**
	 * Saves the given file objects to the specified file.
	 * 
	 * @param xfile
	 * @param file
	 * @throws JMOPPersistenceException
	 */
	void save(FT xfile, File file) throws JMOPPersistenceException;

}

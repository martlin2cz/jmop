package cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject;

import java.io.File;

import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.CommonMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XSPFRuntimeException;

/**
 * The playlist IO loading/creating/saving the instance of XSFPFile.
 * 
 * Component of {@link CommonMusicdataFileManipulator}.
 * 
 * @author martin
 *
 */
public class XSPFPlaylistIO implements BaseFileObjectIO<XSPFFile> {

	private final BaseErrorReporter reporter;

	public XSPFPlaylistIO(BaseErrorReporter reporter) {
		super();
		this.reporter = reporter;
	}

	/**
	 * Just for the testing purposes, I assume.
	 * 
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public static XSPFFile createNew() throws JMOPPersistenceException {
		try {
			return XSPFFile.create();
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Could not create", e);
		}
	}

	@Override
	public XSPFFile tryLoadOrCreate(File file) throws JMOPPersistenceException {
		try {
			return getOrCreate(file);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Could not create/load XSPF document/file: " + file, e);
		}
	}

	private XSPFFile getOrCreate(File file) throws XSPFException {
		if (file != null && file.exists()) {
			try {
				return XSPFFile.load(file);
			} catch (XSPFException | XSPFRuntimeException e) {
				reporter.report("Could not load XSPF file " + file, e);
			}
		}

		return XSPFFile.create();
	}

	@Override
	public XSPFFile load(File file) throws JMOPPersistenceException {
		try {
			return XSPFFile.load(file);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Cannot load XSPF file: " + file, e);
		}
	}

	@Override
	public void save(XSPFFile xfile, File file) throws JMOPPersistenceException {
		try {
			xfile.save(file);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException("Could not save playlist file " + file, e);
		}
	}

}

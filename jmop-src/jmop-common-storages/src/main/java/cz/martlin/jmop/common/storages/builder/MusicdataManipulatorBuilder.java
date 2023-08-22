package cz.martlin.jmop.common.storages.builder;

import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.SimpleMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.XSPFPlaylistFilesMusicdataManipulator;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * Musicdata manipulator builder.
 * 
 * @author martin
 *
 */
public class MusicdataManipulatorBuilder implements Builder<BaseMusicdataFileManipulator> {

	public MusicdataManipulatorBuilder() {
	}

	/**
	 * Builds the musicdata file manipulator.
	 * 
	 * @param format
	 * @param failsave
	 * @param reporter
	 * @param fs
	 * @return
	 */
	public BaseMusicdataFileManipulator create(PlaylistFileFormat format, boolean failsave, BaseErrorReporter reporter,
			BaseFileSystemAccessor fs) {
		switch (format) {
		case PROPERTIES:
			throw new UnsupportedOperationException("Not yet supported");
		case TXT:
			return createSimpleManipulator(fs);
		case XSPF:
			return createXSPFmanipulator(failsave, reporter);
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Builds the simple musicdata file manipulator.
	 * 
	 * @param fs
	 * @return
	 */
	public BaseMusicdataFileManipulator createSimpleManipulator(BaseFileSystemAccessor fs) {
		return new SimpleMusicdataFileManipulator(fs);
	}

	/**
	 * Builds the xspf musicdata file manipulator.
	 * 
	 * @param failsave
	 * @param reporter
	 * @return
	 */
	public BaseMusicdataFileManipulator createXSPFmanipulator(boolean failsave, BaseErrorReporter reporter) {
		if (failsave) {
			return XSPFPlaylistFilesMusicdataManipulator.createFailsave(reporter);
		} else {
			return XSPFPlaylistFilesMusicdataManipulator.createWeak(reporter);
		}
	}

	/**
	 * The format of the playlist file.
	 * 
	 * @author martin
	 *
	 */
	public static enum PlaylistFileFormat {
		/**
		 * The XSPF.
		 */
		XSPF, 
		
		/**
		 * The simple text file.
		 */
		TXT,
		
		/**
		 * The properties file. Not supported currently.
		 */
		PROPERTIES;
	}

}

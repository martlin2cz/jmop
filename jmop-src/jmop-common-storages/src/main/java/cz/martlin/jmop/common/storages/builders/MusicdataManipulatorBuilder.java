package cz.martlin.jmop.common.storages.builders;

import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.simples.SimpleMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.xspf.XSPFPlaylistFilesMusicdataManipulator;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public class MusicdataManipulatorBuilder implements Builder<BaseMusicdataFileManipulator> {

	public MusicdataManipulatorBuilder() {
	}

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

	public BaseMusicdataFileManipulator createSimpleManipulator(BaseFileSystemAccessor fs) {
		return new SimpleMusicdataFileManipulator(fs);
	}

	public BaseMusicdataFileManipulator createXSPFmanipulator(boolean failsave, BaseErrorReporter reporter) {
		if (failsave) {
			return XSPFPlaylistFilesMusicdataManipulator.createFailsave(reporter);
		} else {
			return XSPFPlaylistFilesMusicdataManipulator.createWeak(reporter);
		}
	}

	public static enum PlaylistFileFormat {
		XSPF, TXT, PROPERTIES;
	}

}

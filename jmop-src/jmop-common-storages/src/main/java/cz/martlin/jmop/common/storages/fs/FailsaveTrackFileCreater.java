package cz.martlin.jmop.common.storages.fs;

import java.io.File;

import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.storages.locators.BaseTrackFileLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * Just an failsave extension of the {@link TrackFileCreater}. If anything
 * fails, just catches the exception, reports and returns as no track eas
 * provided.
 * 
 * @author martin
 *
 */
public class FailsaveTrackFileCreater extends TrackFileCreater {

	private final BaseErrorReporter reporter;

	public FailsaveTrackFileCreater(BaseTrackFileLocator tracksLocator, BaseFileSystemAccessor fs,
			BaseErrorReporter reporter) {
		super(tracksLocator, fs);

		this.reporter = reporter;
	}

	@Override
	protected File prepareTheActualFile(TrackFileCreationWay trackCreationWay, File trackSourceFile,
			File trackTargetFile) throws JMOPPersistenceException {
		try {
			return super.prepareTheActualFile(trackCreationWay, trackSourceFile, trackTargetFile);
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			reporter.report("The track file could not be prepared", e);
			return trackSourceFile;
		}
	}

}

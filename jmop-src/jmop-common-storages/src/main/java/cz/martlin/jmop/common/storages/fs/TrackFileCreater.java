package cz.martlin.jmop.common.storages.fs;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.storages.locators.BaseTrackFileLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An helper class responsible for the {@link TrackFileCreationWay}'s based
 * track file creation.
 * 
 * @author martin
 *
 */
public class TrackFileCreater {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	protected final BaseTrackFileLocator tracksLocator;
	private final BaseFileSystemAccessor fs;

	public TrackFileCreater(BaseTrackFileLocator tracksLocator, BaseFileSystemAccessor fs) {
		super();
		this.tracksLocator = tracksLocator;
		this.fs = fs;
	}

	public File performTrackFileCreate(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile) throws JMOPPersistenceException {
		LOG.info("Preparing file for {}, by the {} way with {} file", track.getTitle(), trackCreationWay, trackSourceFile);
		
		File trackTargetFile = tracksLocator.trackFile(track);

		File newTrackFile = prepareTheActualFile(trackCreationWay, trackSourceFile, trackTargetFile);

		track.setFile(newTrackFile);
		return trackTargetFile;
	}

	protected File prepareTheActualFile(TrackFileCreationWay trackCreationWay, File trackSourceFile,
			File trackTargetFile) throws JMOPPersistenceException {
		switch (trackCreationWay) {
		case COPY_FILE:
			copy(trackSourceFile, trackTargetFile);
			return trackTargetFile;
		case MOVE_FILE:
			move(trackSourceFile, trackTargetFile);
			return trackTargetFile;
		case LINK_FILE:
			link(trackSourceFile, trackTargetFile);
			return trackTargetFile;
		case JUST_SET:
			// nothing to be done
			return trackSourceFile;
		case NO_FILE:
			// nothing to be done
			return null;
		default:
			throw new IllegalArgumentException();
		}
	}

	private void copy(File trackSourceFile, File trackTargetFile) throws JMOPPersistenceException {
		LOG.debug("Coping {} to {}", trackSourceFile, trackTargetFile);
		
		fs.copyFile(trackSourceFile, trackTargetFile);
	}

	private void move(File trackSourceFile, File trackTargetFile) throws JMOPPersistenceException {
		LOG.debug("Moving {} to {}", trackSourceFile, trackTargetFile);
		
		fs.moveFile(trackSourceFile, trackTargetFile);
	}

	private void link(File trackSourceFile, File trackTargetFile) throws JMOPPersistenceException {
		LOG.debug("Linking {} to {}", trackSourceFile, trackTargetFile);
		
		fs.linkFile(trackTargetFile, trackSourceFile);
	}

}

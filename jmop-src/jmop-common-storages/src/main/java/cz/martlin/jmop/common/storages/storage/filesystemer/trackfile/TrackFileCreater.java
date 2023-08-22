package cz.martlin.jmop.common.storages.storage.filesystemer.trackfile;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.storage.filesystemer.BaseMusicbaseFilesystemer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseTrackFileLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An helper class responsible for the {@link TrackFileCreationWay}'s based
 * track file creation.
 * 
 * It's component of {@link BaseMusicbaseFilesystemer} implementations.
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

	/**
	 * Executes the track file creation action for the given track, with the track file (if any).
	 * 
	 * @param track
	 * @param trackCreationWay
	 * @param trackSourceFile
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public File performTrackFileCreate(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile) throws JMOPPersistenceException {
		LOG.info("Preparing file for {}, by the {} way with {} file", track.getTitle(), trackCreationWay, trackSourceFile);
		
		File trackTargetFile = tracksLocator.trackFile(track);

		File newTrackFile = prepareTheActualFile(trackCreationWay, trackSourceFile, trackTargetFile);

		track.setFile(newTrackFile);
		return trackTargetFile;
	}

	/**
	 * Prepares the track file to get set into the track.
	 * Does the actual filesystem job, if nescessary.
	 * 
	 * @param trackCreationWay
	 * @param trackSourceFile
	 * @param trackTargetFile
	 * @return
	 * @throws JMOPPersistenceException
	 */
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
		
		if (trackSourceFile == null) {
			LOG.warn("NOT coping the file, since there is no source file");
			return;
		}
		
		fs.copyFile(trackSourceFile, trackTargetFile);
	}

	private void move(File trackSourceFile, File trackTargetFile) throws JMOPPersistenceException {
		LOG.debug("Moving {} to {}", trackSourceFile, trackTargetFile);
		
		if (trackSourceFile == null) {
			LOG.warn("NOT moving the file, since there is no source file");
			return;
		}
		
		fs.moveFile(trackSourceFile, trackTargetFile);
	}

	private void link(File trackSourceFile, File trackTargetFile) throws JMOPPersistenceException {
		LOG.debug("Linking {} to {}", trackSourceFile, trackTargetFile);
		
		if (trackSourceFile == null) {
			LOG.warn("NOT linking the file, since there is no source file");
			return;
		}
		
		fs.linkFile(trackTargetFile, trackSourceFile);
	}

}

package cz.martlin.jmop.core.sources.locals.electronic.source;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesLocator;

public class ElectronicTracksSource implements BaseTracksLocalSource {

	private final BaseFilesLocator locator;
	private final BaseFileSystemAccessor filesystem;

	public ElectronicTracksSource(BaseFilesLocator locator, BaseFileSystemAccessor filesystem) {
		this.locator = locator;
		this.filesystem = filesystem;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format) {
		return file(track, location, format);
	}

	@Override
	public boolean exists(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException {
		File file = file(track, location, format);

		return filesystem.existsFile(file);
	}

	@Override
	public void deleteIfExists(Track track, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {
		File file = file(track, location, format);

		if (filesystem.existsFile(file)) {
			filesystem.deleteFile(file);
		}
	}

	@Override
	public void move(Track oldTrack, Track newTrack, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {
		File oldFile = file(oldTrack, location, format);
		File newFile = file(newTrack, location, format);

		filesystem.moveFile(oldFile, newFile);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private File file(Track track, TrackFileLocation location, TrackFileFormat format) {
		Bundle bundle = track.getBundle();
		return locator.getFileOfTrack(bundle, track, location, format);
	}

}

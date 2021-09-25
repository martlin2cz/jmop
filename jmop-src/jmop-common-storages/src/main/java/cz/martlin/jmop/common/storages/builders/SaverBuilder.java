package cz.martlin.jmop.common.storages.builders;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builders.MusicdataManipulatorBuilder.PlaylistFileFormat;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.musicdatasaver.MusicdataSaverWithFiles;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public class SaverBuilder implements Builder<BaseMusicdataSaver> {

	/**
	 * @deprecated build the manipulator elsewhere.
	 */
	@Deprecated
	private final MusicdataManipulatorBuilder musicdataBuilder = new MusicdataManipulatorBuilder();

	/**
	 * Replaced by {@link #create(BaseMusicdataFileManipulator, Locators)}.
	 * @param failsave
	 * @param playlistFileFormat
	 * @param reporter
	 * @param fs
	 * @param locators
	 * @param inmemory 
	 * @return
	 */
	@Deprecated
	public BaseMusicdataSaver create(boolean failsave, PlaylistFileFormat playlistFileFormat,
			BaseErrorReporter reporter, BaseFileSystemAccessor fs, Locators locators, BaseInMemoryMusicbase inmemory) {

		BaseMusicdataFileManipulator manipulator = musicdataBuilder.create(playlistFileFormat, failsave, reporter, fs);
		BaseMusicdataSaver saver = new MusicdataSaverWithFiles(locators.bundleDataLocator, locators.playlistsLocator,
				locators.tracksFilesLocator, manipulator, inmemory);
		return saver;
	}
	
	public BaseMusicdataSaver create(BaseMusicdataFileManipulator manipulator, Locators locators, BaseInMemoryMusicbase inmemory) {

		BaseMusicdataSaver saver = new MusicdataSaverWithFiles(locators.bundleDataLocator, locators.playlistsLocator,
				locators.tracksFilesLocator, manipulator, inmemory);
		return saver;
	}

}

package cz.martlin.jmop.common.storages.builder;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.builder.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.MusicdataSaverWithFiles;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.utils.Builder;

/**
 * The musicdata saver builder.
 * 
 * @author martin
 *
 */
public class SaverBuilder implements Builder<BaseMusicdataSaver> {

	/**
	 * Creates the musicdata saver.
	 * 
	 * @param manipulator
	 * @param locators
	 * @param inmemory
	 * @return
	 */
	public BaseMusicdataSaver create(BaseMusicdataFileManipulator manipulator, Locators locators,
			BaseInMemoryMusicbase inmemory) {

		BaseMusicdataSaver saver = new MusicdataSaverWithFiles(locators.bundleDataLocator, locators.playlistsLocator,
				manipulator, inmemory);
		return saver;
	}

}

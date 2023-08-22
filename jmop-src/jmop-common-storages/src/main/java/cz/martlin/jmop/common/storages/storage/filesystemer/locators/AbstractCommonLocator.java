package cz.martlin.jmop.common.storages.storage.filesystemer.locators;

import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;

/**
 * Just the common of all the bundle data file(s), playlist file(s) and track
 * file(s).
 * 
 * It's part of the various components of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public interface AbstractCommonLocator extends BaseBundleDataLocator, BasePlaylistLocator, BaseTrackFileLocator {

}
package cz.martlin.jmop.sourcery.locals.sourceries;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.local.BaseTracksImpoter;

/**
 * The common abstract importer. Does all the file-directory, recursivity, and
 * file format filtering.
 * 
 * @author martin
 *
 */
public abstract class AbstractTracksImporter implements BaseTracksImpoter {

	public AbstractTracksImporter() {
		super();
	}

	@Override
	public List<TrackData> importTracks(File dirOrFile, boolean recursive, TrackFileFormat filesFormat) {
		return resourceToTrack(dirOrFile, recursive, filesFormat);
	}

	/**
	 * Loads given file or dir into track data.
	 * 
	 * @param fileOrDir
	 * @param recursive
	 * @param filesFormat
	 * @return
	 */
	protected List<TrackData> resourceToTrack(File fileOrDir, boolean recursive, TrackFileFormat filesFormat) {

		if (fileOrDir.isDirectory()) {
			if (recursive) {
				return directoryToTracks(fileOrDir, recursive, filesFormat);
			} else {
				return Collections.emptyList();
			}
		}

		if (fileOrDir.isFile()) {
			return fileToTracks(fileOrDir, filesFormat);
		}

		// TODO what about (sym)link?

		return Collections.emptyList();

	}

	/**
	 * Loads given dir into track data.
	 * 
	 * @param dir
	 * @param recursive
	 * @param filesFormat
	 * @return
	 */
	protected List<TrackData> directoryToTracks(File dir, boolean recursive, TrackFileFormat filesFormat) {
		return Arrays.stream(dir.listFiles()) //
				.flatMap(f -> resourceToTrack(f, recursive, filesFormat).stream()) //
				.collect(Collectors.toList()); //
	}

	/**
	 * Loads given file into track data.
	 * 
	 * @param file
	 * @param format
	 * @return
	 */
	protected abstract List<TrackData> fileToTracks(File file, TrackFileFormat format);

}
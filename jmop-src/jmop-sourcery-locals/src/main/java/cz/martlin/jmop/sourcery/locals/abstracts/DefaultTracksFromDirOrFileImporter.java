package cz.martlin.jmop.sourcery.locals.abstracts;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.local.BaseTracksFromDirOrFileImporter;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;

/**
 * The common abstract importer. Does all the file-directory, recursivity, and
 * file format filtering.
 * 
 * @author martin
 *
 */
public class DefaultTracksFromDirOrFileImporter implements BaseTracksFromDirOrFileImporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTracksFromDirOrFileImporter.class);

	private final BaseTracksFromFileImporter fromFileImporter;

	public DefaultTracksFromDirOrFileImporter(BaseTracksFromFileImporter fromFileImporter) {
		super();
		this.fromFileImporter = fromFileImporter;
	}

	@Override
	public List<TrackData> importTracks(File dirOrFile, boolean recursive, TrackFileFormat filesFormat) {
		return rootResourceToTrack(dirOrFile, recursive, filesFormat);
	}

	/**
	 * Loads given file or dir into track data (even into dirs).
	 * 
	 * @param fileOrDir
	 * @param recursive
	 * @param filesFormat
	 * @return
	 * @throws IOException
	 */
	protected List<TrackData> rootResourceToTrack(File fileOrDir, boolean recursive, TrackFileFormat filesFormat) {

		if (fileOrDir.isDirectory()) {
			return directoryToTracks(fileOrDir, recursive, filesFormat);
		}

		if (fileOrDir.isFile()) {
			return fileToTracks(fileOrDir, filesFormat);
		}

		// TODO what about (sym)link?

		return Collections.emptyList();
	}

	/**
	 * Loads given file or dir into track data (ignoring dir if recursive = false).
	 * 
	 * @param fileOrDir
	 * @param recursive
	 * @param filesFormat
	 * @return
	 * @throws IOException
	 */
	protected List<TrackData> resourceToTrack(File fileOrDir, boolean recursive, TrackFileFormat filesFormat)
			throws IOException {

		if (fileOrDir.isDirectory()) {
			if (recursive) {
				return directoryToTracks(fileOrDir, recursive, filesFormat);
			}
		}

		if (fileOrDir.isFile()) {
			return fileToTracks(fileOrDir, filesFormat);
		}

		// TODO what about (sym)link?

		return Collections.emptyList();
	}

	/**
	 * Loads given file into track data.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private List<TrackData> fileToTracks(File file, TrackFileFormat filesFormat) {
		try {
			if (file.getName().endsWith(filesFormat.fileExtension())) {
				return fromFileImporter.importTracks(file);
			} else {
				LOGGER.debug("Ingoring file {}, it's not in the desired format", file);
				return Collections.emptyList();
			}
		} catch (IOException e) {
			LOGGER.error("Import from file {} failed", file);
			return Collections.emptyList();
		}
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
		return resourcesToTracks(Arrays.stream(dir.listFiles()), recursive, filesFormat);
	}

	/**
	 * Loads given resources into track data.
	 * 
	 * @param resources
	 * @param recursive
	 * @param filesFormat
	 * @return
	 */
	private List<TrackData> resourcesToTracks(Stream<File> resources, boolean recursive, TrackFileFormat filesFormat) {
		return resources.flatMap(f -> {
			try {
				return resourceToTrack(f, recursive, filesFormat).stream();
			} catch (IOException e) {
				LOGGER.error("Cannot import from directory {}", f, e);
				return Collections.<TrackData>emptyList().stream();
			}
		}) //
				.collect(Collectors.toList());
	}

}
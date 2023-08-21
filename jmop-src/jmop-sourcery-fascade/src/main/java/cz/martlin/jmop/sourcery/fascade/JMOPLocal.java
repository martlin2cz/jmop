package cz.martlin.jmop.sourcery.fascade;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.engine.TracksImporter;
import cz.martlin.jmop.sourcery.local.BaseTracksImpoter;

/**
 * The sourcery component responsible for local sourcering (importing from
 * elsewhere in the computer).
 * 
 * @author martin
 *
 */
public class JMOPLocal {

	private final TracksImporter importer;

	public JMOPLocal(BaseMusicbase musicbase, TrackFileFormat trackFormat, BaseTracksImpoter directory) {
		super();
		this.importer = new TracksImporter(musicbase, trackFormat, directory);
	}

	/**
	 * Import tracks from given dir or file into the given bundle.
	 * 
	 * @param dirOrFile
	 * @param bundle
	 * @param recursive
	 * @param createFiles
	 * @return
	 */
	public List<Track> importFromDirOrFile(File dirOrFile, Bundle bundle, boolean recursive,
			TrackFileCreationWay createFiles) {
		return importer.importFrom(dirOrFile, bundle, createFiles, recursive);
	}

	/**
	 * Import tracks from given dirs and/or files into the given bundle.
	 * 
	 * @param dirsOrFiles
	 * @param bundle
	 * @param recursive
	 * @param createFiles
	 * @return
	 */
	public List<Track> importFromDirsOrFiles(List<File> dirsOrFiles, Bundle bundle, boolean recursive,
			TrackFileCreationWay createFiles) {
		return dirsOrFiles.stream() //
				.flatMap(df -> importer.importFrom(df, bundle, createFiles, recursive).stream()) //
				.collect(Collectors.toList());
	}
}


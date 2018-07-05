package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public class DefaultFilesNamer extends AbstractFilesNamer {

	private static final String SEPARATOR = "_";
	private static final String HIDDEN_FILE_PREFIX = ".";
	private static final String PLAYLIST_FILE_SUFFIX = "xspf";

	public DefaultFilesNamer(File rootDir) {
		super(rootDir);
	}

	@Override
	protected String dirnameOfBundle(Bundle bundle) {
		String bundleName = bundle.getName();
		String cleanName = clean(bundleName);
		return cleanName;
	}

	@Override
	protected String filenameOfTrack(Track track) {
		String trackName = track.getTitle();
		String id = track.getIdentifier().getIdentifier();

		String cleanName = clean(trackName);

		return cleanName + SEPARATOR + id;
	}


	@Override
	protected String bundleNameOfDirectory(String directoryName) {
		return directoryName;
	}

	@Override
	protected boolean isBundleDir(String directoryName) {
		return !directoryName.startsWith(HIDDEN_FILE_PREFIX);
	}

	@Override
	protected String nameOfPlaylist(String filename) {
		return filename.replaceAll("\\.[^\\.]+$", "");
	}

	@Override
	protected boolean isPlaylistFile(String fileName) {
		return fileName.endsWith(DOT + PLAYLIST_FILE_SUFFIX);
	}

	
	private String clean(String text) {
		return text.replaceAll("/[^A-Za-z0-9]/", "_");
	}
	

}

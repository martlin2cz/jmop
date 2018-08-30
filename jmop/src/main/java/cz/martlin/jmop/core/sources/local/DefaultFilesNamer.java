package cz.martlin.jmop.core.sources.local;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.SourceKind;

public class DefaultFilesNamer extends SimpleFilesNamer {

	private static final String SEPARATOR = "_";
	private static final String HIDDEN_FILE_PREFIX = ".";
	private static final String PLAYLIST_FILE_SUFFIX = "xspf";

	private static final String FULL_PLAYLIST_NAME = "all_tracks";
	private static final String TEMP_DIR_NAME = "jmop";
	private static final String CACHE_DIR_NAME = "cache";

	public DefaultFilesNamer() {
	}

	@Override
	protected String filenameOfTrack(Track track) {
		String trackName = track.getTitle();
		String id = track.getIdentifier();

		String cleanName = clean(trackName);

		return cleanName + SEPARATOR + id;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected String dirnameOfBundle(SourceKind source, String name) {
		return clean(name);
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
	protected String tmpDirectoryName() {
		return TEMP_DIR_NAME;
	}
	
	@Override
	protected String cacheDirectoryName() {
		return CACHE_DIR_NAME;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected String nameOfPlaylist(String filename) {
		return filename.replaceAll("\\.[^\\.]+$", "");
	}

	@Override
	public String nameOfFullPlaylist() {
		return FULL_PLAYLIST_NAME;
	}

	@Override
	protected String filenameOfPlaylist(String name) {
		return name + DOT + PLAYLIST_FILE_SUFFIX;
	}

	@Override
	protected boolean isPlaylistFile(String fileName) {
		return fileName.endsWith(DOT + PLAYLIST_FILE_SUFFIX);
	}

	protected static String clean(String text) {
		return text.replaceAll("[^A-Za-z0-9\\-\\.\\_]", "_");
	}

}

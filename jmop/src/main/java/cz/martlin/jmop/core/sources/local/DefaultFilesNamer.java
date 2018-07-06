package cz.martlin.jmop.core.sources.local;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.Track;

public class DefaultFilesNamer extends SimpleFilesNamer {

	private static final String SEPARATOR = "_";
	private static final String HIDDEN_FILE_PREFIX = ".";
	private static final String PLAYLIST_FILE_SUFFIX = "xspf";

	private static final String FULL_PLAYLIST_NAME = "all_tracks";

	public DefaultFilesNamer() {
	}

	@Override
	protected String filenameOfTrack(Track track) {
		String trackName = track.getTitle();
		String id = track.getIdentifier().getIdentifier();

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

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected String nameOfPlaylist(String filename) {
		return filename.replaceAll("\\.[^\\.]+$", "");
	}

	@Override
	protected String nameOfFullPlaylist() {
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

	private String clean(String text) {
		return text.replaceAll("/[^A-Za-z0-9]/", "_");
	}

}

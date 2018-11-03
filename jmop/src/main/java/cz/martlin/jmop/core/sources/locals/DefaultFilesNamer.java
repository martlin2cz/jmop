package cz.martlin.jmop.core.sources.locals;

import cz.martlin.jmop.core.data.Track;

public class DefaultFilesNamer extends SimpleFilesNamer {

	private static final String SEPARATOR = "_";

	private static final String TEMP_DIR_NAME = "jmop";
	private static final String CACHE_DIR_NAME = ".cache";

	public DefaultFilesNamer() {
		super();
	}


	/////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String directoryNameOfBundle(String bundleName) {
		return convertBundleNameToDirName(bundleName);
	}

	private String convertBundleNameToDirName(String bundleName) {
		return clean(bundleName);
	}

	@Override
	public String fileNameOfPlaylist(String playlistName, String playlistFileExtension) {
		String name = convertPlaylistNameToFileName(playlistName);
		return name + DOT + playlistFileExtension;
	}

	private String convertPlaylistNameToFileName(String playlistName) {
		return clean(playlistName);
	}

	@Override
	public String fileNameOfTrack(Track track, TrackFileFormat format) {
		String name = convertTrackToTrackFile(track);
		return name + DOT + format.getExtension();
	}

	private String convertTrackToTrackFile(Track track) {
		String trackName = track.getTitle();
		String id = track.getIdentifier();

		String cleanName = clean(trackName);

		return cleanName + SEPARATOR + id;
	}

	@Override
	public String directoryNameOfCache() {
		return CACHE_DIR_NAME;
	}

	@Override
	public String temporaryDirectoryName() {
		return TEMP_DIR_NAME;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	protected static String clean(String text) {
		return text.replaceAll("[^A-Za-z0-9\\-\\.\\_]", "_");
	}

}

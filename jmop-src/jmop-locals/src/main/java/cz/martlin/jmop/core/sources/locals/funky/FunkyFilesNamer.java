package cz.martlin.jmop.core.sources.locals.funky;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The default files namer with following:
 * <ul>
 * <li>uses {@link #clean(String)} to replace all non-ascii charaters in the
 * dir/file names
 * <li>track file is named: "clean(TITLE)_ID.extension"
 * <li>temp directory is named "jmop"
 * <li>cache directory is named ".cache"
 * 
 * @author martin
 *
 */
public class FunkyFilesNamer extends SimpleFilesNamer {

	private static final String SEPARATOR = "_"; //$NON-NLS-1$

	private static final String TEMP_DIR_NAME = "jmop"; //$NON-NLS-1$
	private static final String CACHE_DIR_NAME = ".cache"; //$NON-NLS-1$

	public FunkyFilesNamer() {
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
		return text.replaceAll("[^A-Za-z0-9\\-\\.\\_]", "_"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}

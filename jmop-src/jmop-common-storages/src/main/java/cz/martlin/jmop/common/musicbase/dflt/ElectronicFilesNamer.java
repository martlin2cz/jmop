package cz.martlin.jmop.common.musicbase.dflt;

import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesNamer;

public class ElectronicFilesNamer implements BaseFilesNamer {
	private static final String ILLEGAL_CHAR_REGEX = "[^A-Za-z0-9\\-\\.\\_]";

	private static final String SEPARATOR = "_"; //$NON-NLS-1$

	private static final String TEMP_DIR_NAME = "jmop"; //$NON-NLS-1$
	private static final String CACHE_DIR_NAME = ".cache"; //$NON-NLS-1$

	public ElectronicFilesNamer() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public String directoryNameOfBundle(String bundleName) {
		return clean(bundleName);
	}

	@Override
	public String fileBasenameOfPlaylist(String playlistName) {
		return clean(playlistName);
	}

	@Override
	public String fileBasenameOfTrack(Track track) {
		String name = track.getTitle() + SEPARATOR + track.getIdentifier();
		return clean(name);
	}
	
	@Override
	public String fileBasenameOfTrack(String trackTitle) {
		return clean(trackTitle);
	}

	@Override
	public String nameOfTempDir() {
		return TEMP_DIR_NAME;
	}

	@Override
	public String nameOfCacheDir() {
		return CACHE_DIR_NAME;
	}
	///////////////////////////////////////////////////////////////////////////

	protected static String clean(String name) {
		return name.replaceAll(ILLEGAL_CHAR_REGEX, SEPARATOR);

	}

}

package cz.martlin.jmop.core.sources.local;

import cz.martlin.jmop.common.utils.FileExtensionSpecifier;

/**
 * The file format (so the audio format) of the track.
 * 
 * @author martin
 *
 */
public enum TrackFileFormat implements FileExtensionSpecifier {
	MP3("mp3"), OPUS("opus"), WAV("wav"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * The file extension.
	 */
	private final String extension;

	private TrackFileFormat(String extension) {
		this.extension = extension;
	}

	/**
	 * @deprecated use the {@link #fileExtension()} instead
	 * @return
	 */
	@Deprecated
	public String getExtension() {
		return extension;
	}

	public String fileExtension() {
		return extension;
	}
}

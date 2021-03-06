package cz.martlin.jmop.core.sources.local;

/**
 * The file format (so the audio format) of the track.
 * 
 * @author martin
 *
 */
public enum TrackFileFormat {
	MP3("mp3"), OPUS("opus"), WAV("wav"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * The file extension.
	 */
	private final String extension;

	private TrackFileFormat(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}
}

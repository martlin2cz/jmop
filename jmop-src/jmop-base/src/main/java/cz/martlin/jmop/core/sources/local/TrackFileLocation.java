package cz.martlin.jmop.core.sources.local;

/**
 * The symbolic location of track file.
 * 
 * @author martin
 *
 */
public enum TrackFileLocation {
	/**
	 * Temporary location. This location could be deleted ASAP, or even at the
	 * system reboot. For instance system temp dir.
	 */
	TEMP,

	/**
	 * The cache directory. This location may persist as long as it is not
	 * deleted by user. But it shall be hidden a bit (the hidden directory may
	 * come in place here).
	 */
	CACHE,

	/**
	 * The main directory, where the track files for the user may go to. This
	 * directory might be located somewhere in the userspace, even within the
	 * "Music" directory (but it shall be relative to app root directory.
	 */
	SAVE;
}

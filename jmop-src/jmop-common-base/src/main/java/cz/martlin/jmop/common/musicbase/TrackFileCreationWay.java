package cz.martlin.jmop.common.musicbase;

/**
 * Specifies how to create (or whether) the track file in the bundle location
 * based on the provided actual track file (if any).
 * 
 * @author martin
 *
 */
public enum TrackFileCreationWay {
	COPY_FILE, MOVE_FILE, LINK_FILE, JUST_SET, NO_FILE;
}

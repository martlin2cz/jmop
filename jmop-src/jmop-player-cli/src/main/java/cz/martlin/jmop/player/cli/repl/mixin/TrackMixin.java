package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Track;
import picocli.CommandLine.Parameters;

/**
 * The mandatory track mixin. Use in all the cases where the track is
 * nescessary to be provided (but possibly as a current one).
 * 
 * @author martin
 *
 */
public class TrackMixin extends AbstractJMOPMixin {

	@Parameters(arity = "1", //
			paramLabel = "TRACK", //
			description = "The title of track (either in 'track title' or 'bundle name/track title' format)"
			// defaultValue = AbstractJMOPConverter.USE_CURRENT
			)
	private Track track;

	public TrackMixin() {
	}

	/**
	 * Returns the track (never null).
	 * @return
	 */
	public Track getTrack() {
		return track;
	}
}

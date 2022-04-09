package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.converters.TrackConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Parameters;

/**
 * The mandatory track mixin, however just as a track inside of particular bundle.
 * 
 * @author martin
 *
 */
public class TrackOfBundleMixin extends AbstractJMOPMixin {
	
	@Parameters(arity = "1", //
			paramLabel = "TRACK", //
			description = "The track (specified by the 'track title')" //
//			defaultValue = AbstractJMOPConverter.USE_CURRENT
			)
	private String trackTitle;
	
	public TrackOfBundleMixin() {
	}
	
	/**
	 * Returns the track (never null) in the given bundle.
	 * 
	 * @param jmop
	 * @param bundle
	 * @return
	 */
	public Track getTrack(JMOPPlayer jmop, Bundle bundle)  {
		return TrackConverter.convertTrack(jmop, bundle, trackTitle);
	}
}

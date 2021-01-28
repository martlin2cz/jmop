package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.converters.AbstractJMOPConverter;
import cz.martlin.jmop.player.cli.repl.converters.TrackConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Parameters;

public class TrackOfBundleMixin extends AbstractJMOPMixin {
	
	@Parameters(arity = "0..1", defaultValue = AbstractJMOPConverter.USE_CURRENT)
	private String trackTitle;
	
	public TrackOfBundleMixin() {
	}
	
	public Track getTrack(JMOPPlayer jmop, Bundle bundle)  {
		return TrackConverter.convertTrack(jmop, bundle, trackTitle);
	}
}

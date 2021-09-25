package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.converters.AbstractJMOPConverter;
import picocli.CommandLine.Parameters;

public class TrackMixin extends AbstractJMOPMixin {
	
	@Parameters(arity = "0..1", defaultValue = AbstractJMOPConverter.USE_CURRENT)
	private Track track;
	
	public TrackMixin() {
	}
	
	public Track getTrack()  {
		return track;
	}
}

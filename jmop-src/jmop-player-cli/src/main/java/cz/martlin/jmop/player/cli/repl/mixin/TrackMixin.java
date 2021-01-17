package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.converter.TrackOrCurrentConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Parameters;

public class TrackMixin extends AbstractJMOPMixin {
	@Parameters(arity = "0..1")
	private Bundle bundle;
	
	@Parameters(arity = "0..1")
	private String trackTitle;
	
	public TrackMixin() {
	}
	
	public Track getTrack() throws JMOPMusicbaseException {
		JMOPPlayer jmop = getJMOP();
		TrackOrCurrentConverter converter = new TrackOrCurrentConverter(jmop);
		
		return converter.convert(bundle, trackTitle);
	}
}

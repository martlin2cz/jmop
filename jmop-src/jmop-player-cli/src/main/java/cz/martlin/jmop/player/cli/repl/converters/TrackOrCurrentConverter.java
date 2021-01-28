package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

public class TrackOrCurrentConverter extends TrackConverter {
	
	public TrackOrCurrentConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Track convert(String value) throws Exception {
		if (value.equals(USE_CURRENT)) {
			return currentTrack();
		} else {
			return track(value);
		}
	}

	public Track currentTrack() {
		Track track = jmop.playing().currentTrack();
		
		if (track == null) {
			throw new CommandLine.TypeConversionException("There is no current track");
		}
		
		return track;
	}

}
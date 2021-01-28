package cz.martlin.jmop.player.cli.repl.converter;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.converters.AbstractJMOPConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

@Deprecated
public class TrackOrCurrentConverter  extends AbstractJMOPConverter<Track> {
	
	public TrackOrCurrentConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Track convert(String value) throws Exception {
		if (value == null || value.equals(USE_CURRENT)) {
			return pickCurrentTrack();
		} else {
			return pickTrackByName(jmop.playing().currentBundle(), value);
		}
	}

	private Track pickTrackByName(Bundle bundle, String value)  {
		System.err.println("Warning, assuming current bundle!");
		return jmop.musicbase().trackOfTitle(bundle, value);
	}

	private Track pickCurrentTrack() {
		return jmop.playing().currentTrack();
	}

	public Track convert(Bundle bundle, String trackTitle)  {
		if (trackTitle == null || trackTitle.equals(USE_CURRENT)) {
			return pickCurrentTrack();
		} else {
			return pickTrackByName(bundle, trackTitle);
		}
	}
	
	

}

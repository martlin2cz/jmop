package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

public class CurrentPlaylistTrackIndexOrCurrentConverter extends CurrentPlaylistTrackIndexConverter {

	public CurrentPlaylistTrackIndexOrCurrentConverter(JMOPPlayer jmop) {
		super(jmop);
	}
	
	@Override
	public TrackIndex convert(String value) throws Exception {
		if (value.equals(USE_CURRENT)) {
			return currentTrackIndex();
		} else {
			return super.convert(value);
		}
	}

	private TrackIndex currentTrackIndex() {
		return playlist().getCurrentTrackIndex();
	}

}

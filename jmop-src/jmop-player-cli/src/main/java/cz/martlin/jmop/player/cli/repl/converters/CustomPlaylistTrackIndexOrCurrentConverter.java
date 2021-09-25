package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

public class CustomPlaylistTrackIndexOrCurrentConverter extends CustomPlaylistTrackIndexConverter {

	public CustomPlaylistTrackIndexOrCurrentConverter(JMOPPlayer jmop, Playlist playlist) {
		super(jmop, playlist);
	}

	@Override
	public TrackIndex convert(String value) throws Exception {
		return trackIndexOrCurrent(value);
	}

	public TrackIndex trackIndexOrCurrent(String trackIndexOrCurrent) {
		if (trackIndexOrCurrent.equals(USE_CURRENT)) {
			return currentTrackIndex();
		} else {
			return trackIndex(trackIndexOrCurrent);
		}
	}

	private TrackIndex currentTrackIndex() {
		return playlist().getCurrentTrackIndex();
	}

	public static TrackIndex convert(JMOPPlayer jmop, Playlist playlist, String value) {
		CustomPlaylistTrackIndexOrCurrentConverter converter = new CustomPlaylistTrackIndexOrCurrentConverter(jmop, playlist);
		return converter.trackIndexOrCurrent(value);
	}
}

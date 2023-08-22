package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

/**
 * The track index of the current playlist converter.
 * 
 * @author martin
 *
 */
public class CurrentPlaylistTrackIndexConverter extends AbstractTrackIndexConverter {

	public CurrentPlaylistTrackIndexConverter(JMOPPlayer jmop) {
		super(jmop);
	}
	
	@Override
	protected Bundle bundle() {
		return jmop.status().currentBundle();
	}
	
	@Override
	protected Playlist playlist() {
		return jmop.status().currentPlaylist();
	}

	public static TrackIndex convertIndex(JMOPPlayer jmop, String specifier) {
		CurrentPlaylistTrackIndexConverter converter = new CurrentPlaylistTrackIndexConverter(jmop);
		return converter.trackIndex(specifier);
	}

}

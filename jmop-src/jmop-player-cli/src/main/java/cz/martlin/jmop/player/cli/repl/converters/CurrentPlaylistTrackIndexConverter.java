package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

public class CurrentPlaylistTrackIndexConverter extends AbstractTrackIndexConverter {

	public CurrentPlaylistTrackIndexConverter(JMOPPlayer jmop) {
		super(jmop);
	}
	
	@Override
	protected Bundle bundle() {
		return jmop.playing().currentBundle();
	}
	
	@Override
	protected Playlist playlist() {
		return jmop.playing().currentPlaylist();
	}

}

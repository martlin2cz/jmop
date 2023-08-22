package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

/**
 * The track index converter based on specified playlist.
 * 
 * @author martin
 *
 */
public class CustomPlaylistTrackIndexConverter extends AbstractTrackIndexConverter {

	private final Playlist playlist;

	public CustomPlaylistTrackIndexConverter(JMOPPlayer jmop, Playlist playlist) {
		super(jmop);

		this.playlist = playlist;
	}

	@Override
	protected Bundle bundle() {
		return playlist.getBundle();
	}

	@Override
	protected Playlist playlist() {
		return playlist;
	}

}

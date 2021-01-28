package cz.martlin.jmop.player.cli.repl.converter;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.converters.AbstractJMOPConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

@Deprecated
public class PlaylistOrCurrentConverter extends AbstractJMOPConverter<Playlist> {

	public PlaylistOrCurrentConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Playlist convert(String value) throws Exception {
		if (value == null || value.equals(USE_CURRENT)) {
			return pickCurrentPlaylist();
		} else {
			return pickPlaylistByName(jmop.playing().currentBundle(), value);
		}
	}

	private Playlist pickPlaylistByName(Bundle bundle, String value)  {
		System.err.println("Warning, assuming current bundle!");
		return jmop.musicbase().playlistOfName(bundle, value);
	}

	private Playlist pickCurrentPlaylist() {
		return jmop.playing().currentPlaylist();
	}

	public Playlist convert(Bundle bundle, String playlistName)  {
		if (playlistName == null || playlistName.equals(USE_CURRENT)) {
			return pickCurrentPlaylist();
		} else {
			return pickPlaylistByName(bundle, playlistName);
		}
	}
	
	

}

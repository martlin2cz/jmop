package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

public class PlaylistOrCurrentConverter extends PlaylistConverter {
	
	public PlaylistOrCurrentConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Playlist convert(String value) throws Exception {
		if (value.equals(USE_CURRENT)) {
			return currentPlaylist();
		} else {
			return playlist(value);
		}
	}

	public Playlist currentPlaylist() {
		Playlist playlist = jmop.playing().currentPlaylist();
		
		if (playlist == null) {
			throw new CommandLine.TypeConversionException("There is no current playlist");
		}
		
		return playlist;
	}

}

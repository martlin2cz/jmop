package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.cli.repl.converters.AbstractJMOPConverter;
import picocli.CommandLine.Parameters;

public class PlaylistMixin extends AbstractJMOPMixin {
	
	@Parameters(arity = "0..1", defaultValue = AbstractJMOPConverter.USE_CURRENT)
	private Playlist playlist;
	
	public PlaylistMixin() {
	}
	
	public Playlist getPlaylist()  {
		return playlist;
	}
}

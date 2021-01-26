package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.converter.PlaylistOrCurrentConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Parameters;

public class PlaylistMixin extends AbstractJMOPMixin {
	@Parameters(arity = "1")
	private Bundle bundle;
	
	@Parameters(arity = "1")
	private String playlistName;
	
	public PlaylistMixin() {
	}
	
	public Playlist getPlaylist()  {
		JMOPPlayer jmop = getJMOP();
		PlaylistOrCurrentConverter converter = new PlaylistOrCurrentConverter(jmop);
		
		return converter.convert(bundle, playlistName);
	}
}

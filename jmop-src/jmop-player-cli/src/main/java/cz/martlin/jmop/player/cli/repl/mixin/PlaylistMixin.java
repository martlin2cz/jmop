package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Playlist;
import picocli.CommandLine.Parameters;

/**
 * The mandatory playlist mixin. Use in all the cases where the playlist is
 * nescessary to be provided (but possibly as a current one).
 * 
 * @author martin
 *
 */
public class PlaylistMixin extends AbstractJMOPMixin {
	
	@Parameters(arity = "1", 
			paramLabel = "PLAYLIST", //
			description = "The playlist (either in 'playlist name' or 'bundle name/playlist name' format)")
	private Playlist playlist;
	
	public PlaylistMixin() {
	}
	
	/**
	 * Returns the playlist (never null).
	 * @return
	 */
	public Playlist getPlaylist()  {
		return playlist;
	}
}

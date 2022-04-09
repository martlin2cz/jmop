package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Playlist;

//@TestMethodOrder(OrderAnnotation.class)
class JmopReplAdvancedMusicbaseTest extends AbstractReplTest {

	
	@BeforeEach
	public void before()  {
		Playlist playlist = tme.tmd.bestTracks;
		jmop.playing().play(playlist);
	}
	
	@AfterEach
	public void after()  {
		jmop.playing().stop();
	}


	@Test
	void testListCommands()  {
		// bundles
		exec("list", "bundles");
		
		// playlists
		exec("list", "playlists");
		exec("list", "playlists", ".");
		
		// tracks
		exec("list", "tracks");
		exec("list", "tracks", ".");
	}
	
	@Test
//	@Order(value = 2)
	void testCreate()  {
		// bundle
		// no need to test create bundle with "."
		
		// playlist
		exec("create", "playlist", ".", "deadmau5");
		
		// track
		exec("create", "track", ".", "Ghosts N stuff", "duration", "3:15", "description", "deadmau5 feat. Rob Swire - Ghosts N Stuff", "no-file");
	}

	@Test
	void testRename()  {
		// bundle
		exec("rename", "bundle", ".", "Tony Collman");
				
		// playlist
		exec("rename", "playlist", ".", "all the best tracks");
		
		// track
		exec("rename", "track", ".", "Just one second (original)");
	}
	
	@Test
	void testMovePlaylistCurrentToSpecified()  {
		// Notice: if we move playlist, it moves its tracks as well
		// thus, each move has to be executed separatelly,
		// to let the musicbase to roll it back
		String daftPunk = tme.tmd.daftPunk.getName();
		
		// playlist
		exec("move", "playlist", ".", daftPunk);
	}
	
	@Test
	void testMovePlaylistSpecifiedToCurrent()  {
		// Notice: if we move playlist, it moves its tracks as well
		// thus, each move has to be executed separatelly,
		// to let the musicbase to roll it back		
		String daftPunk = tme.tmd.daftPunk.getName();
		String discovery = tme.tmd.discovery.getName();
		
		// playlist
		exec("move", "playlist", couple(daftPunk, discovery), ".");
	}
	
	@Test
	void testMoveTracks()  {
		String daftPunk = tme.tmd.daftPunk.getName();
		String aerodynamics = tme.tmd.aerodynamic.getTitle();
		
		// track
		exec("move", "track", ".", daftPunk);
		exec("move", "track", couple(daftPunk, aerodynamics), ".");
	}
	
	
	@Test
	void testRemove()  {
		// important: we have to remove the whole bundle last
		// IMPRTANTER: we have to run both delete methods separatelly,
		// since deleted X cannot be deleted again
		
		// track
		exec("delete", "track", ".");
		
		// playlist
		exec("delete", "playlist", ".");
				
		// bundle
		exec("delete", "bundle", ".");
	}
	
	@Test
	void testRemoveSimpler()  {
		// important: we have to remove the whole bundle last

		
		// track
		exec("delete", "track", ".");
		
		// playlist
		exec("delete", "playlist", ".");
				
		// bundle
		exec("delete", "bundle", ".");
	}
	
	@Test
//	@Order(value = 2)
	void testModifyPlaylist()  {
		String invisibleWorlds = tme.tmd.invisibleWorlds.getTitle();
		
//		DO NOT: exec("playlist", ".", "add");
		exec("playlist", ".", "add", invisibleWorlds);

		exec("playlist", ".", "insert", "1.", invisibleWorlds);
//		DO NOT: 		exec("playlist", ".", "insert", "1.", ".");

		exec("playlist", ".", "remove", invisibleWorlds);
//		DO NOT: 	exec("playlist", ".", "remove", ".");
		
		exec("playlist", ".", "remove", "1.");
	}

	
}

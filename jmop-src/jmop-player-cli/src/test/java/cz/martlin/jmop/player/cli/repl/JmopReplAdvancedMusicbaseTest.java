package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Playlist;

//@TestMethodOrder(OrderAnnotation.class)
class JmopReplAdvancedMusicbaseTest extends AbstractReplTest {

	
	@BeforeEach
	public void before()  {
		Playlist playlist = tmb.tm.bestTracks;
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
		exec("list", "playlists", ".");
		
		// tracks
		exec("list", "tracks", ".");
	}
	
	@Test
//	@Order(value = 2)
	void testCreate()  {
		// bundle
		// no need to test create bundle
		
		// playlist
		exec("create", "playlist", ".", "deadmau5");
		
		// track
		exec("create", "track", ".", "Ghosts N stuff", "duration", "3:15", "description", "deadmau5 feat. Rob Swire - Ghosts N Stuff");
	}

	@Test
	void testRename()  {
		// bundle
		exec("rename", "bundle", ".", "Tony Collman");
				
		// playlist
		exec("rename", "playlist", ".", ".", "all the best tracks");
		
		// track
		exec("rename", "track", ".", ".", "Just one second (original)");
	}
	
	@Test
	void testMove()  {
		String daftPunk = tmb.tm.daftPunk.getName();
		String discovery = tmb.tm.discovery.getName();
		String aerodynamics = tmb.tm.aerodynamic.getTitle();
		
		// playlist
		exec("move", "playlist", ".", ".", daftPunk);
		exec("move", "playlist", daftPunk, discovery, ".");

		
		// track
		exec("move", "track", ".", ".", daftPunk);
		exec("move", "track", daftPunk, aerodynamics, ".");
	}
	
	@Test
	void testRemove()  {
		// important: we have to remove the whole bundle last
		
		// track
		exec("remove", "track", ".", ".");
		
		// playlist
		exec("remove", "playlist", ".", ".");
				
		// bundle
		exec("remove", "bundle", ".");
	}
	
	@Test
//	@Order(value = 2)
	void testModifyPlaylist()  {
		String invisibleWorlds = tmb.tm.invisibleWorlds.getTitle();
		
		exec("playlist", ".", ".", "add", ".", invisibleWorlds);

		exec("playlist", ".", ".", "insert", ".", invisibleWorlds, "0");
		exec("playlist", ".", ".", "insert", ".", ".", "0");

//		//FIXME track by index
//		exec("playlist", ".", ".", "remove", "0");
//		exec("playlist", ".", ".", "LoremBundle/testing");

		exec("playlist", ".", ".", "remove",".",  invisibleWorlds);
		exec("playlist", ".", ".", "remove", ".", ".");
	}

	
}

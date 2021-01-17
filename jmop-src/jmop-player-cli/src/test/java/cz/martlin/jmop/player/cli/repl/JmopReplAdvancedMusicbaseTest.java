package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

//@TestMethodOrder(OrderAnnotation.class)
class JmopReplAdvancedMusicbaseTest extends AbstractReplTest {

	
	@BeforeEach
	public void before() throws JMOPMusicbaseException {
		Playlist playlist = tmb.tm.bestTracks;
		jmop.playing().play(playlist);
	}
	
	@AfterEach
	public void after() throws JMOPMusicbaseException {
		jmop.playing().stop();
	}


	@Test
//	@Order(value = 2)
	void testCreate() throws JMOPMusicbaseException {
		// bundle
		// no need to test create bundle
		
		// playlist
		exec("create", "playlist", ".", "deadmau5");
		
		// track
		exec("create", "track", ".", "Ghosts N stuff", "duration", "3:15", "description", "deadmau5 feat. Rob Swire - Ghosts N Stuff");
	}

	@Test
	void testRename() throws JMOPMusicbaseException {
		// bundle
		exec("rename", "bundle", ".", "Tony Collman");
				
		// playlist
		exec("rename", "playlist", ".", ".", "all the best tracks");
		
		// track
		exec("rename", "track", ".", ".", "Just one second (original)");
	}
	
	@Test
	void testMove() throws JMOPMusicbaseException {
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
	void testRemove() throws JMOPMusicbaseException {
		// important: we have to remove the whole bundle last
		
		// track
		exec("remove", "track", ".", ".");
		
		// playlist
		exec("remove", "playlist", ".", ".");
				
		// bundle
		exec("remove", "bundle", ".");
	}
	
	@Disabled
	@Test
//	@Order(value = 2)
	void testModifyPlaylist() throws JMOPMusicbaseException {
		exec("playlist", ".", "add", "track99");

		exec("playlist", ".", "insert", "0", "track99");

//		//FIXME track by index
//		exec("playlist", ".", "remove", "0");
//		exec("playlist", ".", "LoremBundle/testing");

		exec("playlist", ".", "remove", "track99");
	}

	
}

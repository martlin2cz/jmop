package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;

//@TestMethodOrder(OrderAnnotation.class)
class JmopReplBasicMusicbaseTest extends AbstractReplTest {


	@Test
	void testListCommands()  {
		String daftPunk = tmb.tm.daftPunk.getName();
		
		// bundles
		exec("list", "bundles");
		
		// playlists
		exec("list", "playlists");
		exec("list", "playlists", daftPunk);

		// tracks
		exec("list", "tracks");
		exec("list", "tracks", daftPunk);
	}
	
	@Test
//	@Order(value = 1)
	void testCreateCommands()  {
		// bundle
		exec("create", "bundle", "deadmau5");
		// playlist
		exec("create", "playlist", "deadmau5", "chill mix");
		// track
		exec("create", "track", "deadmau5", "Ghosts N stuff", "duration", "3:15", "description", "deadmau5 feat. Rob Swire - Ghosts N Stuff");
	}

	@Test
//	@Order(value = 2)
	void testModifyPlaylist()  {
		String londonElektricity = tmb.tm.londonElektricity.getName();
		String bestTracks = tmb.tm.bestTracks.getName();
		String invisibleWorlds = tmb.tm.invisibleWorlds.getTitle();
		
		exec("playlist", couple(londonElektricity, bestTracks), "add", invisibleWorlds);
		exec("playlist", couple(londonElektricity, bestTracks), "insert", invisibleWorlds, "1.");

		exec("playlist", couple(londonElektricity, bestTracks), "remove", "1.");
		exec("playlist", couple(londonElektricity, bestTracks), "remove", invisibleWorlds);
	}

	@Test
//	@Order(value = 3)
	void testRenameCommands()  {
		// bundle
		String londonElektricity = tmb.tm.londonElektricity.getName();
		exec("rename", "bundle", londonElektricity, "Tony Collman");
		
		// playlist
		String cocolinoDeep = tmb.tm.cocolinoDeep.getName();
		String seventeen = tmb.tm.seventeen.getName();
		exec("rename", "playlist", couple(cocolinoDeep, seventeen), "Seventeen I.-IV.");
		
		// track
		String robick = tmb.tm.robick.getName();
		String atZijiDuchove = tmb.tm.atZijiDuchove.getTitle();
		exec("rename", "track", couple(robick, atZijiDuchove), "Zavolejte straze REMIX");
	}

	@Test
//	@Order(value = 4)
	void testMoveCommands()  {
		String electronicMusic = "Electronic music";
		Bundle emb = jmop.musicbase().createNewBundle(electronicMusic);
		
		try {
		// playlist
		String cocolinoDeep = tmb.tm.cocolinoDeep.getName();
		String seventeen = tmb.tm.seventeen.getName();
		exec("move", "playlist", couple(cocolinoDeep, seventeen), electronicMusic);
		
		// track
		String robick = tmb.tm.robick.getName();
		String atZijiDuchove = tmb.tm.atZijiDuchove.getTitle();
		exec("move", "track", couple(robick, atZijiDuchove), electronicMusic);
		} finally {
			jmop.musicbase().removeBundle(emb);
		}
		
	}

	@Test
//	@Order(value = 5)
	void testRemoveMusicbase()  {
		// bundle
		String londonElektricity = tmb.tm.londonElektricity.getName();
		exec("remove", "bundle", londonElektricity);
		
		// playlist
		String cocolinoDeep = tmb.tm.cocolinoDeep.getName();
		String seventeen = tmb.tm.seventeen.getName();
		exec("remove", "playlist", couple(cocolinoDeep, seventeen));
		
		// track
		String robick = tmb.tm.robick.getName();
		String atZijiDuchove = tmb.tm.atZijiDuchove.getTitle();
		exec("remove", "track", couple(robick, atZijiDuchove));
	}

}

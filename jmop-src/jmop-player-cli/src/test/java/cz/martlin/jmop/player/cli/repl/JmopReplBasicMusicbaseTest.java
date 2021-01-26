package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

//@TestMethodOrder(OrderAnnotation.class)
class JmopReplBasicMusicbaseTest extends AbstractReplTest {

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

	@Disabled
	@Test
//	@Order(value = 2)
	void testModifyPlaylist()  {
		//TODO 
		exec("create", "playlist", "LoremBundle", "testing");
		exec("playlist", "LoremBundle/testing");

		exec("playlist", "add", "track99");
		exec("playlist", "LoremBundle/testing");

		exec("playlist", "insert", "0", "track99");
		exec("playlist", "LoremBundle/testing");

//		//FIXME track by index
//		exec("playlist", "remove", "0");
//		exec("playlist", "LoremBundle/testing");

		exec("playlist", "remove", "track99");
		exec("playlist", "LoremBundle/testing");
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
		exec("rename", "playlist", cocolinoDeep, seventeen, "Seventeen I.-IV.");
		
		// track
		String robick = tmb.tm.robick.getName();
		String atZijiDuchove = tmb.tm.atZijiDuchove.getTitle();
		exec("rename", "track", robick, atZijiDuchove, "Zavolejte straze REMIX");
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
		exec("move", "playlist", cocolinoDeep, seventeen, electronicMusic);
		
		// track
		String robick = tmb.tm.robick.getName();
		String atZijiDuchove = tmb.tm.atZijiDuchove.getTitle();
		exec("move", "track", robick, atZijiDuchove, electronicMusic);
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
		exec("remove", "playlist", cocolinoDeep, seventeen);
		
		// track
		String robick = tmb.tm.robick.getName();
		String atZijiDuchove = tmb.tm.atZijiDuchove.getTitle();
		exec("remove", "track", robick, atZijiDuchove);
	}

}

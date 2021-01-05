package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import picocli.CommandLine;

@TestMethodOrder(OrderAnnotation.class)
class JmopReplMusicbaseTest extends AbstractReplTest {

	@Test
	@Order(value = 1)
	void testCreateMusicbase() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		// basic testing
		exec(cl, "create", "bundle", "LoremBundle");
		exec(cl, "create", "playlist", "LoremBundle", "karelPlaylist");
		exec(cl, "create", "track", "LoremBundle", "track42", "duration", "1:23", "description", "da desc");

		// further testing data
		exec(cl, "create", "bundle", "IpsumBundle");
		exec(cl, "create", "playlist", "LoremBundle", "frantaPlaylist");
		exec(cl, "create", "track", "LoremBundle", "track99", "duration", "9:09");
	}

	@Test
	@Order(value = 2)
	void testRenameMusicbase() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		exec(cl, "rename", "bundle", "LoremBundle", "LOREMBundle");
		exec(cl, "rename", "playlist", "LOREMBundle/karelPlaylist", "KARELPlaylist");
		exec(cl, "rename", "track", "LOREMBundle/track42", "TRACK99");

	}
	
	@Test
	@Order(value = 3)
	void testMoveMusicbase() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		exec(cl, "move", "playlist", "LOREMBundle/KARELPlaylist", "IpsumBundle");
		exec(cl, "move", "track", "LOREMBundle/TRACK99", "IpsumBundle");

	}

	@Test
	@Order(value = 4)
	void testRemoveMusicbase() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		exec(cl, "remove", "bundle", "LOREMBundle");
		exec(cl, "remove", "playlist", "IpsumBundle/KARELPlaylist");
		exec(cl, "remove", "track", "IpsumBundle/TRACK99");

	}
	
}

package cz.martlin.jmop.player.cli.repl;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.player.cli.repl.exit.JMOPExceptionManager;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;
import picocli.CommandLine;

public class AbstractReplTest {
	protected JMOPPlayer jmop;

	@RegisterExtension
	protected TestingMusicdataExtension tme;

	protected CommandLine cl;

	public AbstractReplTest() {
		super();

		jmop = DefaultJMOPPlayerBuilder.createTesting();

		BaseMusicbase musicbase = jmop.musicbase().getMusicbase();
		tme = TestingMusicdataExtension.create(musicbase, true);
	}

/////////////////////////////////////////////////////////////////////

	@BeforeEach
	public void prepare() {
//		System.setProperty("picocli.trace", "INFO");

		this.cl = prepareRepl();
	}

	@AfterEach
	public void finish() {
		finishJmop(this.jmop);

//		System.setProperty("picocli.trace", "");
	}

/////////////////////////////////////////////////////////////////////

//	private JMOPPlayer prepareJMOP() {
//		JMOPPlayer jmop = DefaultJMOPPlayerBuilder.createTesting();
//		try {
//			jmop.config().load();
//		} catch (JMOPMusicbaseException e) {
//			e.printStackTrace();
//			assumeFalse(e != null);
//		}
//
//		return jmop;
//	}

	private void finishJmop(JMOPPlayer jmop) {
		jmop.config().terminate();
	}

	private CommandLine prepareRepl() {
		AbstractRepl repl = new JmopRepl(jmop);
		return repl.createStandaloneCommandline();
	}

//	private TestingMusicbase prepareTestingMusicbase() {
//		try {
//			return new TestingMusicbase(jmop.musicbase().getMusicbase(), true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			assumeTrue(e == null, e.toString());
//			return null;
//		}
//	}

	private void finishTestingMusicbase(AbstractTestingMusicdata tmb) {
		try {
			tmb.close();
		} catch (Exception e) {
			e.printStackTrace();
			assumeTrue(e == null, e.toString());
		}
	}

/////////////////////////////////////////////////////////////////////

	protected void exec(String... command) {
		System.out.println("======= EXECUTING " + Arrays.toString(command) + " =======");

		int code = this.cl.execute(command);

		if (code == JMOPExceptionManager.OK //
				|| code == JMOPExceptionManager.INVALID_USAGE_REJECTED) {
			// okay
		} else {
			fail("The command " + Arrays.toString(command) + " " //
					+ "ended with " + JMOPExceptionManager.errorCodeToString(code) + " code.");
		}
	}

	protected String couple(String first, String second) {
		return first + "/" + second;
	}
	
	protected String couple(Bundle bundle, Track track) {
		return couple(bundle.getName(), track.getTitle());
	}
	
	protected String couple(Bundle bundle, Playlist playlist) {
		return couple(bundle.getName(), playlist.getName());
	}

}
package cz.martlin.jmop.sourcery.app.mains;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.ginsberg.junit.exit.FailOnSystemExit;

import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.resources.TestingRootDir;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryBuilder;

@Tag("IDE_ONLY")
class DownloadingTrackAdderMainTest {

	@RegisterExtension
	public TestingRootDir root = new TestingRootDir(this);

	@RegisterExtension
	public TestingMusicdataExtension tme = TestingMusicdataExtension.withMusicbase(() -> createMusicbase(), null);

	@FailOnSystemExit
	@Test
	void test() {
		String[] args = new String[] { //
				root.getFile().getAbsolutePath(), //
				tme.tmd.robick.getName(), //
				"robick remix" //
		};

		DownloadingTrackAdderMain.main(args);
	}

	private BaseMusicbaseModifing createMusicbase() {
		// TODO little hack
		return DownloadingTrackAdderMain.createJMOP(root.getFile()).musicbase().getMusicbase();
	}

}

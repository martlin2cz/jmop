package cz.martlin.jmop.player.fascade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

class JMOPPlayingTest {

	@RegisterExtension
	public TestingMusicdataExtension tme;

	private JMOPPlaying playing;

	public JMOPPlayingTest() {
		JMOPPlayer jmop = DefaultJMOPPlayerBuilder.createTesting();
		this.playing = jmop.playing();

		BaseMusicbaseModifing musicbase = jmop.musicbase().getMusicbase();
		tme = TestingMusicdataExtension.create(musicbase, true);
	}

	@Test
	void testIt()  {

		check(null, null, null, PlayerStatus.NO_TRACK, null);

		playing.play(tme.tmd.discovery);
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.oneMoreTime, PlayerStatus.PLAYING, d(0));

		playing.pause();
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.oneMoreTime, PlayerStatus.PAUSED, d(1));

		playing.resume();
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.oneMoreTime, PlayerStatus.PLAYING, d(2));

		playing.toNext();
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.aerodynamic, PlayerStatus.PLAYING, d(0));

		playing.toNext();
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.verdisQuo, PlayerStatus.PLAYING, d(0));

		playing.toPrevious();
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.aerodynamic, PlayerStatus.PLAYING, d(0));

		playing.stop();
		check(tme.tmd.daftPunk, tme.tmd.discovery, null, PlayerStatus.STOPPED, null);

		playing.play();
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.aerodynamic, PlayerStatus.PLAYING, d(0));

		playing.play(TrackIndex.ofIndex(2));
		check(tme.tmd.daftPunk, tme.tmd.discovery, tme.tmd.verdisQuo, PlayerStatus.PLAYING, d(0));

		playing.play(tme.tmd.syncopatedCity);
		check(tme.tmd.londonElektricity, tme.tmd.syncopatedCity, tme.tmd.justOneSecond, PlayerStatus.PLAYING, d(0));

		playing.stop();
		check(tme.tmd.londonElektricity, tme.tmd.syncopatedCity, null, PlayerStatus.STOPPED, null);
	}
	
	//TODO test non-existing track files

	private void check(Bundle expectedBundle, Playlist expectedPlaylist, Track expectedTrack,
			PlayerStatus expectedStatus, Duration expectedDuration) {

		JMOPStatus status = new JMOPStatus(playing.getEngine());
		
		assertEquals(expectedBundle, status.currentBundle());
		assertEquals(expectedPlaylist, status.currentPlaylist());
		assertEquals(expectedTrack, status.currentTrack());
		assertEquals(expectedStatus, status.currentStatus());
		assertEquals(expectedDuration, status.currentDuration());
	}

	private Duration d(int sec) {
		return DurationUtilities.createDuration(0, 0, sec);
	}

}

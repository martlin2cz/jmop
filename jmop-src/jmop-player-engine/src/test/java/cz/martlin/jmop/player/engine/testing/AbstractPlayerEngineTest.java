package cz.martlin.jmop.player.engine.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.utils.TestingDataCreator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.LoggingPlayerEngine;
import cz.martlin.jmop.player.players.PlayerStatus;

public abstract class AbstractPlayerEngineTest {

	private Track barTrack;
	private Track fooTrack;
	private Track bazTrack;

	protected abstract BasePlayerEngine createEngine();

	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testSome() throws JMOPMusicbaseException {
		BasePlayerEngine engine = log(createEngine());
		Playlist playlist = playlist();

		check(engine, null, null, PlayerStatus.NO_TRACK);
		waitAbit();

		engine.startPlayingPlaylist(playlist);
		check(engine, playlist, null, PlayerStatus.NO_TRACK);
		waitAbit();

		engine.play();
		check(engine, playlist, fooTrack, PlayerStatus.PLAYING);
		waitAbit();

		engine.pause();
		check(engine, playlist, fooTrack, PlayerStatus.PAUSED);
		waitAbit();

		engine.toNext();
		check(engine, playlist, barTrack, PlayerStatus.PLAYING);
		waitAbit();

		engine.stop();
		check(engine, playlist, null, PlayerStatus.STOPPED);
		waitAbit();

		engine.play();
		check(engine, playlist, barTrack, PlayerStatus.PLAYING);
		waitAbit();

		engine.toNext();
		check(engine, playlist, bazTrack, PlayerStatus.PLAYING);
		waitAbit();

		engine.toPrevious();
		check(engine, playlist, barTrack, PlayerStatus.PLAYING);
		waitAbit();

		engine.stop();
		check(engine, playlist, null, PlayerStatus.STOPPED);
		waitAbit();
		
		engine.stopPlayingPlaylist();
		check(engine, null, null, PlayerStatus.STOPPED);
		waitAbit();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void check(BasePlayerEngine engine, Playlist expectedPlaylist, Track expectedTrack,
			PlayerStatus expectedStatus) {
		assertEquals(engine.currentPlaylist(), expectedPlaylist);
		assertEquals(engine.currentTrack(), expectedTrack);
		assertEquals(engine.currentStatus(), expectedStatus);
	}

	private void waitAbit() {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			assumeTrue(e != null);
		}
	}

	private BasePlayerEngine log(BasePlayerEngine engine) {
		return new LoggingPlayerEngine(engine);
	}

	private Playlist playlist() {
		BaseMusicbaseModifing musicbase = new DefaultInMemoryMusicbase();
		Bundle bundle = TestingDataCreator.bundle(musicbase);
		Metadata metadata = Metadata.createNew();

		fooTrack = TestingDataCreator.track(musicbase, bundle, "foo");
		barTrack = TestingDataCreator.track(musicbase, bundle, "bar");
		bazTrack = TestingDataCreator.track(musicbase, bundle, "baz");

		Tracklist tracks = new Tracklist(Arrays.asList(fooTrack, barTrack, bazTrack));
		return new Playlist(bundle, "testing playlist", tracks, 0, metadata);
	}

}

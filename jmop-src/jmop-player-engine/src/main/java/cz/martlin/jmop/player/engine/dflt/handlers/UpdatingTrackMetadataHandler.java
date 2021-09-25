package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackEndedHandler;
import javafx.util.Duration;

public class UpdatingTrackMetadataHandler implements BeforeTrackEndedHandler {

	private final BaseMusicbase musicbase;
	private final int markAsPlayedAfter;

	public UpdatingTrackMetadataHandler(BaseMusicbase musicbase, int markAsPlayedAfter) {
		super();
		this.musicbase = musicbase;
		this.markAsPlayedAfter = markAsPlayedAfter;
	}

	
	@Override
	public void beforeTrackEnded(BasePlayerEngine engine, Track track)  {
		Duration currentDuration = engine.currentDuration();

		// little trick: instead of waiting markAsPlayedAfter seconds and then
		// updating the track metadata, we can simply wait for the moment,
		// when the plaing of the track ends, and then just simply checking
		// whether the plaing was longer than the markAsPlayedAfter,
		// by simply checking the current time of the player.
		// Gimme the Nobel prize!
		if (isMoreThan(currentDuration, markAsPlayedAfter)) {
			reportPlayed(engine, track, currentDuration);
		}
	}

	private void reportPlayed(BasePlayerEngine engine, Track track, Duration currentDuration) {
		track.played(currentDuration);
		musicbase.trackUpdated(track);
		
		Playlist playlist = engine.currentPlaylist();
		playlist.played(currentDuration);
		musicbase.playlistUpdated(playlist);
		
		Bundle bundle = playlist.getBundle();
		bundle.played(currentDuration);
		musicbase.bundleUpdated(bundle);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isMoreThan(Duration duration, int seconds) {
		return duration.toSeconds() > seconds;
	}

}

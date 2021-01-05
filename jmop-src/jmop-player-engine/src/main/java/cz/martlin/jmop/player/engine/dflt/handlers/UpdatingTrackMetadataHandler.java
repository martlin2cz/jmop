package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
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
	public void beforeTrackEnded(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException {
		Duration currentDuration = engine.currentDuration();

		// little trick: instead of waiting markAsPlayedAfter seconds and then
		// updating the track metadata, we can simply wait for the moment,
		// when the plaing of the track ends, and then just simply checking
		// whether the plaing was longer than the markAsPlayedAfter,
		// by simply checking the current time of the player.
		// Gimme the Nobel prize!
		if (isMoreThan(currentDuration, markAsPlayedAfter)) {
			track.played();
			musicbase.trackUpdated(track);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isMoreThan(Duration duration, int seconds) {
		return duration.toSeconds() > seconds;
	}

}

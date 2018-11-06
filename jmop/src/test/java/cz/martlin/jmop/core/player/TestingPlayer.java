package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class TestingPlayer extends SimpleObjectProperty<BasePlayer> implements BasePlayer {

	private final TrackFileFormat playableFormat;
	private Track playing;
	private boolean stopped;
	private boolean paused;
	private boolean over;

	public TestingPlayer(TrackFileFormat playableFormat) {
		this.playableFormat = playableFormat;

		this.stopped = true;
	}

	@Override
	public Duration currentTime() {
		return new Duration(0);
	}

	@Override
	public Track getPlayedTrack() {
		return playing;
	}

	@Override
	public TrackFileFormat getPlayableFormat() {
		return playableFormat;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public boolean isStopped() {
		return stopped;
	}

	@Override
	public boolean isPlayOver() {
		return over;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void startPlaying(Track track) {
		System.out.println("Playing " + track);
		this.playing = track;
		this.stopped = false;
		this.paused = false;
	}

	@Override
	public void stop() {
		this.playing = null;
		System.out.println("Player stopped");

		this.stopped = true;
	}

	@Override
	public void pause() {
		System.out.println("Player paused");

		this.paused = true;
	}

	@Override
	public void resume() {
		System.out.println("Player resumed, plaing again " + playing);

		this.paused = false;
	}

	@Override
	public void seek(Duration to) {
		System.out.println("Seeking to " + DurationUtilities.toHumanString(to));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "TestingPlayer [playing=" + playing + "]";
	}

}

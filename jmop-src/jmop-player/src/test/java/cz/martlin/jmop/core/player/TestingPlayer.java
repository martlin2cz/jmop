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
		System.out.println("Playing " + track); //$NON-NLS-1$
		this.playing = track;
		this.stopped = false;
		this.paused = false;
	}

	@Override
	public void stop() {
		this.playing = null;
		System.out.println("Player stopped"); //$NON-NLS-1$

		this.stopped = true;
	}

	@Override
	public void pause() {
		System.out.println("Player paused"); //$NON-NLS-1$

		this.paused = true;
	}

	@Override
	public void resume() {
		System.out.println("Player resumed, plaing again " + playing); //$NON-NLS-1$

		this.paused = false;
	}

	@Override
	public void seek(Duration to) {
		System.out.println("Seeking to " + DurationUtilities.toHumanString(to)); //$NON-NLS-1$
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "TestingPlayer [playing=" + playing + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}

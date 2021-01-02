package cz.martlin.jmop.player.players;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

public class TestingPlayer extends AbstractPlayer {

	private int seconds = 0;
	
	public TestingPlayer() {
		super();
	}

	@Override
	public Duration currentTime() {
		return DurationUtilities.createDuration(0, 0, seconds);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void doStartPlaying(Track track) {
		System.out.println("# Playing " + track); //$NON-NLS-1$
		seconds = 0;
	}

	@Override
	public void doStopPlaying() {
		System.out.println("# Player stopped"); //$NON-NLS-1$
		seconds = 0;
	}

	@Override
	public void doPausePlaying() {
		System.out.println("# Player paused"); //$NON-NLS-1$
		seconds++;
	}

	@Override
	public void doResumePlaying() {
		System.out.println("# Player resumed, plaing again " + actualTrack()); //$NON-NLS-1$
		seconds++;
	}

	@Override
	public void doSeek(Duration to) {
		System.out.println("# Seeking to " + DurationUtilities.toHumanString(to)); //$NON-NLS-1$
		seconds = (int) to.toSeconds();
	}

	@Override
	protected void doTrackFinished() {
		System.out.println("# Okay, the track finished."); //$NON-NLS-1$
		seconds++;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "TestingPlayer [actualTrack()=" + actualTrack() + ", currentTime()=" + currentTime() + "]";
	}

}

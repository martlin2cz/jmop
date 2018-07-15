package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;

public class TestingPlayer implements AbstractPlayer {

	private Track playing;

	public TestingPlayer() {
	}

	public Track getPlaying() {
		return playing;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void play(Track track) {
		System.out.println("Playing " + track);
		this.playing = track;
	}

	@Override
	public void stop() {
		this.playing = null;
		System.out.println("Player stopped");
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "TestingPlayer [playing=" + playing + "]";
	}

}

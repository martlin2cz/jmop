package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class TestingPlayer implements BasePlayer {

	private Track playing;

	public TestingPlayer() {
	}

	public Track getPlaying() {
		return playing;
	}

	@Override
	public void setHandler(TrackPlayedHandler handler) {
		//ignore
	}


	@Override
	public boolean supports(TrackFileFormat format) {
		return true;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	
	@Override
	public void startPlayling(Track track) {
		System.out.println("Playing " + track);
		this.playing = track;
	}

	@Override
	public void stop() {
		this.playing = null;
		System.out.println("Player stopped");
	}

	@Override
	public void pause() {
		System.out.println("Player paused");
	}

	@Override
	public void resume() {
		System.out.println("Player resumed, plaing again " + playing);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "TestingPlayer [playing=" + playing + "]";
	}

}

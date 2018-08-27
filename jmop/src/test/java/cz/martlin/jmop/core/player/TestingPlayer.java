package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class TestingPlayer implements BasePlayer {

	private final TrackFileFormat playableFormat;
	private Track playing;

	public TestingPlayer(TrackFileFormat playableFormat) {
		this.playableFormat = playableFormat;
	}

	public Track getPlaying() {
		return playing;
	}

	@Override
	public void setHandler(TrackPlayedHandler handler) {
		// ignore
	}

	@Override
	public TrackFileFormat getPlayableFormat() {
		return playableFormat;
	}

	@Override
	public boolean supports(TrackFileFormat format) {
		return true;
	}

	@Override
	public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
		return new SimpleObjectProperty<>(new Duration(0));
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

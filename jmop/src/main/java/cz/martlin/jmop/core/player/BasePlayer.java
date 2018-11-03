package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.TrackFileFormat;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;

public interface BasePlayer extends ObservableValue<BasePlayer> {

	public TrackFileFormat getPlayableFormat();

	/////////////////////////////////////////////////////////////////////////////////////

	public Track getPlayedTrack();

	public boolean isPlayOver();

	/////////////////////////////////////////////////////////////////////////////////////

	public void stop();

	public void startPlaying(Track track) throws JMOPSourceException;

	public boolean isStopped();

	///////////////////////////////////////////////////////////////////////////////////

	public void pause();

	public void resume();

	public boolean isPaused();

	///////////////////////////////////////////////////////////////////////////////////

	public void seek(Duration to);

	public Duration currentTime();

}

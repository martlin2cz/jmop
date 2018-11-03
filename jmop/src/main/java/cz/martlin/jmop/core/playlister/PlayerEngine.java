package cz.martlin.jmop.core.playlister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.PlayerWrapper;
import cz.martlin.jmop.core.preparer.TrackPreparer;
import javafx.util.Duration;

public class PlayerEngine {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final ErrorReporter reporter;
	private final PlaylisterWrapper playlister;
	private final TrackPreparer preparer;
	private final PlayerWrapper player;

	public PlayerEngine(ErrorReporter reporter, PlaylisterWrapper playlister, PlayerWrapper player,
			TrackPreparer preparer) {
		super();
		this.reporter = reporter;
		this.playlister = playlister;
		this.preparer = preparer;
		this.player = player;

		player.setOnTrackPlayed((t) -> onTrackPlayed(t));
	}

	public PlaylisterWrapper getPlaylister() {
		return playlister;
	}

	public PlayerWrapper getPlayer() {
		return player;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(Playlist playlist) {
		LOG.info("Starting to play playlist " + playlist.getName() + " of bundle " + playlist.getBundle().getName());

		playlister.startPlayingPlaylist(this, playlist);
	}

	public void stopPlayingPlaylist(Playlist currentPlaylist) {
		LOG.info("Stopping to play playlist " + currentPlaylist.getName() + " of bundle "
				+ currentPlaylist.getBundle().getName());

		playlister.stopPlayingPlaylist(currentPlaylist);
	}

	public void playNext() throws JMOPSourceException {
		LOG.info("Playing next to play");

		Track track = playlister.playNext();
		preparer.checkAndLoadTrack(track);
		player.startPlaying(track);
	}

	public void play(int index) throws JMOPSourceException {
		LOG.info("Playing " + index + " th");

		Track track = playlister.play(index);
		preparer.checkAndLoadTrack(track);
		player.startPlaying(track);
	}

	public void stop() {
		LOG.info("Stopping playing");
		player.stop();
	}

	public void pause() {
		LOG.info("Pausing");
		player.pause();
	}

	public void resume() {
		LOG.info("Resuming");
		player.resume();
	}

	public void seek(Duration to) {
		LOG.info("Seeking to " + DurationUtilities.toHumanString(to));
		player.seek(to);
	}

	public void toNext() throws JMOPSourceException {
		LOG.info("Playing next");

		Track track = playlister.toNext();
		preparer.checkAndLoadTrack(track);
		player.startPlaying(track);
	}

	public void toPrevious() throws JMOPSourceException {
		LOG.info("Playing previous");

		Track track = playlister.toPrevious();
		preparer.checkAndLoadTrack(track);
		player.startPlaying(track);
	}

	public void add(Track track) {
		LOG.info("Adding track " + track.getTitle() + ", id " + track.getIdentifier());

		playlister.add(track);
	}

	public void clearRemaining() {
		LOG.info("Clearing remaining");

		playlister.clearRemaining();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void onTrackPlayed(Track track) {
		try {
			if (hasNext()) {
				toNext();
			}
			// TODO Park here and wait until has next
		} catch (JMOPSourceException e) {
			reporter.report(e);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}

	private boolean hasNext() {
		return playlister.hasNextProperty().get();
	}

}

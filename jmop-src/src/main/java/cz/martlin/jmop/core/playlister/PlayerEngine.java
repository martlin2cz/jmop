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

/**
 * Player engine is just simply the JMOP core of core. It is responsible for
 * both track playing (player) and also for the choosing of track (playlister).
 * 
 * @author martin
 *
 */
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
	/**
	 * Mark as playing given playist.
	 * 
	 * @param playlist
	 */
	public void startPlayingPlaylist(Playlist playlist) {
		LOG.info("Starting to play playlist " + playlist.getName() + " of bundle " + playlist.getBundle().getName()); //$NON-NLS-1$ //$NON-NLS-2$

		playlister.startPlayingPlaylist(this, playlist);
	}

	/**
	 * Mark as not playing given playlist.
	 * 
	 * @param currentPlaylist
	 */
	public void stopPlayingPlaylist(Playlist currentPlaylist) {
		LOG.info("Stopping to play playlist " + currentPlaylist.getName() + " of bundle " //$NON-NLS-1$ //$NON-NLS-2$
				+ currentPlaylist.getBundle().getName());

		playlister.stopPlayingPlaylist(currentPlaylist);
	}

	/**
	 * Play next track in the queque.
	 * 
	 * @throws JMOPSourceException
	 */
	public void playNext() throws JMOPSourceException {
		LOG.info("Playing next to play"); //$NON-NLS-1$

		Track track = playlister.playNext();
		playChecked(track);
	}

	/**
	 * Play the index-th track in the (current) playlist.
	 * 
	 * @param index
	 * @throws JMOPSourceException
	 */
	public void play(int index) throws JMOPSourceException {
		LOG.info("Playing " + index + " th"); //$NON-NLS-1$ //$NON-NLS-2$

		Track track = playlister.play(index);
		playChecked(track);
	}

	/**
	 * Stop playing.
	 */
	public void stop() {
		LOG.info("Stopping playing"); //$NON-NLS-1$
		player.stop();
	}

	/**
	 * Pause playing.
	 */
	public void pause() {
		LOG.info("Pausing"); //$NON-NLS-1$
		player.pause();
	}

	/**
	 * Resume playing.
	 */
	public void resume() {
		LOG.info("Resuming"); //$NON-NLS-1$
		player.resume();
	}

	/**
	 * Seek to given time.
	 * 
	 * @param to
	 */
	public void seek(Duration to) {
		LOG.info("Seeking to " + DurationUtilities.toHumanString(to)); //$NON-NLS-1$
		player.seek(to);
	}

	/**
	 * Go to (start playing) next track.
	 * 
	 * @throws JMOPSourceException
	 */
	public void toNext() throws JMOPSourceException {
		LOG.info("Playing next"); //$NON-NLS-1$

		Track track = playlister.toNext();
		playChecked(track);
	}

	/**
	 * Go to (start playing) previous track.
	 * 
	 * @throws JMOPSourceException
	 */
	public void toPrevious() throws JMOPSourceException {
		LOG.info("Playing previous"); //$NON-NLS-1$

		Track track = playlister.toPrevious();
		playChecked(track);
	}

	/**
	 * Add given track to the play (probably to the playlist).
	 * 
	 * @param track
	 */
	public void add(Track track) {
		LOG.info("Adding track " + track.getTitle() + ", id " + track.getIdentifier()); //$NON-NLS-1$ //$NON-NLS-2$

		playlister.add(track);
	}

	/**
	 * Remove all the track after the current one.
	 */
	public void clearRemaining() {
		LOG.info("Clearing remaining"); //$NON-NLS-1$

		playlister.clearRemaining();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Handle track have been played. In fact, if has next, play next.
	 * 
	 * @param track
	 */
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

	/**
	 * Has next? Just and shorthand method.
	 * 
	 * @return
	 */
	private boolean hasNext() {
		return playlister.hasNextProperty().get();
	}

	/**
	 * Checks (and if needed, performs so) check of the existence of given track's
	 * files. After that plays it.
	 * 
	 * @param track
	 * @throws JMOPSourceException
	 */
	private void playChecked(Track track) throws JMOPSourceException {
		preparer.checkAndLoadTrack(track, player);
	}

}

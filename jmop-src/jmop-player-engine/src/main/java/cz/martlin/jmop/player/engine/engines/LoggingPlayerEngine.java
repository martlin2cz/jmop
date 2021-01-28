package cz.martlin.jmop.player.engine.engines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

/**
 * Just the engine, which simply logs what all the method calls.
 * 
 * @author martin
 *
 */
public class LoggingPlayerEngine implements BasePlayerEngine {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BasePlayerEngine delegee;
	
	public LoggingPlayerEngine(BasePlayerEngine delegee) {
		super();
		this.delegee = delegee;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void startPlayingPlaylist(Playlist playlist)  {
		LOG.info("Starting to play playlist " + playlist.getName() + " of bundle " + playlist.getBundle().getName()); //$NON-NLS-1$ //$NON-NLS-2$

		delegee.startPlayingPlaylist(playlist);
	}

	@Override
	public void stopPlayingPlaylist()  {
		LOG.info("Stopping to play playlist " + currentPlaylist().getName() + " of bundle " //$NON-NLS-1$ //$NON-NLS-2$
				+ currentPlaylist().getBundle().getName());

		delegee.stopPlayingPlaylist();
	}

	@Override
	public void playlistChanged() {
		LOG.info("The playlist changed");

		delegee.playlistChanged();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Playlist currentPlaylist() {
		return delegee.currentPlaylist();
	}
	
	@Override
	public Track currentTrack() {
		return delegee.currentTrack();
	}
	
	@Override
	public Duration currentDuration() {
		return delegee.currentDuration();
	}
	
	@Override
	public PlayerStatus currentStatus() {
		return delegee.currentStatus();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////


	@Override
	public void play()  {
		LOG.info("Playing current to play"); //$NON-NLS-1$

		delegee.play();
	}

	@Override
	public void play(TrackIndex index)  {
		LOG.info("Playing " + index.getIndex() + " th"); //$NON-NLS-1$ //$NON-NLS-2$

		delegee.play(index);
	}

	@Override
	public void stop()  {
		LOG.info("Stopping playing"); //$NON-NLS-1$
		
		delegee.stop();
	}

	@Override
	public void pause() {
		LOG.info("Pausing"); //$NON-NLS-1$
		
		delegee.pause();
	}

	@Override
	public void resume() {
		LOG.info("Resuming"); //$NON-NLS-1$
		
		delegee.resume();
	}

	@Override
	public void seek(Duration to) {
		LOG.info("Seeking to " + DurationUtilities.toHumanString(to)); //$NON-NLS-1$
		
		delegee.seek(to);
	}

	@Override
	public void toNext()  {
		LOG.info("Playing next"); //$NON-NLS-1$

		delegee.toNext();
	}

	@Override
	public void toPrevious()  {
		LOG.info("Playing previous"); //$NON-NLS-1$

		delegee.toPrevious();
	}

	@Override
	public boolean hasNext() {
		return delegee.hasNext();
	}
	
	@Override
	public boolean hasPrevious() {
		return delegee.hasPrevious();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
}

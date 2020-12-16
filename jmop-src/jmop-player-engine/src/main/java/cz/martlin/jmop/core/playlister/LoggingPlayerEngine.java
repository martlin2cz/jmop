package cz.martlin.jmop.core.playlister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.base.engine.BasePlayerEngine;
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
	public void startPlayingPlaylist(Playlist playlist) {
		LOG.info("Starting to play playlist " + playlist.getName() + " of bundle " + playlist.getBundle().getName()); //$NON-NLS-1$ //$NON-NLS-2$

		delegee.startPlayingPlaylist(playlist);
	}

	@Override
	public void stopPlayingPlaylist(Playlist playlist) {
		LOG.info("Stopping to play playlist " + playlist.getName() + " of bundle " //$NON-NLS-1$ //$NON-NLS-2$
				+ playlist.getBundle().getName());

		delegee.stopPlayingPlaylist(playlist);
	}

	@Override
	public void playlistChanged() {
		LOG.info("The playlist changed");

		delegee.playlistChanged();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////


	@Override
	public void playNext() throws JMOPMusicbaseException {
		LOG.info("Playing next to play"); //$NON-NLS-1$

		delegee.playNext();
	}

	@Override
	public void play(int index) throws JMOPMusicbaseException {
		LOG.info("Playing " + index + " th"); //$NON-NLS-1$ //$NON-NLS-2$

		delegee.play(index);
	}

	@Override
	public void stop() {
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
	public void toNext() throws JMOPMusicbaseException {
		LOG.info("Playing next"); //$NON-NLS-1$

		delegee.toNext();
	}

	@Override
	public void toPrevious() throws JMOPMusicbaseException {
		LOG.info("Playing previous"); //$NON-NLS-1$

		delegee.toPrevious();
	}

	/////////////////////////////////////////////////////////////////////////////////////
}

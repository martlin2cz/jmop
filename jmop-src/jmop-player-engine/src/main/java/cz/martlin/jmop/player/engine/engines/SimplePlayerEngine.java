package cz.martlin.jmop.player.engine.engines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.runtime.PlaylistRuntime;
import cz.martlin.jmop.player.players.BasePlayer;
import javafx.util.Duration;

/**
 * Player engine is just simply the JMOP core of core. It is responsible for
 * both track playing (player) and also for the choosing of track (playlister).
 * 
 * @author martin
 *
 */
public class SimplePlayerEngine implements BasePlayerEngine {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseErrorReporter reporter;
	private final BasePlayer player;
	
	/**
	 * @deprecated the playlist instance gets stored inside of the runtime
	 */
	@Deprecated
	private Playlist currentPlaylist = null;
	private PlaylistRuntime runtime = null;
	
	public SimplePlayerEngine(BaseErrorReporter reporter, BasePlayer player) {
		super();
		this.reporter = reporter;
		this.player = player;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void startPlayingPlaylist(Playlist playlist) {
//		LOG.info("Starting to play playlist " + playlist.getName() + " of bundle " + playlist.getBundle().getName()); //$NON-NLS-1$ //$NON-NLS-2$

		currentPlaylist = playlist;
		runtime = PlaylistRuntime.of(playlist);
	}

	@Override
	public void stopPlayingPlaylist(Playlist playlist) {
//		LOG.info("Stopping to play playlist " + currentPlaylist.getName() + " of bundle " //$NON-NLS-1$ //$NON-NLS-2$
//				+ currentPlaylist.getBundle().getName());

		currentPlaylist = null;
		runtime = null;
	}

	@Override
	public void playlistChanged() {
//		LOG.info("The playlist changed");

		runtime = PlaylistRuntime.of(currentPlaylist);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////


	@Override
	public void play() throws JMOPMusicbaseException {
//		LOG.info("Playing current to play"); //$NON-NLS-1$

		Track track = runtime.current();
		player.startPlaying(track);
	}

	@Override
	public void play(int index) throws JMOPMusicbaseException {
//		LOG.info("Playing " + index + " th"); //$NON-NLS-1$ //$NON-NLS-2$

		Track track = runtime.play(index);
		player.startPlaying(track);
	}

	@Override
	public void stop() {
//		LOG.info("Stopping playing"); //$NON-NLS-1$
		player.stop();
	}

	@Override
	public void pause() {
//		LOG.info("Pausing"); //$NON-NLS-1$
		player.pause();
	}

	@Override
	public void resume() {
//		LOG.info("Resuming"); //$NON-NLS-1$
		player.resume();
	}

	@Override
	public void seek(Duration to) {
//		LOG.info("Seeking to " + DurationUtilities.toHumanString(to)); //$NON-NLS-1$
		player.seek(to);
	}

	@Override
	public void toNext() throws JMOPMusicbaseException {
//		LOG.info("Playing next"); //$NON-NLS-1$

		Track track = runtime.toNext();
		player.startPlaying(track);
	}

	@Override
	public void toPrevious() throws JMOPMusicbaseException {
//		LOG.info("Playing previous"); //$NON-NLS-1$

		Track track = runtime.toPrevious();
		player.startPlaying(track);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Handle track have been played. In fact, if has next, play next.
	 * 
	 * @param track
	 */
	private void onTrackPlayed(Track track) {
		try {
			if (runtime.hasNextToPlay()) {
				toNext();
			}
			// TODO Park here and wait until has next
		} catch (JMOPMusicbaseException e) {
			reporter.report(e);
		} catch (Exception e) {
			reporter.internal(e);
		}
	}
}

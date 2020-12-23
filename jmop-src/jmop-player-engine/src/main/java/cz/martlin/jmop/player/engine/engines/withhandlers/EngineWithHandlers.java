package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.runtime.PlaylistRuntime;
import cz.martlin.jmop.player.players.BasePlayer;
import javafx.util.Duration;

public class EngineWithHandlers 	implements BasePlayerEngine {

		private final BasePlayer player;
		
		private PlaylistRuntime runtime = null;

		private BeforeTrackPlayedHandler before;
		private AfterTrackPlayedHandler after;
		
		public EngineWithHandlers(BasePlayer player) {
			super();
			this.player = player;
		}

		/////////////////////////////////////////////////////////////////////////////////////

		@Override
		public void startPlayingPlaylist(Playlist playlist) {
			runtime = PlaylistRuntime.of(playlist);
		}

		@Override
		public void stopPlayingPlaylist(Playlist playlist) {
			runtime = null;
		}

		@Override
		public void playlistChanged() {
			Playlist currentPlaylist = runtime.getPlaylist();
			runtime = new PlaylistRuntime(currentPlaylist);
		}
		
		/////////////////////////////////////////////////////////////////////////////////////


		@Override
		public void play() throws JMOPMusicbaseException {
			Track track = runtime.current();
			playTrack(track);
		}

		@Override
		public void play(int index) throws JMOPMusicbaseException {
			Track track = runtime.play(index);
			playTrack(track);
		}

		@Override
		public void stop() {
			Track track = runtime.current();
			after.beforeTrackEnded(track);
			player.stop();
		}

		@Override
		public void pause() {
			player.pause();
		}

		@Override
		public void resume() {
			player.resume();
		}

		@Override
		public void seek(Duration to) {
			player.seek(to);
		}

		@Override
		public void toNext() throws JMOPMusicbaseException {
			Track track = runtime.toNext();
			playTrack(track);
		}

		@Override
		public void toPrevious() throws JMOPMusicbaseException {
			Track track = runtime.toPrevious();
			playTrack(track);
		}

		/////////////////////////////////////////////////////////////////////////////////////

		private void playTrack(Track track) throws JMOPMusicbaseException {
			if (player.currentStatus().isPlayingTrack()) {
				after.beforeTrackEnded(track);
				player.stop();
			}
			
			if (before.beforeTrackPlayed(track)) {
				player.startPlaying(track);
			}
		}

		
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
			} catch (JMOPMusicbaseException e) {
				reporter.report(e);
			} catch (Exception e) {
				reporter.internal(e);
			}
		}
}

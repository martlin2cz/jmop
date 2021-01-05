package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.OnPlaylistStartedHandler;

public class PlaylistAndBundleStartedHandler implements OnPlaylistStartedHandler {

	@FunctionalInterface
	public static interface OnBundleStartedHandler {
		public void onBundleStarted(BasePlayerEngine engine, Bundle bundle) throws JMOPMusicbaseException;
	}

	private final OnBundleStartedHandler bundleStarted;
	private final OnPlaylistStartedHandler playlistStarted;

	private Bundle currentBundle;

	public PlaylistAndBundleStartedHandler(OnBundleStartedHandler bundleStarted,
			OnPlaylistStartedHandler playlistStarted) {
		super();
		this.bundleStarted = bundleStarted;
		this.playlistStarted = playlistStarted;
	}

	@Override
	public void onPlaylistStarted(BasePlayerEngine engine, Playlist playlist) throws JMOPMusicbaseException {
		Bundle bundle = playlist.getBundle();

		// since we don't get information about why which bundle gets started (and
		// stopped), we have to simply remember each bundle when its playlist gets
		// started and then when different playlist starts to be played, just check
		// whether we started to play different bundle or we are still in the same one.
		// If yes, report it as a new bundle started to be played, otherwise ignore.
		if (currentBundle != bundle) {
			bundleStarted.onBundleStarted(engine, bundle);

			currentBundle = bundle;
		}

		playlistStarted.onPlaylistStarted(engine, playlist);
	}

}

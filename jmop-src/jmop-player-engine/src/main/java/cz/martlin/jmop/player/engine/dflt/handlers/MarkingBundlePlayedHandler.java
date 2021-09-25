package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.dflt.handlers.PlaylistAndBundleStartedHandler.OnBundleStartedHandler;
import javafx.util.Duration;

public class MarkingBundlePlayedHandler implements OnBundleStartedHandler {
	private final BaseMusicbase musicbase;

	public MarkingBundlePlayedHandler(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}

	@Override
	public void onBundleStarted(BasePlayerEngine engine, Bundle bundle)  {
		bundle.played(Duration.ZERO);
		musicbase.bundleUpdated(bundle);
	}
}

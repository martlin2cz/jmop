package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.engines.withhandlers.AfterTrackPlayedHandler;
import javafx.util.Duration;

public class SavingMetadataHandler implements AfterTrackPlayedHandler {

	@Override
	public void beforeTrackEnded(Track track) {
		//TODO if duration > 10s
		// then #played on metadata
		// and (fire modified on musicbase)?
	}

}

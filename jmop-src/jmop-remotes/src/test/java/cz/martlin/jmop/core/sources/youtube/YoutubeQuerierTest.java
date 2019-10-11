package cz.martlin.jmop.core.sources.youtube;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Metadata;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteQuerier;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteQuerierBaseTest;
import cz.martlin.jmop.core.sources.remote.youtube.YoutubeQuerier;
import javafx.util.Duration;

public class YoutubeQuerierTest extends AbstractRemoteQuerierBaseTest {

	@Override
	protected AbstractRemoteQuerier createQuerier() {

		BaseConfiguration config = new ConstantConfiguration();
		InternetConnectionStatus connection = new InternetConnectionStatus(config);

		return new YoutubeQuerier(config, connection);
	}

	@Override
	protected Bundle createBundle() {
		String name = "EDM?";
		SourceKind kind = SourceKind.YOUTUBE;
		Metadata metadata = Metadata.createNew();
		
		return new Bundle(kind, name, metadata);
	}

	@Override
	protected String createQuery() {
		return "hello";
	}

	@Override
	protected Track createTrack() {
		Bundle bundle = createBundle();
		String identifier = "ih2xubMaZWI";
		
		String title = "OMFG Hello";
		Duration duration = DurationUtilities.createDuration(0, 3, 45);
		String description = "Get the sound out of my head!!! REALLY!";
		Metadata metadata = Metadata.createNew();
		
		return bundle.createTrack(identifier, title, description, duration, metadata);
	}

}

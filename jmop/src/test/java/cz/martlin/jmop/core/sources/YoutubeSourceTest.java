package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;

public class YoutubeSourceTest {

	public static void main(String[] args) throws JMOPSourceException {
		YoutubeSource source = new YoutubeSource();

		final Bundle bundle = new Bundle(SourceKind.YOUTUBE, "house music");
		final String query = "progressive house mix";

		Track current = source.search(bundle, query);

		System.out.println("Track : " + current.getIdentifier() + ", " + current.getTitle() + ", "
				+ DurationUtilities.toHumanString(current.getDuration()));
		for (int i = 0; i < 10; i++) {
			current = source.getNextTrackOf(current);

			System.out.println("Track : " + current.getIdentifier() + ", " + current.getTitle() + ", "
					+ DurationUtilities.toHumanString(current.getDuration()));

			// try {
			// TimeUnit.SECONDS.sleep(10);
			// } catch (InterruptedException eIgnore) {
			// }
		}
	}
}

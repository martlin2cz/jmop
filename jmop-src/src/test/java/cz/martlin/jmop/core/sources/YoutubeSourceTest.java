package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;

public class YoutubeSourceTest {

	public static void main(String[] args) throws JMOPSourceException {
		ConstantConfiguration config = new ConstantConfiguration();
		InternetConnectionStatus connection = new InternetConnectionStatus(config);
		YoutubeSource source = new YoutubeSource(connection);

		final Bundle bundle = new Bundle(SourceKind.YOUTUBE, "house music"); //$NON-NLS-1$
		final String query = "progressive house mix"; //$NON-NLS-1$

		Track current = source.search(bundle, query);

		System.out.println("Track : " + current.getIdentifier() + ", " + current.getTitle() + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ DurationUtilities.toHumanString(current.getDuration()));
		for (int i = 0; i < 10; i++) {
			current = source.getNextTrackOf(current);

			System.out.println("Track : " + current.getIdentifier() + ", " + current.getTitle() + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ DurationUtilities.toHumanString(current.getDuration()));

			// try {
			// TimeUnit.SECONDS.sleep(10);
			// } catch (InterruptedException eIgnore) {
			// }
		}
	}
}

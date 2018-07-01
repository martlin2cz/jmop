package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.core.tracks.Track;

public class YoutubeSourceTest {

	
	public static void main(String[] args) throws JMOPSourceException {
		YoutubeSource source = new YoutubeSource();
		
		final String query = "progressive house mix";
		
		Track current = source.search(query);
		
		System.out.println("Track : " + current.getIdentifier().getIdentifier() + ", " + current.getTitle());
		for (int i = 0; i < 10; i++) {
			current = source.getNextTrackOf(current);
			
			System.out.println("Track : " + current.getIdentifier().getIdentifier() + ", " + current.getTitle());
			
//			try {
//				TimeUnit.SECONDS.sleep(10);
//			} catch (InterruptedException eIgnore) {
//			}
		}
	}
}

package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.stream.StreamSeed;
import javafx.concurrent.Task;

public class PlaylisterTask extends Task<Void> {

	public PlaylisterTask(StreamSeed seed) {
	}

	@Override
	protected Void call() throws Exception {
		Track next = loadNextTrack();
		Track current;
		
		while (!isCancelled()) {
			current = next;
			
			play(current);
			next = loadNextTrack();
		}
		
		return null;
	}



	private void play(Track track) {
		// TODO play track
		
	}
	
	private Track loadNextTrack() {
		// TODO Auto-generated method stub
		return null;
	}

	

}

package cz.martlin.jmop.common.data.misc;

import java.util.List;
import java.util.Random;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

public class ExtendedPlaylistModifier extends PlaylistModifier {

	private Random random;

	public ExtendedPlaylistModifier(Playlist playlist) {
		super(playlist);
		
		this.random = new Random();
	}
	
	public ExtendedPlaylistModifier(Playlist playlist, Random random) {
		super(playlist);
		
		this.random = random;
	}
	
//	public void remove(int start, int end) {
//		// little trick: we allways remove the track
//		// on the start position, since they gets shifted
//		// back to the emptied spot. Thus we only have to remove
//		// exactly (end - start) tracks
//		for (int i = start; i < end; i++) {
//			remove(start);
//		}
//	}
//	
//	public void remove(List<Track> tracks) {
//		for (Track track : tracks) {
//			removeAll(track);
//		}
//	}
	
	public void shuffle(int amount) {
		int count = playlist.getTracks().count();
		
		for (int i = 0; i < amount; i++) {
			int sourceIndex = random.nextInt(count);
			int targetIndex = random.nextInt(count);
			
			if (targetIndex < count) {
				move(sourceIndex, targetIndex);	
			} else {
				moveToEnd(sourceIndex);
			}
			
		}
	}
	
	
	
	
	
}

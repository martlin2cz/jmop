package cz.martlin.jmop.common.data.misc;

import java.util.Random;

import cz.martlin.jmop.common.data.model.Playlist;

/**
 * The extended playlist modifier.
 * Adds some extra, little advanced features.
 * 
 * @author martin
 *
 */
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
	
	/**
	 * Shuffles the playlist. Number specifies how much.
	 * 
	 * @param amount
	 */
	public void shuffle(int amount) {
		int count = playlist.getTracks().count();
		
		for (int i = 0; i < amount; i++) {
			int sourceIndx = random.nextInt(count);
			int targetIndx = random.nextInt(count);
			
			if (targetIndx < count) {
				TrackIndex sourceIndex = TrackIndex.ofIndex(sourceIndx);
				TrackIndex targetIndex = TrackIndex.ofIndex(targetIndx);
				move(sourceIndex, targetIndex);	
			} else {
				TrackIndex sourceIndex = TrackIndex.ofIndex(sourceIndx);
				moveToEnd(sourceIndex);
			}
			
		}
	}
	
	
	
	
	
}

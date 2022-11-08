package cz.martlin.jmop.sourcery.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;

/**
 * An component which loads the playlist from the file and prepares it to be
 * created in the jmop bundle.
 * 
 * @author martin
 *
 */
public interface BasePlaylistImporter {

	/**
	 * Loads the playlist data from the given file.
	 * 
	 * @param file
	 * @param createFiles
	 * @return
	 * @throws IOException
	 */
	PlaylistData load(File file, TrackFileCreationWay createFiles) throws IOException;

	public class PlaylistData {
		private final String name;
		private final List<TrackData> trackDatas;

		public PlaylistData(String name, List<TrackData> trackDatas) {
			super();
			this.name = name;
			this.trackDatas = trackDatas;
		}

		public String getName() {
			return name;
		}

		public List<TrackData> getTracks() {
			return trackDatas;
		}

		@Override
		public String toString() {
			return "PlaylistData [name=" + name + ", trackDatas=" + trackDatas + "]";
		}

	}

}

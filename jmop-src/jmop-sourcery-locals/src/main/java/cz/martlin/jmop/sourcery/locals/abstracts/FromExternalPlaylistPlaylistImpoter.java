package cz.martlin.jmop.sourcery.locals.abstracts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.sourcery.local.BasePlaylistImporter;

/**
 * The playlist importer importing form some external playlist file.
 * 
 * @author martin
 *
 * @param <PT>
 * @param <TT>
 */
public class FromExternalPlaylistPlaylistImpoter<PT, TT> implements BasePlaylistImporter {

	private final BaseExternalPlaylistEncapsulator<PT, TT> encapsulator;

	public FromExternalPlaylistPlaylistImpoter(BaseExternalPlaylistEncapsulator<PT, TT> encapsulator) {
		super();

		this.encapsulator = encapsulator;
	}

	@Override
	public PlaylistData load(File file, TrackFileCreationWay createFiles) throws IOException {

		PT xplaylist = encapsulator.loadPlaylist(file);

		List<TrackData> trackDatas = encapsulator.obtainTracks(xplaylist);

		String name = encapsulator.obtainName(xplaylist);

		return new PlaylistData(name, trackDatas);
	}
}

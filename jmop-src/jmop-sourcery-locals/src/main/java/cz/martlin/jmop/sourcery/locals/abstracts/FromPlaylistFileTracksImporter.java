package cz.martlin.jmop.sourcery.locals.abstracts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;

/**
 * The tracks file importer importin from some playlist file.
 * 
 * @author martin
 *
 * @param <PT>
 * @param <TT>
 */
public class FromPlaylistFileTracksImporter<PT, TT> implements BaseTracksFromFileImporter {

	private final BaseExternalPlaylistEncapsulator<PT, TT> encapsulator;

	public FromPlaylistFileTracksImporter(BaseExternalPlaylistEncapsulator<PT, TT> encapsulator) {
		super();
		this.encapsulator = encapsulator;
	}

	@Override
	public List<TrackData> importTracks(File file) throws IOException {
		PT xplaylist = encapsulator.loadPlaylist(file);
		return encapsulator.obtainTracks(xplaylist);
	}

}

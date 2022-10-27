package cz.martlin.jmop.sourcery.locals.sourceries;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * The simple single track per file importer, which just loads track based on
 * the file name.
 * 
 * @author martin
 *
 */
public class SimpleDirectoryImporter extends AbstractSingleTrackPerFileImporter {

	public SimpleDirectoryImporter() {
		super();
	}

	@Override
	protected TrackData fileToTrack(File file) {
		String title = file.getName();
		String description = null;
		Duration duration = DurationUtilities.createDuration(0, 0, 10);
		URI location = file.toURI();

		return new TrackData(title, description, duration, location, file);
	}

}

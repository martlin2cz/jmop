package cz.martlin.jmop.core.sources.remote.empty;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.sourcery.remote.BaseConverter;

/**
 * Converter doing simply no conversion, or even nothing.
 * 
 * @author martin
 *
 */
public class NoopConverter implements BaseConverter {

	public NoopConverter() {
		super();
	}

	@Override
	public void convert(Track track, File from, File to) {
		System.out.println("Not converting " + track.getTitle());
	}
}

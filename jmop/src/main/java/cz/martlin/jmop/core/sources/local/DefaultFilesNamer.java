package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public class DefaultFilesNamer extends AbstractFilesNamer {

	protected static final String SEPARATOR = "_";
	protected static final String SUFFIX = ".mp3";

	public DefaultFilesNamer(File rootDir) {
		super(rootDir);
	}

	@Override
	protected String dirnameOfBundle(Bundle bundle) {
		String bundleName = bundle.getName();
		String cleanName = clean(bundleName);
		return cleanName;
	}

	@Override
	protected String filenameOfTrack(Track track) {
		String trackName = track.getTitle();
		String id = track.getIdentifier().getIdentifier();

		String cleanName = clean(trackName);
		String fileName = cleanName + SEPARATOR + id + SUFFIX;
		return fileName;
	}

	private String clean(String text) {
		//TODO remove all non-ascii chars
		return text;
	}

}

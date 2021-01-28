package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.converters.CoupleOrNotParser.CoupleOrNot;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

public class TrackConverter extends AbstractJMOPConverter<Track> {
	private static final String NO_TRACK_INDICATOR = "(no track)";
	
	private final CoupleOrNotParser parser;
	private final BundleOrCurrentConverter bundles;

	public TrackConverter(JMOPPlayer jmop) {
		super(jmop);

		this.bundles = new BundleOrCurrentConverter(jmop);
		this.parser = new CoupleOrNotParser();

	}

	@Override
	public Track convert(String value) throws Exception {
		return track(value);
	}

	public Track track(String trackSpecifier) {
		CoupleOrNot couple = parser.parse(trackSpecifier);


		if (couple.hasBoth()) {
			String bundleName = couple.first(USE_CURRENT);
			Bundle bundle = bundles.bundleOrCurrent(bundleName);

			String trackTitle = couple.second(NO_TRACK_INDICATOR);
			return track(bundle, trackTitle);
		}

		if (couple.hasOnlyFirst()) {
			Bundle bundle = bundles.currentBundle();

			String trackName = couple.first(NO_TRACK_INDICATOR);

			return track(bundle, trackName);
		}

		throw new CommandLine.TypeConversionException("Not a track specifier: " + trackSpecifier);
	}

	public Track track(Bundle bundle, String trackTitle) {
		Track track = jmop.musicbase().trackOfTitle(bundle, trackTitle);
		if (track == null) {
			throw new CommandLine.TypeConversionException("Track " + trackTitle + " does not exist");
		}
		return track;
	}
}

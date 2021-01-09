package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;

public class TrackConverter implements ITypeConverter<Track> {
	private final JMOPPlayerAdapter adapter;
	private final BundledItemPathParser parser;
	private final JMOPPlayerFascade fascade;
	
	public TrackConverter(JMOPPlayerFascade fascade, JMOPPlayerAdapter adapter) {
		super();
		this.fascade = fascade;
		this.adapter = adapter;
		this.parser = new BundledItemPathParser(null);
	}

	@Override
	public Track convert(String bundledTrackSpecOrNot) throws Exception {
		BundledItemName bpn = parser.parse(bundledTrackSpecOrNot);
		
		Bundle bundle;
		String trackTitle;
		if (bpn.bundleName != null && bpn.itemName != null) {
			String bundleName = bpn.bundleName;
			bundle = adapter.bundleOfName(bundleName);
	
			trackTitle = bpn.itemName;
		} else {
			bundle = fascade.currentBundle();
			trackTitle = bpn.itemName;
		}
		
		Track track = adapter.trackOfTitle(bundle, trackTitle);
		if (track == null) {
			throw new CommandLine.TypeConversionException("Track " + trackTitle + " does not exist");
		}
		return track;
	}

}

package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import picocli.CommandLine.ITypeConverter;

public class TrackConverter implements ITypeConverter<Track> {
	private final JMOPPlayerAdapter adapter;
	private final BundledItemPathParser parser;
	
	public TrackConverter(JMOPPlayerAdapter adapter) {
		super();
		this.adapter = adapter;
		this.parser = new BundledItemPathParser("TODO"); //TODO FIXMEE
	}

	@Override
	public Track convert(String bundledTrackSpecOrNot) throws Exception {
		BundledItemName bpn = parser.parse(bundledTrackSpecOrNot);
		String bundleName = bpn.bundleName;
		Bundle bundle = adapter.bundleOfName(bundleName);

		String trackTitle = bpn.itemName;
		return adapter.trackOfTitle(bundle, trackTitle);
	}

}

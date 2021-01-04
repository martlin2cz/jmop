package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import picocli.CommandLine.ITypeConverter;

public class PlaylistConverter implements ITypeConverter<Playlist> {

	private final JMOPPlayerAdapter adapter;
	private final BundledItemPathParser parser;
	
	public PlaylistConverter(JMOPPlayerAdapter adapter, BaseDefaultStorageConfig config) {
		super();
		this.adapter = adapter;
		this.parser = new BundledItemPathParser(config.getAllTrackPlaylistName());
	}

	@Override
	public Playlist convert(String bundledPlaylistSpecOrNot) throws Exception {
		BundledItemName bpn = parser.parse(bundledPlaylistSpecOrNot);
		String bundleName = bpn.bundleName;
		Bundle bundle = adapter.bundleOfName(bundleName);

		String playlistName = bpn.itemName;
		return adapter.playlistOfName(bundle, playlistName);
	}


}

package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;

public class PlaylistConverter implements ITypeConverter<Playlist> {
	
	private final JMOPPlayerFascade fascade;
	private final JMOPPlayerAdapter adapter;
	private final BundledItemPathParser parser;
	
	
	
	public PlaylistConverter(JMOPPlayerFascade fascade, JMOPPlayerAdapter adapter, BaseDefaultStorageConfig config) {
		super();
		this.fascade = fascade;
		this.adapter = adapter;
		this.parser = new BundledItemPathParser(config.getAllTrackPlaylistName());
	}

	@Override
	public Playlist convert(String bundledPlaylistSpecOrNot) throws Exception {
		BundledItemName bpn = parser.parse(bundledPlaylistSpecOrNot);



		Bundle bundle;
		String playlistName;
		if (bpn.bundleName != null && bpn.itemName != null) {
			String bundleName = bpn.bundleName;
			bundle = adapter.bundleOfName(bundleName);
	
			playlistName = bpn.itemName;
		} else {
			bundle = fascade.currentBundle();
			playlistName = bpn.itemName;
		}
		
		Playlist playlist = adapter.playlistOfName(bundle, playlistName);
		if (playlist == null) {
			throw new CommandLine.TypeConversionException("Playlist " + playlistName + " does not exist");
		}
		return playlist;
	}


}

package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.dflt.BaseDefaultJMOPConfig;
import picocli.CommandLine;

public class PlaylistConverter extends AbstractJMOPConverter<Playlist> {
	
	private final BundledItemPathParser parser;
	
	
	
	public PlaylistConverter(JMOPPlayer jmop) {
		super(jmop);
		
		BaseDefaultJMOPConfig config = (BaseDefaultJMOPConfig) jmop.config().getConfiguration();
		String allTrackPlaylistName = config.getAllTrackPlaylistName();
		this.parser = new BundledItemPathParser(allTrackPlaylistName);
	}

	@Override
	public Playlist convert(String bundledPlaylistSpecOrNot) throws Exception {
		BundledItemName bpn = parser.parse(bundledPlaylistSpecOrNot);



		Bundle bundle;
		String playlistName;
		if (bpn.bundleName != null && bpn.itemName != null) {
			String bundleName = bpn.bundleName;
			bundle = jmop.musicbase().bundleOfName(bundleName);
	
			playlistName = bpn.itemName;
		} else {
			bundle = jmop.playing().currentBundle();
			playlistName = bpn.itemName;
		}
		
		Playlist playlist = jmop.musicbase().playlistOfName(bundle, playlistName);
		if (playlist == null) {
			throw new CommandLine.TypeConversionException("Playlist " + playlistName + " does not exist");
		}
		return playlist;
	}


}

package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.cli.repl.converters.CoupleOrNotParser.CoupleOrNot;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.dflt.BaseJMOPPlayerConfig;
import picocli.CommandLine;

public class PlaylistConverter extends AbstractJMOPConverter<Playlist> {

	private final CoupleOrNotParser parser;
	private final BundleOrCurrentConverter bundles;

	public PlaylistConverter(JMOPPlayer jmop) {
		super(jmop);

		this.bundles = new BundleOrCurrentConverter(jmop);
		this.parser = new CoupleOrNotParser();

	}

	@Override
	public Playlist convert(String value) throws Exception {
		return playlist(value);
	}

	public Playlist playlist(String playlistSpecifier) {
		CoupleOrNot couple = parser.parse(playlistSpecifier);

		BaseJMOPPlayerConfig config = (BaseJMOPPlayerConfig) jmop.config().getConfiguration();
		String allTrackPlaylistName = config.getAllTracksPlaylistName();

		if (couple.hasBoth()) {
			String bundleName = couple.first(USE_CURRENT);
			Bundle bundle = bundles.bundleOrCurrent(bundleName);

			String playlistName = couple.second(allTrackPlaylistName);
			return playlist(bundle, playlistName);
		}

		if (couple.hasOnlyFirst()) {
			Bundle bundle = bundles.currentBundle();

			String playlistName = couple.first(allTrackPlaylistName);

			return playlist(bundle, playlistName);
		}

		throw new CommandLine.TypeConversionException("Not a playlist specifier: " + playlistSpecifier);
	}

	public Playlist playlist(Bundle bundle, String playlistName) {
		Playlist playlist = jmop.musicbase().playlistOfName(bundle, playlistName);
		if (playlist == null) {
			throw new CommandLine.TypeConversionException("Playlist " + playlistName + " does not exist");
		}
		return playlist;
	}

	public static Playlist convertPlaylist(JMOPPlayer jmop, Bundle bundle, String name) {
		PlaylistConverter converter = new PlaylistConverter(jmop);
		return converter.playlist(bundle, name);
	}

}

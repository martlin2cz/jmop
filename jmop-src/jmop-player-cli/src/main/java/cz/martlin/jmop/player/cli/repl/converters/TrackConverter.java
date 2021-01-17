package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.dflt.BaseDefaultJMOPConfig;
import picocli.CommandLine;

public class TrackConverter extends AbstractJMOPConverter<Track> {
	private final BundledItemPathParser parser;
	
	public TrackConverter(JMOPPlayer jmop) {
		super(jmop);
		this.parser = new BundledItemPathParser(null);
	}

	@Override
	public Track convert(String bundledTrackSpecOrNot) throws Exception {
		BundledItemName bpn = parser.parse(bundledTrackSpecOrNot);
		
		Bundle bundle;
		String trackTitle;
		if (bpn.bundleName != null && bpn.itemName != null) {
			String bundleName = bpn.bundleName;
			bundle = jmop.musicbase().bundleOfName(bundleName);
	
			trackTitle = bpn.itemName;
		} else {
			bundle = jmop.playing().currentBundle();
			trackTitle = bpn.itemName;
		}
		
		Track track = jmop.musicbase().trackOfTitle(bundle, trackTitle);
		if (track == null) {
			throw new CommandLine.TypeConversionException("Track " + trackTitle + " does not exist");
		}
		return track;
	}

	public Track convert(Bundle bundle, String trackTitle) throws JMOPMusicbaseException {
			if (bundle == null && trackTitle == null) {
				return jmop.playing().currentTrack();
			}
			
			if (bundle != null && trackTitle == null) {
				BaseDefaultJMOPConfig config = (BaseDefaultJMOPConfig) jmop.config().getConfiguration();
				trackTitle = config.getAllTrackPlaylistName();
			}
			
			if (bundle != null && trackTitle != null) {
				// okay, we're all set
			}
			
			Track track = jmop.musicbase().trackOfTitle(bundle, trackTitle);
			if (track == null) {
				throw new CommandLine.TypeConversionException("Track " + trackTitle + " does not exist");
			}
			return track;
	}

}

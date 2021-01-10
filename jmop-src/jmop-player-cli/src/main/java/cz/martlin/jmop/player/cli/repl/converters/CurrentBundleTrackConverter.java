package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;
/**
 * @deprecated cannot be used this way. The converter annotation requires 
 * non-parametric c-tor and the global one needs be more general ...
 * @author martin
 *
 */
public class CurrentBundleTrackConverter extends AbstractJMOPConverter<Track> {
	
	public CurrentBundleTrackConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Track convert(String trackTitle) throws Exception {
		Bundle bundle = jmop.playing().currentBundle();

		Track track = jmop.musicbase().trackOfTitle(bundle, trackTitle);
		if (track == null) {
			throw new CommandLine.TypeConversionException("Track " + trackTitle + " does not exist");
		}
		return track;
	}

}

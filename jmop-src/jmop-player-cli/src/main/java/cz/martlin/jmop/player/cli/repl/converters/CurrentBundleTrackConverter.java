package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;
/**
 * @deprecated cannot be used this way. The converter annotation requires 
 * non-parametric c-tor and the global one needs be more general ...
 * @author martin
 *
 */
public class CurrentBundleTrackConverter implements ITypeConverter<Track> {
	private final JMOPPlayerFascade fascade;
	private final JMOPPlayerAdapter adapter;
	
	public CurrentBundleTrackConverter(JMOPPlayerAdapter adapter, JMOPPlayerFascade fascade) {
		super();
		this.adapter = adapter;
		this.fascade = fascade;
	}

	@Override
	public Track convert(String trackTitle) throws Exception {
		Bundle bundle = fascade.currentBundle();

		Track track = adapter.trackOfTitle(bundle, trackTitle);
		if (track == null) {
			throw new CommandLine.TypeConversionException("Track " + trackTitle + " does not exist");
		}
		return track;
	}

}

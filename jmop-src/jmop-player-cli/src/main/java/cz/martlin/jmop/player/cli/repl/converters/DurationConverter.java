package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;
import picocli.CommandLine.ITypeConverter;

/**
 * The converter of duration.
 * 
 * @author martin
 *
 */
public class DurationConverter implements ITypeConverter<Duration> {

	@Override
	public Duration convert(String durationStr) throws Exception {
		return DurationUtilities.parseHumanDuration(durationStr);
	}
}

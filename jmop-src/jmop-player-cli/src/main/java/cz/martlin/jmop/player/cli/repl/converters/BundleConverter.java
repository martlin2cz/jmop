package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

@Deprecated
public class BundleConverter extends AbstractJMOPConverter<Bundle> {
	

	public BundleConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Bundle convert(String bundleNameOrDot) throws Exception {
		Bundle bundle = jmop.musicbase().bundleOfName(bundleNameOrDot);
		if (bundle == null) {
			throw new CommandLine.TypeConversionException("Bundle " + bundleNameOrDot + " does not exist");
		}
		return bundle;
	}

}

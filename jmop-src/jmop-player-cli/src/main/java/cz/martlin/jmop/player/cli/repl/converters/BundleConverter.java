package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

public class BundleConverter extends AbstractJMOPConverter<Bundle> {
	public BundleConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Bundle convert(String bundleNameOrNot) throws Exception {
		Bundle bundle = jmop.musicbase().bundleOfName(bundleNameOrNot);
		if (bundle == null) {
			throw new CommandLine.TypeConversionException("Bundle " + bundleNameOrNot + " does not exist");
		}
		return bundle;
	}

}

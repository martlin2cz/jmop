package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

public class BundleConverter extends AbstractJMOPConverter<Bundle> {

	public BundleConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Bundle convert(String value) throws Exception {
		return bundle(value);
	}

	public Bundle bundle(String bundleName) {
		Bundle bundle = jmop.musicbase().bundleOfName(bundleName);
		if (bundle == null) {
			throw new CommandLine.TypeConversionException("Bundle " + bundleName + " does not exist");
		}
		return bundle;
	}

	public static Bundle convertBundle(JMOPPlayer jmop, String name) {
		BundleConverter converter = new BundleConverter(jmop);
		return converter.bundle(name);
	}

}

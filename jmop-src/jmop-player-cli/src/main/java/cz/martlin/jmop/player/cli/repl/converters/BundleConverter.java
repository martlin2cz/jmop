package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;

public class BundleConverter implements ITypeConverter<Bundle> {
	private final JMOPPlayerAdapter adapter;

	public BundleConverter(JMOPPlayerAdapter adapter) {
		super();
		this.adapter = adapter;
	}

	@Override
	public Bundle convert(String bundleNameOrNot) throws Exception {
		Bundle bundle = adapter.bundleOfName(bundleNameOrNot);
		if (bundle == null) {
			throw new CommandLine.TypeConversionException("Bundle " + bundleNameOrNot + " does not exist");
		}
		return bundle;
	}

}

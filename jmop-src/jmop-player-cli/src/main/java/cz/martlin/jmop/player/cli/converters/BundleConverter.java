package cz.martlin.jmop.player.cli.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.ITypeConverter;

public class BundleConverter implements ITypeConverter<Bundle> {
	private final JMOPPlayerAdapter adapter;

	public BundleConverter(JMOPPlayerAdapter adapter) {
		super();
		this.adapter = adapter;
	}

	@Override
	public Bundle convert(String bundleNameOrNot) throws Exception {
		return adapter.bundleOfName(bundleNameOrNot);
	}

}

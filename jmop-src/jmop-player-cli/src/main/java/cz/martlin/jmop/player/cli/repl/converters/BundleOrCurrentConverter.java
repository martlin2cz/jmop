package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

/**
 * The bundle or current converter.
 * @author martin
 *
 */
public class BundleOrCurrentConverter extends BundleConverter {

	public BundleOrCurrentConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Bundle convert(String value) throws Exception {
		return bundleOrCurrent(value);
	}

	/**
	 * Find bundle of given name (or current if current).
	 * @param bundleNameOrCurrentMarker
	 * @return
	 */
	public Bundle bundleOrCurrent(String bundleNameOrCurrentMarker) {
		if (bundleNameOrCurrentMarker.equals(USE_CURRENT)) {
			return currentBundle();
		} else {
			return bundle(bundleNameOrCurrentMarker);
		}
	}

	public Bundle currentBundle() {
		Bundle bundle = jmop.status().currentBundle();
		if (bundle == null) {
			throw new CommandLine.TypeConversionException("There is no current bundle played.");
		}
		return bundle;
	}

}

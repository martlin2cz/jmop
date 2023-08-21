package cz.martlin.jmop.sourcery.picocli.converters;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import picocli.CommandLine;

/**
 * Just a teporary bundle converter for the sourcery means.
 * It's in fact same as the player one.
 * 
 * @author martin
 *
 */
public class BundleConverter extends AbstractJmopSourceryConverter<Bundle> {

	public BundleConverter() {
		super();
	}

	@Override
	public Bundle convert(String value) throws Exception {
		JMOPSourcery jmop = JMOPSourceryProvider.get().getSourcery();
		
		Bundle bundle = jmop.musicbase().bundleOfName(value);
		if (bundle == null) {
			throw new CommandLine.TypeConversionException("No such bundle: " + value);
		}
		return bundle;
	}

}

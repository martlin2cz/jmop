package cz.martlin.jmop.player.cli.repl.converter;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.converters.AbstractJMOPConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

public class BundleOrCurrentConverter extends AbstractJMOPConverter<Bundle> {

	public BundleOrCurrentConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Bundle convert(String value) throws Exception {
		if (value == null || value.equals(USE_CURRENT)) {
			return pickCurrentBundle();
		} else {
			return pickBundleByName(value);
		}
	}

	private Bundle pickCurrentBundle() {
		Bundle bundle = jmop.playing().currentBundle();
		if (bundle == null) {
			fail("There is no bundle beeing played. Specify it explicitly.");
		}
		return bundle;
	}

	private Bundle pickBundleByName(String name)  {
		Bundle bundle = jmop.musicbase().bundleOfName(name);
		if (bundle == null) {
			fail("The bundle '" + name + "' does not exist.");
		}
		return bundle;
	}

}

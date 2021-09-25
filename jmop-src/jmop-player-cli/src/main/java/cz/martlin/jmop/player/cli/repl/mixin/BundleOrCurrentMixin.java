package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.cli.repl.completions.BundlesCompletionsIterator;
import cz.martlin.jmop.player.cli.repl.converters.AbstractJMOPConverter;
import picocli.CommandLine.Parameters;

public class BundleOrCurrentMixin {

	@Parameters(arity = "0..1", defaultValue = AbstractJMOPConverter.USE_CURRENT, completionCandidates = BundlesCompletionsIterator.class)
	private Bundle bundle;

	public BundleOrCurrentMixin() {
	}

	public Bundle getBundle() {
		return bundle;
	}
}
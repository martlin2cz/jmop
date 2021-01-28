package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.cli.repl.completions.BundlesCompletionsIterator;
import picocli.CommandLine.Parameters;

public class BundleOrNoneMixin {

	@Parameters(arity = "0..1", completionCandidates = BundlesCompletionsIterator.class)
	private Bundle bundle;

	public BundleOrNoneMixin() {
	}

	public Bundle getBundle() {
		return bundle;
	}
}
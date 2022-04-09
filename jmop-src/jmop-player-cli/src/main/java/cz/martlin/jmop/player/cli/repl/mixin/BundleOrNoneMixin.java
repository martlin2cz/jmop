package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.cli.repl.completions.BundlesCompletionsIterator;
import picocli.CommandLine.Parameters;

/**
 * The mixin for the optional bundle (in cases where we really can live without
 * it, i.e. "list tracks in bundle or globally" and so).
 * 
 * @author martin
 *
 */
public class BundleOrNoneMixin {

	@Parameters(arity = "0..1", //
			paramLabel = "BUNDLE", //
			description = "The bundle name", //
			completionCandidates = BundlesCompletionsIterator.class)
	private Bundle bundle;

	public BundleOrNoneMixin() {
	}

	/**
	 * Returns the bundle or null (if not specified).
	 * @return
	 */
	public Bundle getBundle() {
		return bundle;
	}
}
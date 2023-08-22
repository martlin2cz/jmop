package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import picocli.CommandLine.Parameters;

/**
 * The mandatory bundle mixin. Use in all the cases where the bundle is
 * nescessary to be provided (but possibly as a current one).
 * 
 * @author martin
 *
 */
public class BundleOrCurrentMixin {

	@Parameters(arity = "1", paramLabel = "BUNDLE", //
			description = "The bundle name or '.' for the bundle currently beeing played")
	private Bundle bundle;

	public BundleOrCurrentMixin() {
	}

	/**
	 * Returns the bundle (never null).
	 * 
	 * @return
	 */
	public Bundle getBundle() {
		return bundle;
	}
}
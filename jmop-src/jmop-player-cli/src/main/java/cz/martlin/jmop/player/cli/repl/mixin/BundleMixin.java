package cz.martlin.jmop.player.cli.repl.mixin;

import cz.martlin.jmop.common.data.model.Bundle;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command()
public class BundleMixin {

	@Parameters(arity = "1")
	private Bundle bundle;

	public BundleMixin() {
	}

	public Bundle getBundle() {
		return bundle;
	}
}
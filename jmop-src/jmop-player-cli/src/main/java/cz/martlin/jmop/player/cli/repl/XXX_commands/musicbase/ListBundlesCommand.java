package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.XXX_commands.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "bundles")
public class ListBundlesCommand extends AbstractCommand {

	public ListBundlesCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Set<Bundle> bundles = fascade.bundles();
		
		PrintUtil.print("Bundles:");
		for (Bundle bundle : bundles) {
			PrintUtil.printBundleName(bundle);
		}
		PrintUtil.emptyLine();
	}

}

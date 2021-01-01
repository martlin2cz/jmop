package cz.martlin.jmop.player.cli.commands;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "bundles")
public class ListBundlesCommand extends AbstractCommand {

//	public ListBundlesCommand(JMOPPlayerFascade fascade) {
//		super(fascade);
//	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Set<Bundle> bundles = fascade().bundles();
		System.out.println("Bundles:");
		for (Bundle bundle : bundles) {
			System.out.println(bundle.getName());
		}
	}

}

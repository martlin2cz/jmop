package cz.martlin.jmop.player.cli.repl.commands.musicbase;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "bundle")
public class CreateBundleCommand extends AbstractCommand {

	@Parameters(arity = "1", index = "0")
	private String name;
	
	public CreateBundleCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.createNewBundle(name);
	}

}

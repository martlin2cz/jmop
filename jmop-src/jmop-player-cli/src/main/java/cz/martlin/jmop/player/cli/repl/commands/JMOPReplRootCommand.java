package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;

@Command(name = "jmop-cli", //
		subcommands = { //
				CreateCommand.class, //
				RenameCommand.class, //
				MoveCommand.class, //
				RemoveCommand.class, //
		}) //
public class JMOPReplRootCommand extends AbstractCommand {

	public JMOPReplRootCommand(JMOPPlayer jmop) {
		super(jmop);
	}

}

package cz.martlin.jmop.player.cli.repl.command;

import cz.martlin.jmop.player.cli.repl.misc.AbstractJMOPPicocliComponent;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Option;

public abstract class AbstractCommand extends AbstractJMOPPicocliComponent {

	
	public AbstractCommand(JMOPPlayer jmop) {
		super(jmop);
	}

}

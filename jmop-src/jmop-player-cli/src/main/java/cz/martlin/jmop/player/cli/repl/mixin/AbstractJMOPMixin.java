package cz.martlin.jmop.player.cli.repl.mixin;

import picocli.CommandLine.Model.CommandSpec;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Spec;

/**
 * The jmop mixin.
 * 
 * @author martin
 *
 */
public abstract class AbstractJMOPMixin {
	@Spec(Spec.Target.MIXEE) CommandSpec mixee;
	
	protected JMOPPlayer getJMOP() {
		AbstractCommand command =  (AbstractCommand) mixee.userObject();
		return command.getJMOP();
	}
}

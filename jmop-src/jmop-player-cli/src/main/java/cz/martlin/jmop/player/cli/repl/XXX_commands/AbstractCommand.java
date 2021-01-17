package cz.martlin.jmop.player.cli.repl.XXX_commands;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.ParentCommand;

/**
 * 
 * @author martin
 * @deprecated replaced by {@link cz.martlin.jmop.player.cli.repl.command.AbstractCommand}
 *
 */
@Deprecated
public abstract class AbstractCommand implements Runnable {

	protected final JMOPPlayerFascade fascade;

//	@ParentCommand
//	private InteractiveRootCommand parent;

	public AbstractCommand(JMOPPlayerFascade fascade) {
		super();
		this.fascade = fascade;
	}

//	protected JMOPPlayerFascade fascade() {
//		return parent.fascade();
//	}

	@Override
	public void run() {
		try {
			doRun();
		} catch (JMOPMusicbaseException e) {
			System.err.println("JMOP error: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Fatal error: " + e);
			e.printStackTrace();
		}
	}

	protected abstract void doRun() throws JMOPMusicbaseException;

}

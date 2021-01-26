package cz.martlin.jmop.player.cli.repl.command;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

public abstract class AbstractRunnableCommand extends AbstractCommand implements Runnable {

	public AbstractRunnableCommand(JMOPPlayer jmop) {
		super(jmop);
	}
	
	@Override
	public void run() {
		try {
			doRun();
		} catch (Exception e) {
			throw new RuntimeException("Command " + this.toString() + " failed ", e);
		}
	}

	protected abstract void doRun() ;

}

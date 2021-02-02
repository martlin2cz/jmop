package cz.martlin.jmop.player.cli.repl.command;

import cz.martlin.jmop.player.cli.repl.exit.OperationRejectedException;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

public abstract class AbstractRunnableCommand extends AbstractCommand implements Runnable {

	public AbstractRunnableCommand(JMOPPlayer jmop) {
		super(jmop);
	}
	
	@Override
	public void run() {
		try {
			doRun();
		} catch (OperationRejectedException e) {
			throw e;
			
		} catch (Exception e) {
			//throw new RuntimeException("Command " + this.toString() + " failed ", e);
			throw e;
		}
	}

	protected abstract void doRun() ;
	
	

	public void reject(String reason) {
		//System.err.println(reason);
		
		throw new OperationRejectedException(reason);
	}

}

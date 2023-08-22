package cz.martlin.jmop.player.cli.repl.command;

import cz.martlin.jmop.player.cli.repl.exit.OperationRejectedException;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

/**
 * The jmop command, which does some actual job.
 * 
 * @author martin
 *
 */
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
			// throw new RuntimeException("Command " + this.toString() + " failed ", e);
			throw e;
		}
	}

	/**
	 * Does actually the desired action.
	 */
	protected abstract void doRun();

	/**
	 * Raises "the operation is not allowed exception" to terminate the command.
	 * 
	 * @param reason
	 */
	public void reject(String reason) {
		// System.err.println(reason);

		throw new OperationRejectedException(reason);
	}

}

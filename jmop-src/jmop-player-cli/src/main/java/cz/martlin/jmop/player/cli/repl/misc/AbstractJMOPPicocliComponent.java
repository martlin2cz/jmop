package cz.martlin.jmop.player.cli.repl.misc;

import cz.martlin.jmop.player.fascade.JMOPPlayer;

/**
 * The very base abstract JMOP picocli object (command, converter, factory,
 * ...).
 * 
 * @author martin
 *
 */
public abstract class AbstractJMOPPicocliComponent {

	protected final JMOPPlayer jmop;

	public AbstractJMOPPicocliComponent(JMOPPlayer jmop) {
		super();

		this.jmop = jmop;
	}

	public JMOPPlayer getJMOP() {
		return jmop;
	}
}

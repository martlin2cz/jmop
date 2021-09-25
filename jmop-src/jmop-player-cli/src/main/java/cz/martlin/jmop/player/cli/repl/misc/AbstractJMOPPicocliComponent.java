package cz.martlin.jmop.player.cli.repl.misc;

import cz.martlin.jmop.player.fascade.JMOPPlayer;

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

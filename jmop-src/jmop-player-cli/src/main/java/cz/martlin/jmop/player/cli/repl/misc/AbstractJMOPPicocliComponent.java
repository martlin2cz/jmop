package cz.martlin.jmop.player.cli.repl.misc;

import cz.martlin.jmop.player.fascade.JMOPPlayer;

public class AbstractJMOPPicocliComponent {
	protected final JMOPPlayer jmop;

	public AbstractJMOPPicocliComponent(JMOPPlayer jmop) {
		super();
		
		this.jmop = jmop;
	}
}

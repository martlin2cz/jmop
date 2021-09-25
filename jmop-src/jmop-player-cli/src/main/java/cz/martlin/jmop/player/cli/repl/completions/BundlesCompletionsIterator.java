package cz.martlin.jmop.player.cli.repl.completions;

import java.util.Iterator;

import cz.martlin.jmop.player.cli.repl.misc.AbstractJMOPPicocliComponent;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

// Seems to be not working for some reason ... :-(
public class BundlesCompletionsIterator extends AbstractJMOPPicocliComponent implements Iterable<String> {

	public BundlesCompletionsIterator(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Iterator<String> iterator() {
		return jmop.musicbase().bundles().stream() //
				.map(b -> b.getName()) //
				.iterator(); //
	}

}

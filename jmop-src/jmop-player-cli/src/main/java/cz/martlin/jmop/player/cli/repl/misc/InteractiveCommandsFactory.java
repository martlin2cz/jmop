package cz.martlin.jmop.player.cli.repl.misc;

import java.lang.reflect.Constructor;

import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

public class InteractiveCommandsFactory implements IFactory {

	private final JMOPPlayer jmop;

	public InteractiveCommandsFactory(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
	}

	@Override
	public <K> K create(Class<K> clazz) throws Exception {
		try {
			return doCreate(clazz); // custom factory lookup or instantiation
		} catch (Exception e) {
			return CommandLine.defaultFactory().create(clazz); // fallback if missing
		}
	}

	private <K> K doCreate(Class<K> clazz) throws UnsupportedOperationException {

		try {
			Constructor<K> ctor = clazz.getConstructor(JMOPPlayer.class);
			return ctor.newInstance(jmop);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Could not create command:" + clazz, e);
		}
	}

}

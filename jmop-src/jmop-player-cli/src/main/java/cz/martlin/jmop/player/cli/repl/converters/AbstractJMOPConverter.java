package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.player.cli.repl.misc.AbstractJMOPPicocliComponent;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.ITypeConverter;

public abstract class AbstractJMOPConverter<T> extends AbstractJMOPPicocliComponent implements ITypeConverter<T> {

	public AbstractJMOPConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public abstract T convert(String value) throws Exception;
}
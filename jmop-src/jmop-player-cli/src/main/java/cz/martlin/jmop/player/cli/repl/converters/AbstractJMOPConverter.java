package cz.martlin.jmop.player.cli.repl.converters;

import cz.martlin.jmop.player.cli.repl.misc.AbstractJMOPPicocliComponent;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.ParameterException;

/**
 * The abstract arguments converter.
 * 
 * @author martin
 *
 * @param <T>
 */
public abstract class AbstractJMOPConverter<T> extends AbstractJMOPPicocliComponent implements ITypeConverter<T> {

	public static final String USE_CURRENT = ".";

	public AbstractJMOPConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public abstract T convert(String value) throws Exception;

	/**
	 * Raise the conversion failed exception.
	 * @param message
	 * @throws ParameterException
	 */
	protected void fail(String message) throws ParameterException {
		throw new CommandLine.TypeConversionException(message);
	}
}
package cz.martlin.jmop.core.misc.ops;

import java.util.function.Function;

/**
 * Like {@link Function} but with exception.
 * 
 * @author martin
 *
 * @param <InT>
 * @param <OuT>
 */
@FunctionalInterface
public interface FunctionWithException<InT, OuT> {
	public OuT run(InT input) throws Exception;
}

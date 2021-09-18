package cz.martlin.jmop.core.misc.ops;

@FunctionalInterface
public interface FunctionWithException<InT, OuT> {
	public OuT run(InT input) throws Exception;
}

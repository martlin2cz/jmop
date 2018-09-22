package cz.martlin.jmop.core.preparer.operations.base;

public interface BaseOperation<IT, OT> {
	public OT run(IT input, OperationChangeListener handler);
}
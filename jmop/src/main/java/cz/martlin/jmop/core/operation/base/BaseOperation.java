package cz.martlin.jmop.core.operation.base;

public interface BaseOperation<IT, OT> {
	public OT run(IT input, OperationChangeListener handler);
}
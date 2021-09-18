package cz.martlin.jmop.core.misc.ops;

public interface BaseOperation<InT, OutT> {
	public String getName();
	
	public String getInputDataAsString();
	
	public OutT run(BaseProgressListener listener) throws Exception;
	
}

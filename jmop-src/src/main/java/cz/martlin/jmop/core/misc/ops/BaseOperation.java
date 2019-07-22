package cz.martlin.jmop.core.misc.ops;

public interface BaseOperation {
	public String getName();
	
	public String getInputDataAsString();
	
	public void run() throws Exception;
	
	//TODO oncomplete?
}

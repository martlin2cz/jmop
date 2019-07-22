package cz.martlin.jmop.core.misc.ops;

/**
 * The short operation. Its implementation is just simple - it simply runs the specified action.
 * @author martin
 *
 */
public class ShortOperation implements BaseOperation {

	private final String name;
	private final String data;
	private final RunnableWithException run;

	public ShortOperation(String name, String data, RunnableWithException run) {
		super();
		this.name = name;
		this.data = data;
		this.run = run;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getInputDataAsString() {
		return data;
	}

	@Override
	public void run() throws Exception {
		run.run();
	}

}

package cz.martlin.jmop.core.misc;

public class SimpleErrorReporter implements BaseErrorReporter {

	@Override
	public void report(Exception e) {
		System.err.println(e);
	}

	@Override
	public void internal(Exception e) {
		System.err.println(e);
	}

}

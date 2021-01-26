package cz.martlin.jmop.core.misc;

public class SimpleErrorReporter implements BaseErrorReporter {

	@Override
	public void report(String reason, Exception e) {
		System.err.println(reason + ":" + e);
	}

	@Override
	public void internal(Exception e) {
		System.err.println(e);
	}

}

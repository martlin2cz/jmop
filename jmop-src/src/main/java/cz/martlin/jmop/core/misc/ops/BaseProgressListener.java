package cz.martlin.jmop.core.misc.ops;

public interface BaseProgressListener {
	public static final double THE_100_PERCENT = 100.0;
	
	public void reportProgressChanged(double progress);
}

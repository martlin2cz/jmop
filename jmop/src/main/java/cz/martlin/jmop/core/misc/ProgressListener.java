package cz.martlin.jmop.core.misc;

@FunctionalInterface
public interface ProgressListener {
	public static final double THE_100_PERCENT = 100.0;

	public void progressChanged(double percentage);
}

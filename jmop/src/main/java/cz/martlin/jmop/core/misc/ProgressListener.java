package cz.martlin.jmop.core.misc;

@FunctionalInterface
public interface ProgressListener {
	public void progressChanged(double percentage);
}

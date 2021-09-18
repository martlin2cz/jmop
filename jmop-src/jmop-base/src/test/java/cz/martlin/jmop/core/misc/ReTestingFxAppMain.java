package cz.martlin.jmop.core.misc;

/**
 * Workaround, see https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
 * @author martin
 *
 */
public class ReTestingFxAppMain {
	public static void main(String[] args) {
		TestingFXApplication.main(args);
	}
}

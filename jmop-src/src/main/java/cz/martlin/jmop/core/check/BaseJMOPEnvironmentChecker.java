package cz.martlin.jmop.core.check;

/**
 * The environment checker does some system-specific and environment-specific
 * check and validations to determine whether the system environment is beeing
 * set up properly. For instance if required programs or libraries are
 * installed.
 * 
 * @author martin
 *
 */
public interface BaseJMOPEnvironmentChecker {
	/**
	 * Performs the check. Returns null, if there is no failure, and non-null
	 * string (containing the error message) if something is not allright.
	 * 
	 * @return error message or null if no such
	 */
	public String doCheck();

}
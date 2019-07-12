package cz.martlin.jmop.gui.local;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The local messages. Holds reference to current locale, uses default if not
 * changed.
 * 
 * @author martin
 *
 */
public class Msg {
	private static final Logger LOG = LoggerFactory.getLogger(Msg.class);
	private static final String BUNDLE_NAME = "cz.martlin.jmop.gui.local.messages"; //$NON-NLS-1$

	private static Locale locale = Locale.getDefault();
	private static ResourceBundle resourceBundle;

	private Msg() {
	}

	/**
	 * Performs the locale load. Logs and rethrows exception if fails.
	 * 
	 * @return
	 */
	private static ResourceBundle loadResourceBundle() {
		try {
			LOG.info("Loading resource in locale " + locale); //$NON-NLS-1$
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			Objects.requireNonNull(bundle);
			return bundle;
		} catch (Exception e) {
			LOG.error("Cannot load resources in locale " + locale, e); //$NON-NLS-1$
			throw e;
		}
	}

	/**
	 * Sets the locale of the resources. Call this before any resources are
	 * used!
	 * 
	 * @param locale
	 */
	public static void setLocale(Locale locale) {
		Msg.locale = locale;
	}

	/**
	 * Returns the resource bundle.
	 * 
	 * @return
	 */
	public static ResourceBundle getResourceBundle() {
		if (resourceBundle == null) {
			resourceBundle = loadResourceBundle();
		}
		return resourceBundle;
	}

	/**
	 * Gets the textual resource by given resource key.
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		try {
			return getResourceBundle().getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

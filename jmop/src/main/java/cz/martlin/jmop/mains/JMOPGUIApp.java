package cz.martlin.jmop.mains;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.CommandlineData;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.builder.BaseJMOPBuilder;
import cz.martlin.jmop.core.wrappers.builder.DefaultJMOPPlayerBuilder;
import cz.martlin.jmop.gui.local.Msg;
import javafx.application.Application;

public class JMOPGUIApp {
	private static final Logger LOG = LoggerFactory.getLogger(JMOPGUIApp.class);
	public static final String VERSION = "1.0"; //$NON-NLS-1$

	private static JMOPPlayer jmop;

	public static void main(String[] args) {
		CommandlineData data = extracCommandLineData(args);
		if (data == null) {
			return;
		}

		if (data.isHelp()) {
			printHelpAndEnd();
			return;
		}

		if (data.isVersion()) {
			printVersionAndEnd();
			return;
		}

		preprocesCommandLineData(data);
		prepareJMOPAndRunGUI(data);
	}

	private static void preprocesCommandLineData(CommandlineData data) {
		String language = data.getLanguage();
		if (language != null) {
			Locale locale = new Locale(language);
			Msg.setLocale(locale);
		}
	}

	private static CommandlineData extracCommandLineData(String[] args) {
		CommandLineDataParser parser = new CommandLineDataParser();
		try {
			return parser.extractCommandLineData(args);
		} catch (Exception e) {
			System.err.println("Cannot extract command line data: " + e); //$NON-NLS-1$
			LOG.error("Cannot extract command line data", e); //$NON-NLS-1$
			return null;
		}
	}

	private static void prepareJMOPAndRunGUI(CommandlineData data) {
		try {
			BaseJMOPBuilder builder = new DefaultJMOPPlayerBuilder();
			jmop = builder.create(data);
		} catch (Exception e) {
			System.err.println("Cannot prepare JMOP core: " + e); //$NON-NLS-1$
			LOG.error("Cannot prepare JMOP core", e); //$NON-NLS-1$
		}

		try {
			Application.launch(JMOPMainGUIApplication.class);
		} catch (Exception e) {
			System.err.println("Cannot start GUI:" + e); //$NON-NLS-1$
			LOG.error("Cannot start GUI", e); //$NON-NLS-1$
		}
	}

	private static void printVersionAndEnd() {
		System.out.println("JMOP " + VERSION); //$NON-NLS-1$
		System.exit(0);
	}

	private static void printHelpAndEnd() {
		System.out.println("JMOP --help | -h | --version | -v"); //$NON-NLS-1$
		System.out.println("JMOP [-lang LANG] [-dir DIR] [-play BUNDLE_NAME [PLAYLIST_NAME]]"); //$NON-NLS-1$
		System.out.println("Where: "); //$NON-NLS-1$
		System.out.println("--help | -h     prints this help and quits"); //$NON-NLS-1$
		System.out.println("--version | -v  prints version and quits"); //$NON-NLS-1$
		System.out.println("-lang LANG      overrides system language for GUI (supported: en/cz)"); //$NON-NLS-1$
		System.out.println("-dir DIR        root directory where all JMOP stuff (bundles, config) is stored"); //$NON-NLS-1$
		System.out.println("-play ...       starts playing immediatelly:"); //$NON-NLS-1$
		System.out.println("-play B_N       - all tracks from given bundle"); //$NON-NLS-1$
		System.out.println("-play B_N P_N   - given playlist in given bundle"); //$NON-NLS-1$
		System.exit(0);
	}

	public static JMOPPlayer getJmop() {
		return jmop;
	}

}

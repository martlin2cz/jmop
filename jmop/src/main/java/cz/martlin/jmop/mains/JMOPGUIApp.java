package cz.martlin.jmop.mains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.config.CommandlineData;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.builder.BaseJMOPBuilder;
import cz.martlin.jmop.core.wrappers.builder.DefaultJMOPPlayerBuilder;
import javafx.application.Application;

public class JMOPGUIApp {
	private static final Logger LOG = LoggerFactory.getLogger(JMOPGUIApp.class);
	private static final String VERSION = "0.4";
	
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

		prepareJMOPAndRunGUI(data);
	}

	private static CommandlineData extracCommandLineData(String[] args) {
		CommandLineDataParser parser = new CommandLineDataParser();
		try {
			return parser.extractCommandLineData(args);
		} catch (Exception e) {
			System.err.println("Cannot extract command line data: " + e);
			LOG.error("Cannot extract command line data", e);
			return null;
		}
	}

	private static void prepareJMOPAndRunGUI(CommandlineData data) {
		try {
			BaseJMOPBuilder builder = new DefaultJMOPPlayerBuilder();
			jmop = builder.create(data);
		} catch (Exception e) {
			System.err.println("Cannot prepare JMOP core: " + e);
			LOG.error("Cannot prepare JMOP core", e);
		}

		try {
			Application.launch(JMOPMainGUIApplication.class);
		} catch (Exception e) {
			System.err.println("Cannot start GUI:" + e);
			LOG.error("Cannot start GUI", e);
		}
	}

	private static void printVersionAndEnd() {
		System.out.println("JMOP "+ VERSION);
		System.exit(0);
	}

	private static void printHelpAndEnd() {
		System.out.println("JMOP --help | -h | --version | -v");
		System.out.println("JMOP [-lang LANG] [-dir DIR] [-play BUNDLE_NAME [PLAYLIST_NAME]]");
		System.out.println("Where: ");
		System.out.println("--help | -h     prints this help and quits");
		System.out.println("--version | -v  prints version and quits");
		System.out.println("-lang LANG      overrides system language for GUI (supported: en/cz)");
		System.out.println("-dir DIR        root directory where all JMOP stuff (bundles, config) is stored");
		System.out.println("-play ...       starts playing immediatelly:");
		System.out.println("-play B_N       - all tracks from given bundle");
		System.out.println("-play B_N P_N   - given playlist in given bundle");
		System.exit(0);
	}

	public static JMOPPlayer getJmop() {
		return jmop;
	}

}

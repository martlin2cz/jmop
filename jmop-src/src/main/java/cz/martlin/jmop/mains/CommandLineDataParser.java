package cz.martlin.jmop.mains;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import cz.martlin.jmop.core.data.CommandlineData;

public class CommandLineDataParser {

	public CommandLineDataParser() {
	}

	public CommandlineData extractCommandLineData(String[] args) {
		CommandlineData data = new CommandlineData();
		Queue<String> remainingArgs = new LinkedList<>(Arrays.asList(args));

		setDefaults(data);
		processArgs(remainingArgs, data);

		return data;
	}
	///////////////////////////////////////////////////////////////////////////

	private void setDefaults(CommandlineData data) {
		setDefaultRootDir(data);
	}

	private void setDefaultRootDir(CommandlineData data) {
		String path = System.getProperty("user.dir"); //$NON-NLS-1$
		File root = new File(path);
		data.setRoot(root);
	}

	///////////////////////////////////////////////////////////////////////////

	private void processArgs(Queue<String> remainingArgs, CommandlineData data) {
		while (!remainingArgs.isEmpty()) {
			String next = remainingArgs.poll();

			processArg(next, remainingArgs, data);
		}
	}

	private void processArg(String arg, Queue<String> remainingArgs, CommandlineData data) {
		switch (arg) {
		case "--help": //$NON-NLS-1$
		case "-h": //$NON-NLS-1$
			processHelp(data);
			break;
		case "--version": //$NON-NLS-1$
		case "-v": //$NON-NLS-1$
			processVersion(data);
			break;
		case "-lang": //$NON-NLS-1$
			processLang(remainingArgs, data);
			break;
		case "-dir": //$NON-NLS-1$
			processDirectory(remainingArgs, data);
			break;
		case "-play": //$NON-NLS-1$
			processPlay(remainingArgs, data);
			break;
		default:
			throw new IllegalArgumentException("Invalid argument: " + arg); //$NON-NLS-1$
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	private void processHelp(CommandlineData data) {
		data.setHelp(true);
	}

	private void processVersion(CommandlineData data) {
		data.setVersion(true);
	}

	private void processLang(Queue<String> remainingArgs, CommandlineData data) {
		String lang = getNextOrFail(remainingArgs);
		data.setLanguage(lang);
	}

	private void processDirectory(Queue<String> remainingArgs, CommandlineData data) {
		String dirPath = getNextOrFail(remainingArgs);
		File root = new File(dirPath);
		data.setRoot(root);
	}

	private void processPlay(Queue<String> remainingArgs, CommandlineData data) {
		String bundle = getNextOrFail(remainingArgs);
		data.setBundleToPlayName(bundle);

		if (!remainingArgs.isEmpty()) {
			String playlist = getNextOrFail(remainingArgs);
			data.setPlaylistToPlayName(playlist);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private static String getNextOrFail(Queue<String> remainingArgs) {
		if (remainingArgs.isEmpty()) {
			throw new IllegalArgumentException("Required at least one more argument"); //$NON-NLS-1$
		} else {
			return remainingArgs.poll();
		}
	}
}

package cz.martlin.jmop.mains;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.JMOPPlayerBuilder;
import javafx.application.Application;

public class JMOPGUIApp {
	private static JMOPPlayer jmop;

	public static void main(String[] args) {

		Playlist playlistOrNull = null;

		File root;
		if (args.length > 0) {
			String path = args[0];
			root = new File(path);
		} else {
			String path = System.getProperty("user.dir");
			root = new File(path);
		}

		try {
			jmop = JMOPPlayerBuilder.create(null, root, playlistOrNull);
		} catch (IOException e) {
			System.err.println("Cannot prepare JMOP core");
			e.printStackTrace();
		}

		try {
			Application.launch(JMOPMainGUIApplication.class);
		} catch (Exception e) {
			System.err.println("Cannot start GUI");
			e.printStackTrace();
		}
	}
	
	public static JMOPPlayer getJmop() {
		return jmop;
	}

}

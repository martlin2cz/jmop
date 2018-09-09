package cz.martlin.jmop.mains;

import java.io.File;

import cz.martlin.jmop.core.config.CommandlineData;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.builder.BaseJMOPBuilder;
import cz.martlin.jmop.core.wrappers.builder.DefaultJMOPPlayerBuilder;
import javafx.application.Application;

public class JMOPGUIApp {
	private static JMOPPlayer jmop;

	public static void main(String[] args) {

		CommandlineData data = new CommandlineData();
		if (args.length > 0) {
			String path = args[0];
			File root = new File(path);
			data.setRoot(root);
		} else {
			String path = System.getProperty("user.dir");
			File root = new File(path);
			data.setRoot(root);
		}

		try {
			BaseJMOPBuilder builder = new DefaultJMOPPlayerBuilder();
			jmop = builder.create(data);
		} catch (Exception e) {
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

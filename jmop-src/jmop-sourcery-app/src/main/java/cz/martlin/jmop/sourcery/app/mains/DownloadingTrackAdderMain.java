package cz.martlin.jmop.sourcery.app.mains;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
import cz.martlin.jmop.player.fascade.dflt.BaseDefaultJMOPConfig;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;
import cz.martlin.jmop.sourcery.app.config.ConstantRemotesConfiguration;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryBuilder;
import cz.martlin.jmop.sourcery.picocli.commands.RemoteCommand;

/**
 * @deprecated replaced by {@link SourceryPicocliMain} and {@link RemoteCommand}
 * @author martin
 *
 */
@Deprecated
public class DownloadingTrackAdderMain {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: ROOT BUNDLE_NAME QUERY");
			System.exit(1);
		}
		
		File root = new File(args[0]);
		JMOPSourcery jmop = createJMOP(root);
//		JMOPSourcery jmop = JMOPSourceryBuilder.createTesting(root); // TODO do not use the testing one
		jmop.config().load();
		
		String bundleName = args[1];
		Bundle bundle = jmop.musicbase().bundleOfName(bundleName);
		if (bundle == null) {
			System.err.println("There is no such bundle");
			System.exit(2);
		}

		String query = args[2];

		boolean download = true;
		try {
			Track track = jmop.youtube().add(bundle, query, download);
			System.out.println("The " + track.toHumanString() + " succesfully added to bundle " + bundle.getName());
		} catch (JMOPSourceryException e) {
			e.printStackTrace();
			System.exit(3);
		}
		
		jmop.config().terminate();
	}

	//TODO move to SourceriesMainsUtil
	protected static JMOPSourcery createJMOP(File root) {
		BaseErrorReporter reporter = new SimpleErrorReporter();
		BaseProgressListener listener = new PrintingListener(System.out);
		
		BaseRemotesConfiguration config = new ConstantRemotesConfiguration();
		BaseDefaultJMOPConfig musicbaseConfig = new ConstantDefaultFascadeConfig();
		
		return JMOPSourceryBuilder.create(root, reporter, config, listener, musicbaseConfig);
	}

}

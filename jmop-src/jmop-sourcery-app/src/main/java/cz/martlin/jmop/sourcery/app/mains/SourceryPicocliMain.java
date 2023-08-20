package cz.martlin.jmop.sourcery.app.mains;

import java.io.File;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.impl.DefaultBHttpClientConnectionFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.player.cli.repl.converters.BundleConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.dflt.BaseDefaultJMOPConfig;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.JavaFXMediaPlayer;
import cz.martlin.jmop.sourcery.app.config.ConstantRemotesConfiguration;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryBuilder;
import cz.martlin.jmop.sourcery.picocli.commands.SourceryRootCommand;
import cz.martlin.jmop.sourcery.picocli.misc.Service;
import cz.martlin.jmop.sourcery.picocli.misc.ServiceConverter;
import cz.martlin.jmop.sourcery.picocli.misc.SourceryCommandsFactory;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

public class SourceryPicocliMain {

	public static void main(String[] args) {
		File root = new File("/tmp/jmop-v1.2"); //FIXME
		Entry<JMOPSourcery, JMOPPlayer> jmop = createJMOP(root);
		JMOPPlayer jmopPlayer = jmop.getValue();
		JMOPSourcery jmopSourcery = jmop.getKey();
			
		CommandLine cl = createCommandLine(jmopPlayer, jmopSourcery);
		
		int exitCode = cl.execute(args);
		System.exit(exitCode);
	}

	private static CommandLine createCommandLine(JMOPPlayer jmopPlayer, JMOPSourcery jmopSourcery) {
		SourceryRootCommand command = new SourceryRootCommand();
		
		IFactory delegeeFactory = CommandLine.defaultFactory();
		IFactory factory = new SourceryCommandsFactory(delegeeFactory, jmopPlayer, jmopSourcery);
		
		CommandLine cl = new CommandLine(command, factory);
		cl.registerConverter(Bundle.class, new BundleConverter(jmopPlayer));
		//TODO playlist converter in here
		cl.setCaseInsensitiveEnumValuesAllowed(true);
		return cl;
	}

	//TODO move to SourceriesMainsUtil
	protected static Entry<JMOPSourcery, JMOPPlayer> createJMOP(File root) {
		BaseErrorReporter reporter = new SimpleErrorReporter();
		BaseProgressListener listener = new PrintingListener(System.out);
		
		BaseRemotesConfiguration remotesConfig = new ConstantRemotesConfiguration();
		BaseDefaultJMOPConfig playersConfig = new ConstantDefaultFascadeConfig();
		
		JMOPSourcery jmopSourcery = JMOPSourceryBuilder.create(root, reporter, remotesConfig, listener, playersConfig);
		jmopSourcery.config().load();
		
		BasePlayer player = new JavaFXMediaPlayer();
		JMOPPlayer jmopPlayer = DefaultJMOPPlayerBuilder.create(root, player, playersConfig, reporter);
		jmopPlayer.config().load();
		
		return new AbstractMap.SimpleEntry<>(jmopSourcery, jmopPlayer);
	}
}

package cz.martlin.jmop.player.cli.repl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import org.jline.console.SystemRegistry;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.keymap.KeyMap;
import org.jline.reader.Binding;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.Parser;
import org.jline.reader.Reference;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;
import picocli.shell.jline3.PicocliCommands;
import picocli.shell.jline3.PicocliCommands.PicocliCommandsFactory;

public abstract class AbstractRepl {

	public AbstractRepl() {
		super();
	}

	protected CommandLine createStandaloneCommandline() {
		Object commands = createRootCommand();

		IFactory customFactory = createCommandsFactory();
		PicocliCommandsFactory defaultFactory = new PicocliCommandsFactory(customFactory);

		CommandLine cmd = new CommandLine(commands, defaultFactory);
		initializeCommandLine(cmd);
		return cmd;
	}
	
	public void runREPL() throws Exception {
		try {
			Supplier<Path> workDir = () -> Paths.get(System.getProperty("user.dir"));
			Builtins builtins = new Builtins(workDir, null, null);

			Object commands = createRootCommand();

			IFactory customFactory = createCommandsFactory();
			PicocliCommandsFactory defaultFactory = new PicocliCommandsFactory(customFactory);

			CommandLine cmd = new CommandLine(commands, defaultFactory);
			initializeCommandLine(cmd);
			PicocliCommands picocliCommands = new PicocliCommands(cmd);

			Parser parser = new DefaultParser();
			try (Terminal terminal = TerminalBuilder.builder().build()) {
				SystemRegistry systemRegistry = new SystemRegistryImpl(parser, terminal, workDir, null);
				systemRegistry.setCommandRegistries(builtins, picocliCommands);
				systemRegistry.register("help", picocliCommands);

				LineReader reader = LineReaderBuilder.builder() //
						.terminal(terminal) //
						.completer(systemRegistry.completer()) //
						.parser(parser) //
						.variable(LineReader.LIST_MAX, 50) //
						.build(); //

				builtins.setLineReader(reader);
				defaultFactory.setTerminal(terminal);

				KeyMap<Binding> keyMap = reader.getKeyMaps().get("main");
				keyMap.bind(new Reference("tailtip-toggle"), KeyMap.alt("s"));

				// start the shell and process input until the user quits with Ctrl-D
				String prompt = createPropmt();
				String line;
				while (true) {
					try {
						systemRegistry.cleanUp();
						line = reader.readLine(prompt, null, (MaskingCallback) null, null);
						systemRegistry.execute(line);
					} catch (UserInterruptException e) {
						// Ignore
					} catch (EndOfFileException e) {
						return;
					} catch (Exception e) {
						systemRegistry.trace(e);
					}
				}
			}
		} catch (Throwable t) {
			throw new Exception("The REPL failed", t);
		}
	}

	protected abstract Object createRootCommand();

	protected abstract IFactory createCommandsFactory();

	protected abstract void initializeCommandLine(CommandLine cmd);

	protected abstract String createPropmt();

}
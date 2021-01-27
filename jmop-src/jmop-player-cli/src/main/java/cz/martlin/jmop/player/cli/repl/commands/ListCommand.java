package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

@Command(name = "list", subcommands = { //
		ListCommand.ListBundlesCommand.class, //
		ListCommand.ListPlaylistsCommand.class, //
		ListCommand.ListTracksCommand.class, //
}) //
public class ListCommand extends AbstractCommand {

	public ListCommand(JMOPPlayer jmop) {
		super(jmop);
	}

	@Command(name = "bundles")
	public static class ListBundlesCommand extends AbstractRunnableCommand {
		
		public ListBundlesCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			System.out.println(jmop.musicbase().bundles()); //TODO FIXME implementme
		}
	}

	@Command(name = "playlists")
	public static class ListPlaylistsCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleMixin bundle;

		public ListPlaylistsCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			System.out.println(jmop.musicbase().playlists(bundle)); //TODO FIXME implementme
		}
	}

	@Command(name = "tracks")
	public static class ListTracksCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleMixin bundle;

		public ListTracksCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			System.out.println(jmop.musicbase().playlists(bundle)); //TODO FIXME implementme
		}
	}

}

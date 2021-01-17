package cz.martlin.jmop.player.cli.repl.converters;

import java.util.StringTokenizer;

import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import picocli.CommandLine;

public class BundledItemPathParser {
	private final String defaultItemName;

	/**
	 * 
	 * @param defaultItemName can be null
	 */
	public BundledItemPathParser(String defaultItemName) {
		this.defaultItemName = defaultItemName;
	}

	protected BundledItemName parse(String bundledItemSpecOrNot) {
		//FIXME tricky AF, however, junit tested
		//FIXME what about '/' in names? will some escaping help?
		
		if (!bundledItemSpecOrNot.startsWith(PrintUtil.PATH_SEPARATOR)) {
			StringTokenizer tokenizer = new StringTokenizer(bundledItemSpecOrNot, PrintUtil.PATH_SEPARATOR);
			int count = tokenizer.countTokens();

			if (count == 1 || count == 2) {
				String bundleName = tokenizer.nextToken();
				String itemName;

				if (count == 2) {
					itemName = tokenizer.nextToken();
					if (itemName.isEmpty()) {
						itemName = defaultItemName;
					}
				} else {
					itemName = defaultItemName;
				}

				if (bundleName != null && itemName != null) {
					return new BundledItemName(bundleName, itemName);
				}
				if (bundleName != null && itemName == null) {
					// little trick, if in format "something",
					// it actually means "null/something"
					// because the defaultItemName catches the 
					// "something/defaultItemName" case.
					
					return new BundledItemName(null, bundleName);
				}
			}
		}

		throw new CommandLine.TypeConversionException(
				"Valid specifier is either 'bundle' or 'bundle/item' (playlist or track), " //
				+ "not: '" + bundledItemSpecOrNot + "'");
	}

	protected static class BundledItemName {
		protected final String bundleName;
		protected final String itemName;

		public BundledItemName(String bundleName, String playlistName) {
			super();
			this.bundleName = bundleName;
			this.itemName = playlistName;
		}

	}

}
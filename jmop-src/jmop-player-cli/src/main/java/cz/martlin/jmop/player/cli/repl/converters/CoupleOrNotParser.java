package cz.martlin.jmop.player.cli.repl.converters;

import java.util.Objects;

public class CoupleOrNotParser {

	private static final String SEPARATOR = "/";

	public CoupleOrNotParser() {
		super();
	}

	protected CoupleOrNot parse(String value) {
		// FIXME what about '/' in names? will some escaping help?

		String[] tokens = value.split(SEPARATOR, -1);

		if (tokens.length == 1) {
			String onlyFirst = tokens[0];
			return new CoupleOrNot(onlyFirst);
		}

		if (tokens.length == 2) {
			String first = tokens[0];
			String second = tokens[1];
			return new CoupleOrNot(first, second);
		}

		throw new IllegalArgumentException("Invalid format: " + value);
	}

	public static class CoupleOrNot {
		private final String first;
		private final String secondOptional;

		public CoupleOrNot(String onlyFirst) {
			super();
			Objects.requireNonNull(onlyFirst, "The onlyFirst must not be null");
			
			this.first = onlyFirst;
			this.secondOptional = null;
		}
		
		public CoupleOrNot(String first, String second) {
			super();
			Objects.requireNonNull(first, "The first must not be null");
			Objects.requireNonNull(second, "The second must not be null");
			
			this.first = first;
			this.secondOptional = second;
		}

		public boolean hasOnlyFirst() {
			return secondOptional == null;
		}
		
		public boolean hasBoth() {
			return secondOptional != null;
		}

		@Deprecated
		public boolean hasFirst() {
			return first != "";
		}
		
		@Deprecated
		public boolean hasSecond() {
			return secondOptional != null && !secondOptional.isEmpty();
		}
		
		@Deprecated
		public String first() {
			return first;
		}
		
		public String first(String dflt) {
			if (first.isEmpty()) {
				return dflt;
			} else {
				return first;
			}
		}
		
		@Deprecated
		public String second() {
			Objects.requireNonNull(secondOptional, "Cannot pick second when null");
			
			return secondOptional;
		}
		
		public String second(String dflt) {
			Objects.requireNonNull(secondOptional, "Cannot pick second when null");
			
			if (secondOptional.isEmpty()) {
				return dflt;
			} else {
				return secondOptional;
			}
		}

	}

}

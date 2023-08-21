package cz.martlin.jmop.common.storages.playlists;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;

public class ValueToAndFromStringConvertersTest {

	@Nested
	public static class SimpleValueToAndFromStringConverterTest extends BaseValueToAndFromStringConvertersTest {

		@Override
		protected BaseValueToAndFromStringConverters obtainConverters() {
			return new SimpleValueToAndFromStringConverters();
		}

		@Disabled("Simple converters does not support null values")
		@Override
		void testNullTrackIndex() throws Exception {
			super.testNullTrackIndex();
		}

		@Disabled("Simple converters does not support null values")
		@Override
		void testNullDate() throws Exception {
			super.testNullDate();
		}

		@Disabled("Simple converters does not support null values")
		@Override
		void testNullDuration() throws Exception {
			super.testNullDuration();
		}
	}

	@Nested
	public static class FormattingValueToAndFromStringConverterTest extends BaseValueToAndFromStringConvertersTest {

		@Override
		protected BaseValueToAndFromStringConverters obtainConverters() {
			return new FormatingValueToAndFromStringConverters();
		}

	}
}

package cz.martlin.jmop.core.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapperWithException {
	public static Stream<String> mapWithException(Stream<String> stream, FunctionWithException<String> mapper)
			throws ExceptionInLoop {

		List<Exception> exceptions = new ArrayList<>(1);

		Stream<String> mapped = stream.map((s) -> {
			try {
				return mapper.apply(s);
			} catch (Exception e) {
				exceptions.add(e);
				return null;
			}
		});

		// force to invoke the map above
		List<String> dumped = mapped.collect(Collectors.toList());

		if (exceptions.isEmpty()) {
			return dumped.stream();
		} else {
			ExceptionInLoop exception = new ExceptionInLoop();
			exceptions.forEach((e) -> exception.addSuppressed(e));
			throw exception;
		}
	}

	@FunctionalInterface
	public static interface FunctionWithException<T> {
		T apply(T input) throws Exception;
	}

	public static class ExceptionInLoop extends Exception {

		private static final long serialVersionUID = -200933488983341264L;

		public ExceptionInLoop() {
			super();
		}

	}

}

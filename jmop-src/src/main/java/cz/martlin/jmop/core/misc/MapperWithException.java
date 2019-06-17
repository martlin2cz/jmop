package cz.martlin.jmop.core.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The utility class for running stream mapping with function declaring checked
 * exceptions ("function with throws") in loop.
 * 
 * @author martin
 *
 */
public class MapperWithException {
	/**
	 * Over given input stream runs given mapper function. If function for some
	 * item of the stream throws exception, the exception is caught. At the end,
	 * if some exception have been caught, it is thrown {@link ExceptionInLoop}
	 * with all the caught expcetions supressed within that one.
	 * 
	 * But, keep in mind, the loop is not terminated, it is completed regardless
	 * of the exceptions thrown for particullar items of the stream.
	 * 
	 * @param stream
	 * @param mapper
	 * @return
	 * @throws ExceptionInLoop
	 */
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

	/**
	 * Function with exception exactly is like {@link Function}, but with
	 * declared thrown exception.
	 * 
	 * @author martin
	 *
	 * @param <T>
	 */
	@FunctionalInterface
	public static interface FunctionWithException<T> {
		T apply(T input) throws Exception;
	}

	/**
	 * Exception occured in the loop. Suppressed exceptions will contain
	 * particullar exceptions caught during the loop runs.
	 * 
	 * @author martin
	 *
	 */
	public static class ExceptionInLoop extends Exception {

		private static final long serialVersionUID = -200933488983341264L;

		public ExceptionInLoop() {
			super();
		}

	}

}

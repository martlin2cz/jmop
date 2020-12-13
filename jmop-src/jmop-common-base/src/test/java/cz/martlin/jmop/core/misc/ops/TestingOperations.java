package cz.martlin.jmop.core.misc.ops;

import java.util.concurrent.TimeUnit;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class TestingOperations {

	private TestingOperations() {
	}

	public static void runPrimitive(OperationsManager man) throws InterruptedException {
		
		TestingCountingLongOperation op = new TestingCountingLongOperation("test", 4);

		man.start(op, (r) -> System.out.println("The result is: " + r));

		TimeUnit.SECONDS.sleep(5);
	}

	public static void runMore(OperationsManager man) throws InterruptedException {
		// AAAAA-
		// -BB---
		// --CC--
		// --DDD-
		// ---E--

		TestingCountingLongOperation opA = new TestingCountingLongOperation("A", 5);
		TestingCountingLongOperation opB = new TestingCountingLongOperation("B", 2);
		TestingCountingLongOperation opC = new TestingCountingLongOperation("C", 2);
		TestingCountingLongOperation opD = new TestingCountingLongOperation("D", 3);
		TestingCountingLongOperation opE = new TestingCountingLongOperation("E", 1);

		man.start(opA, (r) -> System.out.println("The A is done"));
		TimeUnit.SECONDS.sleep(1);

		man.start(opB, (r) -> System.out.println("The B is done"));
		TimeUnit.SECONDS.sleep(1);

		man.start(opC, (r) -> System.out.println("The C is done"));
		man.start(opD, (r) -> System.out.println("The D is done"));
		TimeUnit.SECONDS.sleep(1);

		man.start(opE, (r) -> System.out.println("The E is done"));
		TimeUnit.SECONDS.sleep(1);

		TimeUnit.SECONDS.sleep(2);
	}
	
	public static void runChain(OperationsManager man) throws JMOPMusicbaseException, InterruptedException {
		
		BaseOperationsChain<Integer> chain = new TestingCountingOperationsChain("X", 5);
		man.start(5, chain, (r) -> System.out.println("The chain result is : " + r));
		
		TimeUnit.SECONDS.sleep(1);
		TimeUnit.SECONDS.sleep(1);
		TimeUnit.SECONDS.sleep(2);
		TimeUnit.SECONDS.sleep(3);
		TimeUnit.SECONDS.sleep(4);
		TimeUnit.SECONDS.sleep(5);
	}
}

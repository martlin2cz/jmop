package cz.martlin.jmop.player.cli.repl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Just the empty test, which just simply initializes the whole REPL and then
 * terminates. Use to quickly verify the picocli configuration and the whole
 * jmop-picocli interaction without any actual work.
 * 
 * @author martin
 *
 */
public class JmopReplEmptyTest extends AbstractReplTest {

	@Test
	public void testJustTheEmptyRepl() throws Exception {
		System.out.println("CommandLine ready: " + cl);
		assertNotNull(cl);
	}
	

	@Disabled
	@Test
	public void testPrintHelp() throws Exception {
		exec("help"); //TODO FIXME IMPLEMENTME
	}

}

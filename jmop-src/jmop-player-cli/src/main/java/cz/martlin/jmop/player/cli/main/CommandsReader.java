package cz.martlin.jmop.player.cli.main;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class CommandsReader {

	private static final String DELIMITER = "(?<!([\\\\]|$)) +";
	private final Scanner scanner;
	
	public CommandsReader(InputStream source) {
		super();
		this.scanner = prepareScanner(source);
	}

	private Scanner prepareScanner(InputStream source) {
		Scanner scanner = new Scanner(source);
		
		scanner.useDelimiter(DELIMITER);
		
		return scanner;
	}

	/////////////////////////////////////////////////////////////////
	
	public String[] waitAndGetNext() {
		String line = scanner.nextLine();
		StringTokenizer tokenizer = new StringTokenizer(line, DELIMITER); 
			
		Iterator<Object> iter = tokenizer.asIterator();
		Stream<String> tokens = Stream.generate(() -> iter.next() ).map(i -> (String) i);
		return tokens.toArray(String[]::new);
	}
	
//	
//
//
//	public static void main(String[] args) {
//		String test = "foo bar\\ baz 42";
//		Scanner scan = new Scanner(test);
//		scan.useDelimiter("(?<!([\\\\]|$)) +");
//		
//		System.out.println(scan.tokens().collect(Collectors.toList()));
//		
////		System.out.println(scan.findAll("([^ ])+").map(m -> m.group()).collect(Collectors.toList())); 
////		
////		System.out.println(scan.next());
////		System.out.println(scan.next());
////		System.out.println(scan.next());
////		System.out.println(scan.next());
////		System.out.println(scan.next());
////		System.out.println(scan.next());
////		System.out.println(scan.next());
////		System.out.println(scan.next());
////		
//		
//	}
}

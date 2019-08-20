package cz.martlin.jmop.core.sources.remote;

import java.io.File;
import java.util.Scanner;

public class ConsoleUIInteractor implements BaseUIInterractor {

	private final Scanner scanner;

	public ConsoleUIInteractor() {
		this.scanner = new Scanner(System.in);
	}

	@Override
	public String prompt(String message) {
		System.out.println(message);
		return scanner.next();
	}

	@Override
	public boolean confirm(String message) {
		System.out.println(message);
		return scanner.nextBoolean();
	}

	@Override
	public File promptFile(String message, String extension) {
		System.out.println(message);
		String path = scanner.next();
		return new File(path);
	}

	@Override
	public void displayError(String message) {
		System.err.println(message);
	}

}

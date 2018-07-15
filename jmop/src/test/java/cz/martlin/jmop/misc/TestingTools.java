package cz.martlin.jmop.misc;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class TestingTools {
	
	public static boolean delete(File directory) {
		Path path = directory.toPath();

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public static File testingTempDirectory(String name) {
		
		String tmpDir = System.getProperty("java.io.tmpdir");
		File dir = new File(tmpDir, name);

		
		return dir;
	}
	
	
	public static void runAsJavaFX(Runnable runnable) {
		new JFXPanel(); // Initializes the JavaFx Platform
		
        Platform.runLater(runnable);
	}
}

package almeida.ferreira.junio.bluefood.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class IOUtils {
	
	public static void copyFile(InputStream in, String fileName, String directory) throws IOException {
		Files.copy(in, Paths.get(directory, fileName), StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static boolean deleteFile(String fileName, String directory) throws IOException {
		return Files.deleteIfExists(Paths.get(directory, fileName));
	}
	
	public static byte[] getBytesFile(Path path) throws IOException {
		return Files.readAllBytes(path);
	}

}

package almeida.ferreira.junio.bluefood.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class IOUtils {
	
	public static void copyFile(InputStream in, String fileName, String directory) throws IOException {
		Files.copy(in, Paths.get(directory, fileName), StandardCopyOption.REPLACE_EXISTING);
	}

}

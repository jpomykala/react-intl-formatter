package me.jpomykala.yahoo;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
public class TestUtils {

	public static byte[] readFile(String name) throws IOException {
		File file = new ClassPathResource(name).getFile();
		FileInputStream fileInputStream = new FileInputStream(file);
		return ByteStreams.toByteArray(fileInputStream);
	}
}

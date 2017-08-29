package me.jpomykala.yahoo.formatter.reader;

import static org.assertj.core.api.Assertions.*;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import me.jpomykala.yahoo.TestUtils;
import me.jpomykala.yahoo.exceptions.ReadException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
@RunWith(SpringRunner.class)
public class ExcelReaderTest {

	@Test
	public void readFile_returnMap() throws Exception {
		//given
		String fileName = "translations.xlsx";
		byte[] excelFileBytes = TestUtils.readFile(fileName);

		//when
		TranslationsReader translationsReader = new ExcelReader();
		Map<String, Map<String, String>> jsonMap = translationsReader.read(excelFileBytes);

		//then
		assertThat(jsonMap).containsOnlyKeys("EN", "PL", "ES");
		Map<String, String> enTranslation = jsonMap.get("EN");
		assertThat(enTranslation).containsOnlyKeys("NAVBAR.DISCOVER", "NAVBAR.CREATE");
	}

	@Test
	public void readEmptyFile_returnEmptyMap() throws Exception {
		//given
		String fileName = "emptyTranslations.xlsx";
		byte[] excelFileBytes = TestUtils.readFile(fileName);

		//when
		TranslationsReader translationsReader = new ExcelReader();
		Map<String, Map<String, String>> jsonMap = translationsReader.read(excelFileBytes);

		//then
		assertThat(jsonMap).containsOnlyKeys("EN", "PL", "ES");
		Map<String, String> enTranslation = jsonMap.get("EN");
		assertThat(enTranslation).isEmpty();
	}

	@Test(expected = ReadException.class)
	public void readNullFile_throwWriterException() throws Exception {
		//given
		byte[] excelFileBytes = null;

		//when
		TranslationsReader translationsReader = new ExcelReader();
		translationsReader.read(excelFileBytes);

		//then
	}

}
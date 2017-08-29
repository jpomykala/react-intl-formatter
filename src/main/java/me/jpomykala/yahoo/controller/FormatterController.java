package me.jpomykala.yahoo.controller;

import java.io.IOException;
import java.util.Map;
import me.jpomykala.yahoo.formatter.reader.ExcelReader;
import me.jpomykala.yahoo.formatter.reader.TranslationsReader;
import me.jpomykala.yahoo.formatter.writer.JsonWriter;
import me.jpomykala.yahoo.formatter.writer.TranslationsWriter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jakub Pomykala on 08/08/2017.
 */
@RestController
public class FormatterController {

	@PostMapping(value = "/format")
	public byte[] formatFile(@RequestParam("file") MultipartFile file) throws IOException {
		byte[] excelFile = file.getBytes();
		TranslationsReader translationsReader = new ExcelReader();
		Map<String, Map<String, String>> translationsMap = translationsReader.read(excelFile);
		TranslationsWriter translationsWriter = new JsonWriter();
		return translationsWriter.write(translationsMap);
	}
}
package me.jpomykala.yahoo.controller;

import java.io.IOException;
import java.util.Map;
import me.jpomykala.yahoo.formatter.reader.ExcelReader;
import me.jpomykala.yahoo.formatter.reader.TranslationsReader;
import me.jpomykala.yahoo.formatter.writer.JsonWriter;
import me.jpomykala.yahoo.formatter.writer.TranslationsWriter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
@Controller
public class HomeController {

	@RequestMapping(value = {"/", ""})
	public String home() {
		return "index";
	}

	@PostMapping(value = "/format", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public byte[] formatFile(@RequestParam("file") MultipartFile file) throws IOException {
		byte[] excelFile = file.getBytes();
		TranslationsReader translationsReader = new ExcelReader();
		Map<String, Map<String, String>> translationsMap = translationsReader.read(excelFile);
		TranslationsWriter translationsWriter = new JsonWriter();
		return translationsWriter.write(translationsMap);
	}

}

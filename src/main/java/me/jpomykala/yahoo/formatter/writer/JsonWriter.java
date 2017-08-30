package me.jpomykala.yahoo.formatter.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Map;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
public class JsonWriter implements TranslationsWriter {

	private ObjectMapper objectMapper;

	public JsonWriter() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
		this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Override
	public byte[] write(Map<String, Map<String, String>> translations) {
		return tryWriteData(translations);
	}

	private byte[] tryWriteData(Map<String, Map<String, String>> translations) {
		try {

			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(translations);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Error while writing json");
		}
	}

}

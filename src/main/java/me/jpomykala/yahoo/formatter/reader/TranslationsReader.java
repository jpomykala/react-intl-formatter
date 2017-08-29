package me.jpomykala.yahoo.formatter.reader;

import java.util.Map;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
public interface TranslationsReader {

	Map<String, Map<String, String>> read(byte[] bytes);
}

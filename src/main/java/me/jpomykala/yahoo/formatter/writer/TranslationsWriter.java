package me.jpomykala.yahoo.formatter.writer;

import java.util.Map;
import me.jpomykala.yahoo.exceptions.WriteException;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
public interface TranslationsWriter {

	byte[] write(Map<String, Map<String, String>> translations) throws WriteException;
}

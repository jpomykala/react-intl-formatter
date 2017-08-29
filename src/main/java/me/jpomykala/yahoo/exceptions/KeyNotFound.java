package me.jpomykala.yahoo.exceptions;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
public class KeyNotFound extends RuntimeException {

	public KeyNotFound(String key) {
		super(key);
	}
}

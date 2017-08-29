package me.jpomykala.yahoo.exceptions;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
public class TranslationLanguageNotFound extends RuntimeException {

	public TranslationLanguageNotFound(String translationLanguage) {
		super(translationLanguage);
	}
}

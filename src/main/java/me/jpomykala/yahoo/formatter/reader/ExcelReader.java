package me.jpomykala.yahoo.formatter.reader;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import me.jpomykala.yahoo.exceptions.KeyNotFound;
import me.jpomykala.yahoo.exceptions.ReadException;
import me.jpomykala.yahoo.exceptions.TranslationLanguageNotFound;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
public class ExcelReader implements TranslationsReader {

	@Override
	public Map<String, Map<String, String>> read(byte[] bytes) throws ReadException {
		Optional<Sheet> sheet = tryReadFirstSheet(bytes);
		return sheet
				.map(this::readJsonFromSheet)
				.orElseThrow(ReadException::new);
	}

	private Optional<Sheet> tryReadFirstSheet(byte[] bytes) {
		Workbook workbook = null;
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			workbook = WorkbookFactory.create(inputStream);
		} catch (IOException | InvalidFormatException e) {
			e.printStackTrace();
		} catch (NullPointerException npe) {
			throw new ReadException("bytes cannot be null");
		}
		return Optional.ofNullable(workbook).map(sheets -> sheets.getSheetAt(0));
	}

	private Map<String, Map<String, String>> readJsonFromSheet(Sheet sheet) {
		Map<String, Map<String, String>> jsonMap = Maps.newHashMap();
		Set<String> availableTranslations = getAvailableTranslations(sheet);
		Set<String> availableKeys = getAvailableKeys(sheet);
		for (String translation : availableTranslations) {
			Map<String, String> keyMap = Maps.newHashMap();
			for (String availableKey : availableKeys) {
				Optional<String> translationValue = getTranslation(availableKey, translation, sheet);
				translationValue.ifPresent(value -> keyMap.put(availableKey, value));
			}
			jsonMap.put(translation, keyMap);
		}
		return jsonMap;
	}

	private Set<String> getAvailableTranslations(Sheet sheet) {
		Row headRow = getFirstRow(sheet);
		Iterator<Cell> cellIterator = headRow.cellIterator();
		cellIterator.next();
		Set<String> output = Sets.newHashSet();
		while (cellIterator.hasNext()) {
			Cell next = cellIterator.next();
			String stringCellValue = next.getStringCellValue();
			if (!Strings.isNullOrEmpty(stringCellValue)) {
				output.add(stringCellValue);
			}
		}
		return output;
	}

	private Set<String> getAvailableKeys(Sheet sheet) {
		Set<String> outputKeys = Sets.newHashSet();
		int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
		for (int rowIndex = 1; rowIndex < physicalNumberOfRows; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			Cell firstColumn = getFirstColumn(row);
			String stringCellValue = firstColumn.getStringCellValue();
			if (!Strings.isNullOrEmpty(stringCellValue)) {
				outputKeys.add(stringCellValue.trim());
			}
		}
		return outputKeys;
	}

	private Cell getFirstColumn(Row row) {
		return row.getCell(0);
	}

	private Row getFirstRow(Sheet sheet) {
		return sheet.getRow(0);
	}

	private Optional<String> getTranslation(String key, String translation, Sheet sheet) {
		int rowIndex = getKeyIndex(key, sheet);
		Row row = sheet.getRow(rowIndex);
		int columnIndex = getTranslationIndex(translation, sheet);
		return Optional
				.ofNullable(row.getCell(columnIndex))
				.map(Cell::getStringCellValue);
	}

	private int getKeyIndex(String key, Sheet sheet) {
		key = key.trim();
		int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
		for (int rowIndex = 0; rowIndex < physicalNumberOfRows; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			Cell keyCell = row.getCell(0);
			String stringCellValue = keyCell.getStringCellValue();
			boolean keyIndexFound = key.equalsIgnoreCase(stringCellValue);
			if (keyIndexFound) {
				return rowIndex;
			}
		}
		throw new KeyNotFound(key);
	}

	private int getTranslationIndex(String translation, Sheet sheet) {
		Row headRow = getFirstRow(sheet);
		Iterator<Cell> cellIterator = headRow.cellIterator();
		int cellId = -1;
		while (cellIterator.hasNext()) {
			Cell next = cellIterator.next();
			cellId++;
			String cellValue = next.getStringCellValue();
			if (translation.equalsIgnoreCase(cellValue)) {
				return cellId;
			}
		}
		throw new TranslationLanguageNotFound(translation);
	}

}

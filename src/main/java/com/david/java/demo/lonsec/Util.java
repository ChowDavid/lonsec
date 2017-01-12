package com.david.java.demo.lonsec;

import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.david.java.demo.lonsec.exception.FileReadingException;

public class Util {

	/**
	 * Check if the value is empty or null
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * Check is the input array is null or part of component is empty
	 * 
	 * @param args
	 * @return
	 */
	public static boolean isEmpty(String... args) {
		boolean isEmpty = false;
		if (args == null || args.length == 0)
			return true;
		for (String arg : args) {
			isEmpty = isEmpty || isEmpty(arg);
		}
		return isEmpty;
	}

	/**
	 * Convert the date string into LocalDate
	 * 
	 * @param dateString
	 * @param dateFormatters,
	 *            Collection of DateTimeFormatter
	 * @return LocalDate if input is correct otherwise,
	 * @throws NullPointerException
	 *             if input is empty or null.
	 * @throws DateTimeParseException
	 *             if non of date formatter can be used.
	 */
	public static LocalDate getDate(String dateString, List<DateTimeFormatter> dateFormatters) {
		if (Util.isEmpty(dateString) || dateFormatters == null) {
			throw new NullPointerException();
		}
		LocalDate date = null;
		DateTimeParseException latestError = null;
		for (DateTimeFormatter dateFormatter : dateFormatters) {

			try {
				date = LocalDate.parse(dateString, dateFormatter);
			} catch (DateTimeParseException e) {
				// Retry the other formatter
				latestError = e;
			}
		}
		if (date == null){
			throw new DateTimeException("Please check the date time format expected "+ConfigStore.getDateFormat()+" not ["+dateString+"]");
		}
		return date;

	}

	/**
	 * To split a line of delimiter "," into String array. and check if the
	 * number of cells is expected
	 * 
	 * @param file,
	 *            input for exception message
	 * @param lineNumber,
	 *            input for exception message
	 * @param message,
	 *            the input string to be split
	 * @param expectedColumn,
	 *            used to compare the output cells length
	 * @return all output should be trimmed
	 * @throws FileReadingException,
	 *             if the input message is non-expected
	 */
	public static String[] splite(Path file, int lineNumber, String message, int expectedColumn)
			throws FileReadingException {
		if (message == null || message.trim().length() == 0) {
			if (file == null) {
				throw new FileReadingException("N/A", lineNumber, "Input Message is empty [" + message + "]");
			} else {
				throw new FileReadingException(file.toString(), lineNumber, "Input Message is empty [" + message + "]");
			}
		}
		String[] cells = message.split(",");
		if (cells.length != expectedColumn) {
			if (file == null) {
				throw new FileReadingException("N/A", lineNumber,
						"Expected Column not match expected[" + expectedColumn + "], actual[" + cells.length + "]");
			} else {
				throw new FileReadingException(file.toString(), lineNumber,
						"Expected Column not match expected[" + expectedColumn + "], actual[" + cells.length + "]");
			}
		}
		for (int i=0;i<cells.length;i++){
			cells[i]=cells[i].trim();
		}
		return cells;
	}

	/**
	 * Convert the localdate into string format dd/MM/yyyy 
	 * @param date
	 * @return
	 */
	public static String dateToString(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

}

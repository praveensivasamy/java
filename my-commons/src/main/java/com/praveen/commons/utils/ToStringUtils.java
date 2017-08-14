package com.praveen.commons.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

public class ToStringUtils {

	public static final String CSV_SEP = ",";

	private static NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

	static {
		format.setMaximumFractionDigits(15);
	}

	/**
	 * Standard toString utility method
	 *
	 * @param o the object
	 * @param attributeNames the list of attribute names to be part of the output. If the attribute name ends with 'newLine', the attribute will be
	 * displayed on a new line
	 * @return SimpleClassName[attribute_1:value_1]...[attribute_n:value_n]
	 */
	public static String asString(Object o, String... attributeNames) {
		StringBuilder res = new StringBuilder();
		res.append(o.getClass().getSimpleName());
		for (String attributeName : attributeNames) {
			if (attributeName.endsWith("\n")) {
				attributeName = attributeName.replace("\n", "");
				res.append("\n");
			}
			res.append("[").append(attributeName).append(":").append(output(ReflectionHelper.getFieldValue(attributeName, o))).append("]");
		}
		return res.toString();
	}

	public static String asCommaSeparated(Collection<?> coll) {
		StringBuilder sb = new StringBuilder("(");
		for (Object o : coll) {
			sb.append(o).append(",");
		}
		return StringUtils.removeEnd(sb.toString(), ",") + ")";
	}

	public static List<String> asList(String commaSeparatedString) {
		if ((commaSeparatedString == null) || commaSeparatedString.isEmpty()) {
			return new ArrayList<>();
		}
		return Arrays.asList(commaSeparatedString.split(","));
	}

	/**
	 * This method converts the comma seperated strings to the in clause for SQL in clause
	 *
	 * @param elements
	 * @return
	 */
	public static String encloseSQLQuotes(List<String> elements) {
		String tmpStr = "";
		for (String element : elements) {
			tmpStr += '\'' + element + '\'' + ',';
		}
		tmpStr = tmpStr.lastIndexOf(',') > 0 ? '(' + tmpStr.substring(0, tmpStr.lastIndexOf(',')) + ')' : tmpStr;
		return tmpStr;

	}

	public static String output(Object o) {
		if (o == null) {
			return "null";
		}
		if (o instanceof DateTime) {
			return ((DateTime) o).toString("dd/MM/yyyy");
		}
		if (o instanceof Date) {
			return DateUtils.format((Date) o);
		}
		if (o instanceof Object[]) {
			return Arrays.deepToString((Object[]) o);
		}
		if (o instanceof Collection<?>) {
			Collection<?> col = (Collection<?>) o;
			return asCommaSeparated(col);
		}

		if (o instanceof Double) {
			return format.format(o);
		}

		if (o instanceof Map<?, ?>) {
			StringBuilder s = new StringBuilder();
			for (Object key : ((Map<?, ?>) o).keySet()) {
				s.append(key).append(":").append(((Map<?, ?>) o).get(key));
			}
			return s.toString();
		}

		return o.toString();
	}

	/**
	 * Convert given object to formatted string
	 *
	 * @param o
	 * @param locale
	 * @return
	 */
	private static String output(Object o, Locale locale) {
		if (o == null) {
			return "null";
		}
		if (o instanceof DateTime) {
			return ((DateTime) o).toString("dd/MM/yyyy");
		}
		if (o instanceof Date) {
			return DateUtils.format((Date) o);
		}
		if ((o instanceof Double)) {
			return format.format(o);
		}
		return o.toString();
	}

	/**
	 * Split the given string with ',' as separator
	 * <p>
	 * Ignore any substring enclosed inside / * and * /
	 *
	 * @return
	 */
	public static String[] split(String s) {
		if (s == null) {
			return new String[0];
		} else {
			return removeComments(s).split(",");
		}
	}

	/**
	 * Remove any substring enclosed inside / * and * / from the original string
	 * <p>
	 * User in {link RaceConfiguration#getWriters()} and the like
	 */
	private static String removeComments(String s) {
		StringBuilder res = new StringBuilder();
		boolean inComment = false;
		char[] chars = s.toCharArray();
		for (char c : chars) {
			if (c == '/') {
				inComment = !inComment;
			}
			if (!inComment && (c != '*') && (c != '/')) {
				res.append(c);
			}
		}
		return res.toString();
	}

	/**
	 * Replace the placeholder (which is enclosed in curly brackets) with the given value, optionally quoted with the single quote character
	 * <p>
	 * Example "where userName = {user_name}" to "where userName = abc"
	 *
	 * @return the transformed string
	 */

	public static String replacePlaceholder(String s, String placeholder, String value, boolean quote) {
		String toBeReplaced = "{" + placeholder + "}";
		String replacement = quote ? "'" + value + "'" : value;
		return s.replace(toBeReplaced, replacement);
	}

	/**
	 * Replace occurence '{text}' with the given value
	 *
	 * @param text the text containing the placeholder
	 * @param value the placeholder value
	 */
	public static String replacePlaceholder(String text, String placeholder, Object value) {
		String replacement = asString(value);
		return text.replace("{" + placeholder + "}", replacement);
	}

	private static String asString(Object value) {
		if (value == null) {
			return "null";
		}
		if (value instanceof Date) {
			return DateUtils.format((Date) value);
		}
		return value.toString();
	}

	public static String quote(String s) {
		return "'" + s + "'";
	}

	/**
	 * Get 'url' from the connection string
	 *
	 * @param connString format url|user|password|schema
	 * @return
	 */
	public static String getUrl(String connString) {
		return getConnStringToken(connString, 0);
	}

	/**
	 * Get 'user' from the connection string
	 *
	 * @param connString format url#user#password#schema
	 * @return
	 */
	public static String getUser(String connString) {
		return getConnStringToken(connString, 1);
	}

	/**
	 * Get 'password' from the connection string
	 *
	 * @param connString format url#user#password#schema
	 * @return
	 */
	public static String getPassword(String connString) {
		String password = getConnStringToken(connString, 2);
		return password;
	}

	/**
	 * Get 'schema' from the connection string
	 *
	 * @param connString format url#user#password#schema
	 * @return
	 */
	public static String getSchema(String connString) {
		return getConnStringToken(connString, 3);
	}

	private static String getConnStringToken(String connString, int i) {
		String[] tokens = connString.split("\\|");
		if (tokens.length != 4) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION)
					.details("Invalid format for " + connString + " Expected format: url|user|password|schema");
		}
		return tokens[i];
	}

	public static String getSid(String connString) {
		String url = getUrl(connString);
		return url.substring(url.lastIndexOf(':') + 1);
	}

	public static String asCsvString(String delimiter, Object o, String... attributeNames) {
		StringBuilder res = new StringBuilder();
		delimiter = delimiter == null ? "," : delimiter;
		for (int i = 0; i < attributeNames.length; i++) {
			String s = output(ReflectionHelper.getFieldValue(attributeNames[i], o));
			res.append(s.replace("\n", ":"));
			if (i < (attributeNames.length - 1)) {
				res.append(delimiter);
			}
		}
		return res.toString();
	}

	/**
	 *
	 */
	public static String asCsvString(String delimiter, Object o) {
		StringBuilder res = new StringBuilder();
		delimiter = delimiter == null ? "," : delimiter;

		if (ReflectionHelper.getTableName(o.getClass()) == null) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION).details("Object not entity");
		}
		List<String> column = ReflectionHelper.getAnnotatedFields(Column.class, o.getClass());
		column.sort((o1, o2) -> o1.compareTo(o2));
		return res.toString();
	}

	/**
	 * Converts the given bean to CSV and formats numbers based on locale
	 *
	 * @param delimiter
	 * @param o
	 * @param attributeNames
	 * @return
	 */
	public static String asCSV(String delimiter, Object o, String... attributeNames) {
		StringBuilder res = new StringBuilder();
		delimiter = delimiter == null ? "," : delimiter;
		for (int i = 0; i < attributeNames.length; i++) {
			String s = output(ReflectionHelper.getFieldValue(attributeNames[i], o), Locale.getDefault());
			res.append(s.replace("\n", ":"));
			if (i < (attributeNames.length - 1)) {
				res.append(delimiter);
			}
		}
		return res.toString();
	}

	public static String asCsvHeader(Object o, String... attributeNames) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < attributeNames.length; i++) {
			res.append(attributeNames[i]);
			if (i < (attributeNames.length - 1)) {
				res.append(",");
			}
		}
		return res.toString();
	}

	public static String asCsvHeaderColumns(String delimiter, Object o, String... attributeNames) {
		delimiter = delimiter == null ? "," : delimiter;
		StringBuilder res = new StringBuilder();
		String[] columns = ReflectionHelper.getColumnsNames(o.getClass(), attributeNames);
		for (int i = 0; i < columns.length; i++) {
			res.append("\"" + columns[i] + "\"");
			if (i < (attributeNames.length - 1)) {
				res.append(delimiter);
			}
		}
		return res.toString();
	}

	public static int nthOccurrence(String str, char c, int n) {
		int pos = str.indexOf(c, 0);
		while ((--n > 0) && (pos != -1)) {
			pos = str.indexOf(c, pos + 1);
		}
		return pos;
	}

	public static String getThreadStackTrace() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StringBuilder sb = new StringBuilder(Thread.currentThread().getName() + ":\n");
		for (int i = 1; i < stackTrace.length; i++) {
			StackTraceElement ste = stackTrace[i];
			if (!ste.getClassName().contains("clip")) {
				continue;
			}
			sb.append(ste.getClassName() + ":" + ste.getMethodName() + ":" + ste.getLineNumber()).append("\n");
		}
		return sb.toString();
	}
}

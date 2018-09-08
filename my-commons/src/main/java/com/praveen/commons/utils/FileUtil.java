package com.praveen.commons.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

public class FileUtil {

	private static final String COMMENT = "#";

	public static PrintStream createPrintStream(String fileName) {
		try {
			return new PrintStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		} catch (FileNotFoundException e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
					.details("Error creating PrintStream for " + fileName);
		}

	}

	public static void writeLine(BufferedWriter writer, String s) {
		try {
			writer.write(s);
			writer.newLine();
		} catch (Exception e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
					.details("IO exception in writeLine");
		}
	}

	/**
	 * Write the given String to the class-path resource
	 */
	public static void writeToResource(Class<?> caller, String resource, String s, boolean append) {
		String fileName;
		try {
			fileName = caller.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
					.replace("target/classes/", resource).replace("target/test-classes", resource);
		} catch (URISyntaxException e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
					.details("Invalid resource name specified" + resource);
		}
		writeToFile(fileName, s, append);
	}

	/**
	 * Write the given String to given file name
	 */
	public static void writeToFile(String fileName, String s, boolean append) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
			writer.write(s);
			writer.close();
		} catch (Exception e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
					.details("Exception writing to file:" + fileName);
		}
	}

	public static void clearResource(Class<?> caller, String resource) {
		writeToResource(caller, resource, "", false);
	}

	public static List<String> readLines(String fileName, boolean ignoreComment) {
		BufferedReader reader = null;
		try {
			List<String> res = new ArrayList<>();
			reader = new BufferedReader(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (ignoreComment && line.startsWith(COMMENT)) {
					continue;
				}
				if (!line.isEmpty()) {
					res.add(line);
				}
			}
			return res;
		} catch (Exception e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
					.details("Error reading file" + fileName);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ignore) {
			}
		}
	}

	public static Properties getProperties(String resource) {
		Properties res = new Properties();
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if (is == null) {

				return null;
			}
			res.load(is);
			return res;
		} catch (IOException e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
					.details("IOException trying to load properties from" + resource);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method generates csv files with custom separator.The
	 * <code> String... separator</code> param is optional. Only first option of the
	 * separator is considered rest options are discarded.
	 *
	 * @param fileName  the intended csv file name
	 * @param separator only one value is considered <br>
	 *                  <br>
	 *                  <code>if (separator[0] == null) {separator[0] = ToStringUtils.CSV_SEP; }</code>
	 *
	 */
	public static void writeSeparator(String fileName, String... separator) {
		String sep = ToStringUtils.CSV_SEP;
		if ((separator.length > 0) && (separator[0] != null)) {
			sep = separator[0];
		}
		writeToFile(fileName, "sep=" + sep + "\n", false);
	}

}

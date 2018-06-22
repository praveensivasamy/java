package com.praveen.commons.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

/**
 * String utilities and convinence methods which always returns String
 * 
 * @author Praveen Sivasamy
 *
 */
public class ToStringUtils {

    protected static final String CSV_SEP = ",";

    private static NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

    static {
        format.setMaximumFractionDigits(10);
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
            res.append("[").append(attributeName).append(":").append(objectToString(ReflectionHelper.getFieldValue(attributeName, o))).append("]");
        }
        return res.toString();
    }

    /**
     * convert {@link Collection} as comma seperated {@link String}
     * 
     * @param coll
     * @return {@link String}
     */
    public static String collectionToCSV(Collection<?> coll) {
        StringBuilder sb = new StringBuilder("(");
        for (Object o : coll) {
            sb.append(o).append(",");
        }
        return StringUtils.removeEnd(sb.toString(), ",") + ")";
    }

    /**
     * convert comme seperated string to {@link List}
     * 
     * @param commaSeparatedString
     * @return {@link List}
     */
    public static List<String> csvToList(String commaSeparatedString) {
        if ((commaSeparatedString == null) || commaSeparatedString.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(commaSeparatedString.split(","));
    }

    /**
     * Converts a bean to delimited string default delimiter is comma
     * 
     * @param delimiter
     * @param o
     * @param attributeNames
     * @return
     */
    public static String asDelimitedString(String delimiter, Object o, String... attributeNames) {
        StringBuilder res = new StringBuilder();
        delimiter = delimiter == null ? "," : delimiter;
        for (int i = 0; i < attributeNames.length; i++) {
            String s = formatObjectToString(ReflectionHelper.getFieldValue(attributeNames[i], o), Locale.getDefault());
            res.append(s.replace("\n", ":"));
            if (i < (attributeNames.length - 1)) {
                res.append(delimiter);
            }
        }
        return res.toString();
    }

    /**
     * converts {@link Entity} colums to CSV string
     * 
     * @param delimiter
     * @param entity
     * @param attributeNames
     * @return
     */
    public static String columnsAsCsv(String delimiter, Object entity, String... attributeNames) {
        delimiter = delimiter == null ? "," : delimiter;
        StringBuilder res = new StringBuilder();
        String[] columns = ReflectionHelper.getColumnsNames(entity.getClass(), attributeNames);
        for (int i = 0; i < columns.length; i++) {
            res.append("\"" + columns[i] + "\"");
            if (i < (attributeNames.length - 1)) {
                res.append(delimiter);
            }
        }
        return res.toString();
    }

    /**
     * convert {@link Object} to {@link String} formatted
     * 
     * @param o
     * @return
     */
    public static String objectToString(Object o) {
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
            return collectionToCSV(col);
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
     * Object to formatted string based on {@link Locale}
     *
     * @param o
     * @param locale
     * @return
     */
    private static String formatObjectToString(Object o, Locale locale) {
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

        if (value instanceof String) {
            return text.replace("{" + placeholder + "}", value.toString());
        }
        String replacement = asString(value);
        return text.replace("{" + placeholder + "}", replacement);
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

    /**
     * convert comma seperated strings to the 'in' clause for SQL in clause
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

}

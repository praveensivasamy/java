package com.praveen.commons.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Wrapp the comparison mismatches between 2 objects
 * 
 * @author Praveen
 *
 */
public class ObjectDiff {

    private static final Logger log = LoggerFactory.getLogger(ObjectDiff.class);
    private static final NumberFormat nf = NumberFormat.getInstance();

    public static ThreadLocal<NumberFormat> numberFormat = new ThreadLocal<NumberFormat>() {
	protected NumberFormat initialValue() {
	    return NumberFormat.getInstance();
	};
    };

    private Object object1;
    private Object object2;
    private double approximation;
    private String[] attributes;
    private List<FieldDiff> fieldDiffs = new ArrayList<ObjectDiff.FieldDiff>();

    public ObjectDiff(Object o1, Object o2, double approximation, String... attributes) {
	super();
	this.object1 = o1;
	this.object2 = o2;
	this.approximation = approximation;
	this.attributes = attributes;
	initialize();
    }

    /**
     * Compare the specified numeric/string attributes of the 2 objects , using the given approximation
     * 
     * @return <code>true</code> if the values of all attributes for both objects match, <code>false</code> otherwise
     * @throws Exception if any of the specified attributes do not exist in the given Object or is not a numeric type
     */
    private void initialize() {
	NumberFormat nf = numberFormat.get();
	for (String attribute : attributes) {
	    Object val1 = ReflectionHelper.getFieldValue(attribute, object1);
	    Object val2 = ReflectionHelper.getFieldValue(attribute, object2);
	    if (val1 == null) {
		val1 = 0;
	    }
	    if (val2 == null) {
		val2 = 0;
	    }
	    if (val1 instanceof Number) {
		Number diff = Math.abs(((Number) val2).doubleValue() - ((Number) val1).doubleValue());
		if (diff.doubleValue() > approximation) {
		    log.trace(attribute + " mismatch:" + nf.format(val1) + ":" + nf.format(val2) + " for " + object1 + " and " + object2);
		    fieldDiffs.add(new FieldDiff(attribute, val1, val2));
		}
	    } else {
		if (!val1.equals(val2)) {
		    fieldDiffs.add(new FieldDiff(attribute, val1, val2));
		}
	    }
	}
    }

    public boolean isSame() {
	return fieldDiffs.isEmpty();
    }

    public Object getO1() {
	return object1;
    }

    public Object getO2() {
	return object2;
    }

    public List<FieldDiff> getFieldDiffs() {
	return fieldDiffs;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(object1);
	for (FieldDiff f : fieldDiffs) {
	    sb.append("[").append(f.getFieldName()).append("(expected:").append(asString(f.getVal1())).append(":actual:")
		    .append(asString(f.getVal2())).append("]");
	}
	return sb.toString();
    }

    private Object asString(Object val) {
	if (val instanceof Number) {
	    return nf.format(val);
	}
	if (val instanceof Date) {
	    return DateUtils.format((Date) val);
	}
	return val == null ? "null" : val.toString();
    }

    public class FieldDiff {
	private String fieldName;
	private Object val1;
	private Object val2;

	public FieldDiff(String fieldName, Object val1, Object val2) {
	    super();
	    this.fieldName = fieldName;
	    this.val1 = val1;
	    this.val2 = val2;
	}

	public String getFieldName() {
	    return fieldName;
	}

	public Object getVal1() {
	    return val1;
	}

	public Object getVal2() {
	    return val2;
	}

	@Override
	public String toString() {
	    return "[" + fieldName + "(" + val1 + ":" + val2 + ")]";
	}
    }

}

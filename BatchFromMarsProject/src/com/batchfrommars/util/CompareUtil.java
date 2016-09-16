package com.batchfrommars.util;

import java.math.BigDecimal;
import java.util.Date;

public class CompareUtil {

	public static int compare(Object input1, Object input2) {

		// System.out.println(input1.getClass());
		// System.out.println(int.class);

		// if object is int
		if (input1.getClass().equals(Integer.class) && input2.getClass().equals(Integer.class)) {
			return compareInt((Integer) input1, (Integer) input2);

			// if object is string
		} else if (input1.getClass().equals(String.class) && input2.getClass().equals(String.class)) {
			return compareString((String) input1, (String) input2);

			// if object is date
		} else if (input1.getClass().equals(Date.class) && input2.getClass().equals(Date.class)) {
			return compareDate((Date) input1, (Date) input2);

			// if object is big decimal
		} else if (input1.getClass().equals(BigDecimal.class) && input2.getClass().equals(BigDecimal.class)) {
			return compareBigDecimal((BigDecimal) input1, (BigDecimal) input2);
			
		} else {
			System.err.println("no object match");
			return 0;
		}

	}

	public static int compareString(String input1, String input2) {
		return input1.compareTo(input2);
	}

	public static int compareInt(int input1, int input2) {
		return (input1 - input2);
	}

	public static int compareDate(Date input1, Date input2) {
		return input1.compareTo(input2);
	}

	public static int compareBigDecimal(BigDecimal input1, BigDecimal input2) {
		return input1.compareTo(input2);
	}
}

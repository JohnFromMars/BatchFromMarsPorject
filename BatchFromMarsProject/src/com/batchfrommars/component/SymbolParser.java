package com.batchfrommars.component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public abstract class SymbolParser extends Translation implements Parser {
	private String[] stringList;
	private int count = 0;

	/**
	 * to get the separate symbol from children class
	 * 
	 * @return
	 */
	protected abstract String getSymbol();

	/**
	 * to get the fields list from children class
	 * 
	 * @return
	 */
	protected abstract ArrayList<String> getFields();

	/**
	 * 
	 * @param input
	 */
	public void parse(String input) {
		// split the symbol
		stringList = input.split(this.getSymbol());
		// put the value to each fields
		for (String stringField : this.getFields()) {
			try {
				Field field;
				field = this.getClass().getDeclaredField(stringField);
				field.setAccessible(true);
				Class<?> clazz = field.getType();
				Object value = translate(clazz, count);
				field.set(this, value);
				count++;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * @param clazz
	 * @param count
	 * @return
	 */
	protected Object translate(Class<?> clazz, int count) {
		// different parse for different type
		if (clazz.equals(String.class)) {
			String result = translateToString(stringList[count]);
			count++;
			return result;
		} else if (clazz.equals(int.class)) {
			int result = translateToInteger(stringList[count]);
			count++;
			return result;
		} else if (clazz.equals(BigDecimal.class)) {
			BigDecimal result = translateToBigDecimal(stringList[count]);
			return result;
		} else {
			count++;
			return null;
		}
	}
}
package com.batchfrommars.component;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class SymbolParser {
	private String[] stringList;
	private int count = 0;

	protected abstract String getSymbol();

	protected abstract boolean isSymbolAtLast();

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
		//different parse for different type
		if (clazz.equals(String.class)) {
			return translateToString(count);
		} else if (clazz.equals(int.class)) {
			return translateToInteger(count);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param count
	 * @return
	 */
	private String translateToString(int count) {
		//parse to string
		return stringList[count];
	}

	/**
	 * 
	 * @param count
	 * @return
	 */
	private int translateToInteger(int count) {
		//parse to int
		return Integer.parseInt(stringList[count]);
	}

}
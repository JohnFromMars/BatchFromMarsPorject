package com.batchfrommars.component;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @
 * @author Yj
 *
 */
public abstract class FixedParser {
	// 欄位長度表
	abstract protected LinkedHashMap<String, Integer> getFields();

	// 位置
	private int index = 0;

	/**
	 * 
	 * @param input
	 */
	public void parse(String input) {
		for (Entry<String, Integer> entry : this.getFields().entrySet()) {
			try {
				Field field = this.getClass().getDeclaredField(entry.getKey());
				field.setAccessible(true);
				Class<?> clazz = field.getType();
				int length = entry.getValue();
				Object value = Translate(input, clazz, length);
				field.set(this, value);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 
	 * @param input
	 * @param clazz
	 * @param length
	 * @return
	 */
	protected Object Translate(String input, Class<?> clazz, int length) {
		Object result = null;
		if (clazz.equals(String.class)) {
			return result = translateToString(input, length);
		} else if (clazz.equals(int.class)) {
			return result = translateToInteger(input, length);
		} else {
			return result;
		}

	}

	/**
	 * @param input
	 * @param length
	 * @return
	 */
	private String translateToString(String input, int length) {
		String result = input.substring(index, index + length);
		index += length;
		return result.trim();
	}

	/**
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	private int translateToInteger(String input, int length) {
		int result = Integer.parseInt(input.substring(index, index + length));
		index += length;
		return result;
	}

}

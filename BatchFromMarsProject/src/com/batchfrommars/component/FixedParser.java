package com.batchfrommars.component;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public abstract class FixedParser extends Translation implements Parser {
	// position
	private int index = 0;

	abstract protected LinkedHashMap<String, Integer> getFields();

	
	@Override
	public void parse(String input) {
		for (Entry<String, Integer> entry : this.getFields().entrySet()) {
			try {
				Field field = this.getClass().getDeclaredField(entry.getKey());
				field.setAccessible(true);
				Class<?> clazz = field.getType();
				int length = entry.getValue();
				Object value = translate(input, clazz, length);
				field.set(this, value);

			} catch (Exception e) {
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
	protected Object translate(String input, Class<?> clazz, int length) {
		Object result = null;
		if (clazz.equals(String.class)) {
			result = translateToString(input.substring(index, index + length));
			index += length;
			return result;
		} else if (clazz.equals(int.class)) {
			result = translateToInteger(input.substring(index, index + length));
			index += length;
			return result;
		} else {
			index += length;
			return result;
		}
	}
}

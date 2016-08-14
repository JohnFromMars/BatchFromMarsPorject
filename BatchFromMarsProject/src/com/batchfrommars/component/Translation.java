package com.batchfrommars.component;

public class Translation {

	public Translation() {
	}

	public String translateToString(Object input) {
		if (input.getClass().equals(String.class)) {
			String result = ((String) input).trim();
			return result;
		} else {
			return null;
		}
	}

	public Integer translateToInteger(Object input) {
		if (input.getClass().equals(String.class)) {
			int result = Integer.parseInt((String) input);
			return result;
		} else {
			return null;
		}

	}

}
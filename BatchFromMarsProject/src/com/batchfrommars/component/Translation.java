package com.batchfrommars.component;

import java.math.BigDecimal;

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
		if (input.getClass().equals(String.class) && !input.equals("null")) {
			int result = Integer.parseInt(((String) input).trim());
			return result;
		} else {
			return null;
		}

	}
	
	public BigDecimal translateToBigDecimal(Object input) {
		if (input.getClass().equals(String.class) && !input.equals("null")) {
			BigDecimal result = new BigDecimal(((String) input).trim());
			return result;
		} else {
			return null;
		}

	}
	
	public Boolean translateToBoolean(Object input) {
		if (input.getClass().equals(String.class) && !input.equals("null")) {
			Boolean result = new Boolean(((String) input).trim());
			return result;
		} else {
			return null;
		}

	}

}
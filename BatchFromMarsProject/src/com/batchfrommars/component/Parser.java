package com.batchfrommars.component;

public interface Parser {

	/**
	 * 
	 * @param input
	 */
	void parse(String input)throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;

}
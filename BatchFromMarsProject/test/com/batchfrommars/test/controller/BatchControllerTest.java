 package com.batchfrommars.test.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.util.LogeLevel;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class BatchControllerTest {

	@Test
	public void testSort() throws UnsupportedEncodingException, FileNotFoundException {
		BatchController batchController = new BatchController() {
		};

		batchController.addLogger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .addInput("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .addOutput("D:/BatchFromMars/TTEESST.txt", "BIG5", false)
				       .sort("4,6,1,3,d")
				       .execute();
	}
	
	@Test(expected = SyntaxException.class)  
	public void testSort2() throws UnsupportedEncodingException, FileNotFoundException {
		BatchController batchController = new BatchController() {
		};

		batchController.addLogger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .addInput("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .addOutput("D:/BatchFromMars/TTEESST.txt", "BIG5", false)
				       .sort("4,6,1,3,e")
				       .execute();
	}

}
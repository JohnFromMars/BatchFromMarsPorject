 package com.batchfrommars.test.controller;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.util.LogeLevel;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class BatchControllerTest {

	@Test
	public void testSort() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TTEESST1.txt", "BIG5", false)
				       .sort("4,6,1,3,d")
				       .execute();
	}
	
	@Test(expected = SyntaxException.class)  
	public void testSort2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TTEESST2.txt", "BIG5", false)
				       .sort("4,6,1,3,e")
				       .execute();
	}

}
